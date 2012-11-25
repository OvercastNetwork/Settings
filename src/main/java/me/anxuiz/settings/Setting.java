package me.anxuiz.settings;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Setting {
    @Nonnull String getName();

    @Nonnull Collection<String> getAliases();

    @Nullable Class<?> getScope();

    @Nonnull String getSummary();

    @Nonnull String getDescription();

    @Nonnull Type getType();

    @Nonnull Object getDefaultValue();

    void setDefaultValue(@Nonnull Object newDefault) throws IllegalArgumentException;
}
