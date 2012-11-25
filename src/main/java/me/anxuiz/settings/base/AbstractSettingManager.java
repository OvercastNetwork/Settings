package me.anxuiz.settings.base;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.util.TypeUtil;

import com.google.common.base.Preconditions;

public abstract class AbstractSettingManager implements SettingManager {
    public boolean hasValue(Setting setting) {
        Preconditions.checkNotNull(setting);

        return this.getValue(setting) != null;
    }

    public Object getValue(Setting setting, Object defaultValue) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkNotNull(defaultValue);
        Preconditions.checkArgument(setting.getType().isInstance(defaultValue));

        Object value = this.getValue(setting);
        if(value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    public <T> T getValue(Setting setting, Class<T> typeClass) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkNotNull(typeClass);

        Object rawValue = this.getValue(setting);
        if(rawValue != null) {
            return TypeUtil.getValue(rawValue, typeClass);
        } else {
            return null;
        }
    }

    public <T> T getValue(Setting setting, Class<T> typeClass, T defaultValue) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkNotNull(typeClass);
        Preconditions.checkNotNull(defaultValue);

        T value = this.getValue(setting, typeClass);
        if(value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }
}
