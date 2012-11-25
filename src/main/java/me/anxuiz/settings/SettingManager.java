package me.anxuiz.settings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public interface SettingManager {
    boolean hasValue(@Nonnull Setting setting);

    @Nullable Object getRawValue(@Nonnull Setting setting);

    @Nonnull Object getValue(@Nonnull Setting setting);

    @Nonnull Object getValue(@Nonnull Setting setting, @Nonnull Object defaultValue) throws IllegalArgumentException;

    @Nullable <T> T getRawValue(@Nonnull Setting setting, @Nonnull Class<T> typeClass) throws IllegalArgumentException;

    @Nonnull <T> T getValue(@Nonnull Setting setting, @Nonnull Class<T> typeClass) throws IllegalArgumentException;

    @Nonnull <T> T getValue(@Nonnull Setting setting, @Nonnull Class<T> typeClass, @Nonnull T defaultValue) throws IllegalArgumentException;

    void setValue(@Nonnull Setting setting, @Nonnull Object value);

    void deleteValue(@Nonnull Setting setting);

    @Nonnull SettingCallbackManager getCallbackManager();
}
