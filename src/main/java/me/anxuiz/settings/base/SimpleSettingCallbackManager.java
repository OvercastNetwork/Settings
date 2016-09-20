package me.anxuiz.settings.base;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingCallback;
import me.anxuiz.settings.SettingManager;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

public class SimpleSettingCallbackManager extends AbstractSettingCallbackManager {
    protected final SetMultimap<Setting, SettingCallback> callbacks = Multimaps.synchronizedSetMultimap(LinkedHashMultimap.<Setting, SettingCallback>create());
    protected final Set<SettingCallback> globalCallbacks = new CopyOnWriteArraySet<SettingCallback>();

    public List<SettingCallback> getCallbacks(Setting setting, boolean includeGlobal) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        Set<SettingCallback> settingCallbacks = this.callbacks.get(setting);
        if(includeGlobal) {
            return new ImmutableList.Builder<SettingCallback>().addAll(settingCallbacks).addAll(this.globalCallbacks).build();
        } else {
            return ImmutableList.copyOf(settingCallbacks);
        }
    }

    public int getNumCallbacks(Setting setting, boolean includeGlobal) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        int numLocal = this.callbacks.get(setting).size();
        int numGlobal = includeGlobal ? this.globalCallbacks.size() : 0;

        return numLocal + numGlobal;
    }

    public boolean hasCallbacks(Setting setting, boolean includeGlobal) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        boolean hasLocal = this.callbacks.containsKey(setting);
        boolean hasGlobal = includeGlobal && !this.globalCallbacks.isEmpty();

        return hasLocal || hasGlobal;
    }

    public boolean addCallback(Setting setting, SettingCallback callback) {
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(callback, "callback may not be null");

        return this.callbacks.put(setting, callback);
    }

    public int clearCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return this.callbacks.removeAll(setting).size();
    }

    public boolean removeCallback(Setting setting, SettingCallback callback) {
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(callback, "callback may not be null");

        return this.callbacks.remove(setting, callback);
    }

    public List<SettingCallback> getGlobalCallbacks() {
        return ImmutableList.copyOf(this.globalCallbacks);
    }

    public boolean addGlobalCallback(SettingCallback callback) {
        Preconditions.checkNotNull(callback, "callback may not be null");

        return this.globalCallbacks.add(callback);
    }

    public boolean removeGlobalCallback(SettingCallback callback) {
        Preconditions.checkNotNull(callback, "callback may not be null");

        return this.globalCallbacks.remove(callback);
    }

    public void notifyChange(SettingManager manager, Setting setting, Object oldValue, Object newValue, Object rawValue, boolean includeGlobal, final Runnable changeCallback) {
        Preconditions.checkNotNull(manager, "setting manager may not be null");
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(oldValue, "old value may not be null");
        Preconditions.checkNotNull(newValue, "new value may not be null");

        // Global callbacks get the raw value
        if(includeGlobal && !Objects.equal(oldValue, rawValue)) {
            for(SettingCallback callback : ImmutableSet.copyOf(globalCallbacks)) {
                callback.notifyChange(manager, setting, oldValue, rawValue);
            }
        }

        // Per-setting callbacks get the cooked value i.e. null replaced with default
        if(!Objects.equal(oldValue, newValue)) {
            dispatch(
                manager, setting, oldValue, newValue,
                Iterators.concat(
                    ImmutableSet.copyOf(callbacks.get(setting)).iterator(),
                    Iterators.singletonIterator(new SettingCallback() {
                        public void notifyChange(SettingManager manager, Setting setting, Object oldValue, Object newValue) {
                            changeCallback.run();
                        }
                    })
                )
            );
        }
    }

    private void dispatch(final SettingManager manager, final Setting setting, final Object oldValue, final Object newValue, final Iterator<SettingCallback> callbacks) {
        if(!callbacks.hasNext()) return;

        final Runnable old = YIELDER.get();

        YIELDER.set(new Runnable() {
            boolean yielded;
            public void run() {
                if(yielded) return;
                yielded = true;
                dispatch(manager, setting, oldValue, newValue, callbacks);
            }
        });

        try {
            callbacks.next().notifyChange(manager, setting, oldValue, newValue);
            YIELDER.get().run(); // If callback didn't yield, do it ourselves
        } finally {
            YIELDER.set(old);
        }
    }

    public static void yield() {
        Preconditions.checkState(YIELDER.get() != null, "cannot call yield() outside of a change notification");
        YIELDER.get().run();
    }

    private static final ThreadLocal<Runnable> YIELDER = new ThreadLocal<Runnable>();
}
