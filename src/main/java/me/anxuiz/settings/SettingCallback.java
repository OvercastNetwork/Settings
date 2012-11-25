package me.anxuiz.settings;

public interface SettingCallback {
    void notifyChange(Setting setting, Object oldValue, Object newValue);
}
