package me.anxuiz.settings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public interface SettingManager {

    /**
     * Test if the given setting has a value
     */
    boolean hasValue(@Nonnull Setting setting);

    /**
     * Return the value of the given setting or null if no value is set. Unlike the
     * {@link #getValue} method, this can return null even if the setting is not optional.
     */
    @Nullable Object getRawValue(@Nonnull Setting setting);

    /**
     * Return the value of the given setting. If no value is set, return the default value
     * (which will be null for optional settings).
     */
    @Nullable Object getValue(@Nonnull Setting setting);

    /**
     * Return the value of the given setting, or the given default if no value is set.
     * @throws IllegalArgumentException if the given default is of the wrong type for the setting
     */
    @Nonnull Object getValue(@Nonnull Setting setting, @Nonnull Object defaultValue) throws IllegalArgumentException;

    /**
     * Return the value of the given setting cast to the given type, or null if no value is set.
     * @throws IllegalArgumentException if the setting value cannot be cast to the given type
     */
    @Nullable <T> T getRawValue(@Nonnull Setting setting, @Nonnull Class<T> typeClass) throws IllegalArgumentException;

    /**
     * Return the value of the given setting cast to the given type. If no value is set, return
          * null for an optional setting, or the default value for a non-optional setting.
     * @throws IllegalArgumentException if the setting value cannot be cast to the given type
     */
    @Nullable <T> T getValue(@Nonnull Setting setting, @Nonnull Class<T> typeClass) throws IllegalArgumentException;

    /**
     * Return the value of the given setting cast to the given type, or the given default if no value is set.
     * @throws IllegalArgumentException if the setting value cannot be cast to the given type
     */
    @Nonnull <T> T getValue(@Nonnull Setting setting, @Nonnull Class<T> typeClass, @Nonnull T defaultValue) throws IllegalArgumentException;

    /**
     * Set the value for the given setting. Notification callbacks for the given setting are called,
     * as well as global notification callbacks.
     */
    void setValue(@Nonnull Setting setting, @Nullable Object value);

    /**
     * Set the value for the given setting. Notification callbacks for the given setting are called,
     * and global callbacks are also called if the last argument is true.
     */
    void setValue(@Nonnull Setting setting, @Nullable Object value, boolean notifyGlobal);

    /**
     * Remove any value set for the given setting
     */
    void deleteValue(@Nonnull Setting setting);

    /**
     * Get the SettingCallbackManager that will be used to send change notifications for this SettingManager
     */
    @Nonnull SettingCallbackManager getCallbackManager();
}
