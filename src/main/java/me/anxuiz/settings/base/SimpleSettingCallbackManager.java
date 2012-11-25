package me.anxuiz.settings.base;

import java.util.List;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingCallback;
import me.anxuiz.settings.SettingCallbackManager;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

public class SimpleSettingCallbackManager implements SettingCallbackManager {
    protected final SetMultimap<Setting, SettingCallback> callbacks = Multimaps.synchronizedSetMultimap(LinkedHashMultimap.<Setting, SettingCallback>create());

    public List<SettingCallback> getCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return ImmutableList.copyOf(this.callbacks.get(setting));
    }

    public int getNumCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return this.callbacks.get(setting).size();
    }

    public boolean hasCallbacks(Setting setting) {
        Preconditions.checkNotNull(setting, "setting may not be null");

        return this.callbacks.containsKey(setting);
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

    public void notify(Setting setting, Object oldValue, Object newValue) {
        Preconditions.checkNotNull(setting, "setting may not be null");
        Preconditions.checkNotNull(oldValue, "old value may not be null");
        Preconditions.checkNotNull(newValue, "new value may not be null");

        // don't notify if the values are the same
        if(oldValue.equals(newValue)) {
            return;
        }

        List<SettingCallback> callbacks = this.getCallbacks(setting); // get a copy of the callbacks
        for(SettingCallback callback : callbacks) {
            try {
                callback.notifyChange(setting, oldValue, newValue);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
