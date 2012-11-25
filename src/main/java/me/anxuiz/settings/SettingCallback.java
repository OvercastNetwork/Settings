package me.anxuiz.settings;

import javax.annotation.Nonnull;

public interface SettingCallback {
    void notifyChange(@Nonnull SettingManager manager, @Nonnull Setting setting, @Nonnull Object oldValue, @Nonnull Object newValue);
}
