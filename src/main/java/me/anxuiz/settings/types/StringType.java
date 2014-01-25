package me.anxuiz.settings.types;

import me.anxuiz.settings.Type;
import me.anxuiz.settings.TypeParseException;

import javax.annotation.Nonnull;

public class StringType implements Type {
    @Nonnull
    @Override
    public String getName() {
        return "String";
    }

    @Override
    public boolean isInstance(Object obj) {
        return obj instanceof String;
    }

    @Nonnull
    @Override
    public String print(@Nonnull Object obj) throws IllegalArgumentException {
        return String.valueOf(obj);
    }

    @Nonnull
    @Override
    public String serialize(@Nonnull Object obj) throws IllegalArgumentException {
        return String.valueOf(obj);
    }

    @Nonnull
    @Override
    public Object parse(@Nonnull String raw) throws TypeParseException {
        return raw;
    }
}
