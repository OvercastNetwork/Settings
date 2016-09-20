package me.anxuiz.settings.base;

import java.util.List;

import javax.annotation.Nonnull;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingCallback;
import me.anxuiz.settings.SettingCallbackManager;

public abstract class AbstractSettingCallbackManager implements SettingCallbackManager {
    public List<SettingCallback> getCallbacks(@Nonnull Setting setting) {
        return this.getCallbacks(setting, false);
    }

    public int getNumCallbacks(@Nonnull Setting setting) {
        return this.getNumCallbacks(setting, false);
    }

    public boolean hasCallbacks(@Nonnull Setting setting) {
        return this.hasCallbacks(setting, false);
    }
}
