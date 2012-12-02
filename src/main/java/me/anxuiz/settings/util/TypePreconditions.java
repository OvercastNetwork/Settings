package me.anxuiz.settings.util;

import me.anxuiz.settings.Type;

import com.google.common.base.Preconditions;

public class TypePreconditions {
    public static void checkInstance(Type type, Object obj) throws IllegalArgumentException {
        Preconditions.checkNotNull(obj, "object may not be null");
        Preconditions.checkArgument(type.isInstance(obj), "object is not an instance of " + type.getName());
    }
}
