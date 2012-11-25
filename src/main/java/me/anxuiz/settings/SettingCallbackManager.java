package me.anxuiz.settings;

import java.util.List;

import javax.annotation.Nonnull;

public interface SettingCallbackManager {
    @Nonnull List<SettingCallback> getCallbacks(@Nonnull Setting setting);

    int getNumCallbacks(@Nonnull Setting setting);

    boolean hasCallbacks(@Nonnull Setting setting);

    boolean addCallback(@Nonnull Setting setting, @Nonnull SettingCallback callback);

    int clearCallbacks(@Nonnull Setting setting);

    boolean removeCallback(@Nonnull Setting setting, @Nonnull SettingCallback callback);

    int notifyChange(@Nonnull SettingManager mananger, @Nonnull Setting setting, @Nonnull Object oldValue, @Nonnull Object newValue);
}
