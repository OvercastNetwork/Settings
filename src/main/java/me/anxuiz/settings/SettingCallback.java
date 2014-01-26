package me.anxuiz.settings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface SettingCallback {
    /**
     * Called immediately before a setting is changed
     * @param manager       the SettingManager through which the setting will be changed
     * @param setting       the Setting that will change
     * @param oldValue      the value of the setting before the change
     * @param newValue      the value of the setting after the change
     */
    void notifyChange(@Nonnull SettingManager manager, @Nonnull Setting setting, @Nullable Object oldValue, @Nullable Object newValue);
}
