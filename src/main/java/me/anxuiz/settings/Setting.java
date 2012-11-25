package me.anxuiz.settings;

import java.util.Collection;

public interface Setting {
    String getName();

    Collection<String> getAliases();

    Class<?> getScope();

    String getSummary();

    String getDescription();

    Type getType();

    Object getDefaultValue();

    void setDefaultValue(Object newDefault) throws IllegalArgumentException;
}
