package me.anxuiz.settings;

public interface SettingCallback {
    void notifyChange(SettingManager manager, Setting setting, Object oldValue, Object newValue);
}
