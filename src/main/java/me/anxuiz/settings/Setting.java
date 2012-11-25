package me.anxuiz.settings;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface Setting {
    @Nonnull String getName();

    @Nonnull Collection<String> getAliases();

    @Nonnull Class<?> getScope();

    @Nonnull String getSummary();

    @Nonnull String getDescription();

    @Nonnull Type getType();

    @Nonnull Object getDefaultValue();

    void setDefaultValue(@Nonnull Object newDefault) throws IllegalArgumentException;
}
