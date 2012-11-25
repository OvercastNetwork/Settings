package me.anxuiz.settings.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingManager;
import me.anxuiz.settings.util.TypeUtil;

import com.google.common.base.Preconditions;

public abstract class AbstractSettingManager implements SettingManager {
    public @Nullable abstract Object getRawValue(@Nonnull Setting setting);

    public boolean hasValue(Setting setting) {
        Preconditions.checkNotNull(setting);

        return this.getRawValue(setting) != null;
    }

    public Object getValue(Setting setting) {
        Preconditions.checkNotNull(setting, "setting");

        return this.getValue(setting, setting.getDefaultValue());
    }

    public Object getValue(Setting setting, Object defaultValue) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting, "setting");
        Preconditions.checkNotNull(defaultValue, "default value");
        Preconditions.checkArgument(setting.getType().isInstance(defaultValue), "default value must be instance of setting type");

        Object value = this.getRawValue(setting);
        if(value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    public <T> T getRawValue(Setting setting, Class<T> typeClass) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting, "setting");
        Preconditions.checkNotNull(typeClass, "type class");

        Object rawValue = this.getRawValue(setting);
        if(rawValue != null) {
            return TypeUtil.getValue(rawValue, typeClass);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Setting setting, Class<T> typeClass) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting, "setting");
        Preconditions.checkNotNull(typeClass, "type class");

        return this.getValue(setting, typeClass, (T) setting.getDefaultValue());
    }

    public <T> T getValue(Setting setting, Class<T> typeClass, T defaultValue) throws IllegalArgumentException {
        Preconditions.checkNotNull(setting);
        Preconditions.checkNotNull(typeClass);
        Preconditions.checkNotNull(defaultValue);

        T value = this.getRawValue(setting, typeClass);
        if(value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }
}
