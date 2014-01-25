package me.anxuiz.settings.types;

import me.anxuiz.settings.Type;
import me.anxuiz.settings.TypeParseException;

import javax.annotation.Nonnull;

public class StringType implements Type {
    @Override
    public @Nonnull String getName() {
        return "String";
    }

    @Override
    public @Nonnull boolean isInstance(Object obj) {
        return obj instanceof String;
    }

    @Override
    public @Nonnull String print(@Nonnull Object obj) throws IllegalArgumentException {
        return String.valueOf(obj);
    }

    @Override
    public @Nonnull String serialize(@Nonnull Object obj) throws IllegalArgumentException {
        return String.valueOf(obj);
    }

    @Override
    public @Nonnull Object parse(@Nonnull String raw) throws TypeParseException {
        return raw;
    }
}
