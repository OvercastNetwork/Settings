package me.anxuiz.settings.base;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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

    public int notifyChange(SettingManager manager, Setting setting, Object oldValue, Object newValue, boolean includeGlobal) {
        Preconditions.checkNotNull(manager, "setting manager may not be null");
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(oldValue, "old value may not be null");
        Preconditions.checkNotNull(newValue, "new value may not be null");

        // don't notify if the values are the same
        if(oldValue.equals(newValue)) {
            return 0;
        }

        List<SettingCallback> callbacks = this.getCallbacks(setting, includeGlobal); // get a copy of the callbacks
        int notified = 0;
        for(SettingCallback callback : callbacks) {
            try {
                callback.notifyChange(manager, setting, oldValue, newValue);
                notified++;
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        return notified;
    }
}
