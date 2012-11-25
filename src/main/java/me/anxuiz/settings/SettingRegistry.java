package me.anxuiz.settings;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public interface SettingRegistry {
    @Nullable Setting get(@Nonnull String search, boolean includeAliases);
    @Nonnull Setting find(@Nonnull String search, boolean includeAliases) throws IllegalArgumentException;

    @Nonnull Collection<Setting> getSettings();

    boolean isRegistered(@Nonnull Setting setting);
    void register(@Nonnull Setting setting) throws IllegalArgumentException;
    boolean unregister(@Nonnull Setting setting);
}
