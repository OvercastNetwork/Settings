package me.anxuiz.settings;

import java.util.Collection;

import javax.annotation.Nullable;


public interface SettingRegistry {
    @Nullable Setting get(String search, boolean includeAliases);
    Setting find(String search, boolean includeAliases) throws IllegalArgumentException;

    Collection<Setting> getSettings();

    boolean isRegistered(Setting setting);
    void register(Setting setting) throws IllegalArgumentException;
    boolean unregister(Setting setting);
}
