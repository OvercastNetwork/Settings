package me.anxuiz.settings.base;

import java.util.Set;

import javax.annotation.Nullable;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.Type;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;

public class SimpleSetting implements Setting {
    protected final String name;
    protected final Set<String> aliases;
    protected final Class<?> scope;
    protected final String summary;
    protected final String description;

    protected final Type type;
    protected Object defaultValue;

    public SimpleSetting(String name,
            Set<String> aliases,
            Class<?> scope,
            String summary,
            @Nullable String description,
            Type type,
            Object defaultValue) {
        this.name = name;
        this.aliases = ImmutableSortedSet.copyOf(aliases);
        this.scope = scope;
        this.summary = summary;
        this.description = description;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getAliases() {
        return this.aliases;
    }

    public Class<?> getScope() {
        return this.scope;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getDescription() {
        if(this.description != null) {
            return this.description;
        } else {
            return this.summary;
        }
    }

    public Type getType() {
        return this.type;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object newDefault) throws IllegalArgumentException {
        Preconditions.checkNotNull(newDefault);
        Preconditions.checkArgument(this.type.isInstance(newDefault));

        synchronized(this) {
            this.defaultValue = newDefault;
        }
    }

    @Override
    public String toString() {
        return "SimpleSetting{name='" + this.name + "',type='" + this.type.getName() + "'}";
    }
}
