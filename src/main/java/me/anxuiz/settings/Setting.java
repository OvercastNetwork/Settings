package me.anxuiz.settings;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Setting {

    /**
     * The name of this setting
     */
    @Nonnull String getName();

    /**
     * Alternate names for this setting
     */
    @Nonnull Collection<String> getAliases();

    /**
     * A short description of this setting
     */
    @Nonnull String getSummary();

    /**
     * True if {@link #getDescription()} returns more text than {@link #getSummary}
     */
    boolean hasDescription();

    /**
     * A description of this setting that is potentially longer than {@link #getSummary}
     */
    @Nonnull String getDescription();

    /**
     * The data type of this setting
     */
    @Nonnull Type getType();

    /**
     * True if this setting has a default value defined
     */
    boolean hasDefaultValue();

    /**
     * The default value of this setting, or null if no default is defined
     */
    @Nullable Object getDefaultValue();
}
