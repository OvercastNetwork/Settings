package me.anxuiz.settings;

import javax.annotation.Nonnull;

public interface Type {
    @Nonnull String getName();

    boolean isInstance(Object obj);

    @Nonnull String print(@Nonnull Object obj) throws IllegalArgumentException;

    @Nonnull String serialize(@Nonnull Object obj) throws IllegalArgumentException;

    @Nonnull Object parse(@Nonnull String raw) throws TypeParseException;
}
