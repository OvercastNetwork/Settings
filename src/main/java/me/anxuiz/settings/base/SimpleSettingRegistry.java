package me.anxuiz.settings.base;

import java.util.Set;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingRegistry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class SimpleSettingRegistry implements SettingRegistry {
    protected final Set<Setting> settings = Sets.newLinkedHashSet();

    public Setting get(String search, boolean includeAliases) {
        Preconditions.checkNotNull(search);

        for(Setting setting : this.settings) {
            if(setting.getName().equalsIgnoreCase(search)) {
                return setting;
            }
        }

        // look through aliases afterward so the name match can be found first
        if(includeAliases) {
            for(Setting setting : this.settings) {
                for(String alias : setting.getAliases()) {
                    if(alias.equalsIgnoreCase(search)) {
                        return setting;
                    }
                }
            }
        }

        return null;
    }

    public Setting find(String search, boolean includeAliases) throws IllegalArgumentException {
        Setting setting = this.get(search, includeAliases);
        if(setting != null) {
            return setting;
        } else {
            throw new IllegalArgumentException("failed to find setting for '" + search + "'");
        }
    }

    public Set<Setting> getSettings() {
        return ImmutableSet.copyOf(this.settings);
    }

    public boolean isRegistered(Setting setting) {
        Preconditions.checkNotNull(setting);

        return this.settings.contains(setting);
    }

    public void register(Setting setting) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkArgument(this.get(setting.getName(), false) == null, "setting already registered to name '%s'", setting.getName());

        this.settings.add(setting);
    }

    public boolean unregister(Setting setting) {
        return this.settings.remove(setting);
    }
}
