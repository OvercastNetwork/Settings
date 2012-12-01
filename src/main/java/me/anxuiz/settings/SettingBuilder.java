package me.anxuiz.settings;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.anxuiz.settings.base.SimpleSetting;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

public class SettingBuilder {
    protected @Nullable String name = null;
    protected final Set<String> aliases = Sets.newLinkedHashSet();
    protected @Nullable String summary = null;
    protected @Nullable String description = null;
    protected @Nullable Type type = null;
    protected @Nullable Object defaultValue = null;

    public SettingBuilder() {
    }

    public @Nonnull SettingBuilder name(@Nonnull String name) {
        Preconditions.checkNotNull(name, "name");
        this.name = name;
        return this;
    }

    public @Nonnull SettingBuilder alias(@Nonnull String alias) {
        Preconditions.checkNotNull(alias, "alias");
        this.aliases.add(alias);
        return this;
    }

    public @Nonnull SettingBuilder aliases(@Nonnull Collection<String> aliases) {
        Preconditions.checkNotNull(aliases, "aliases");
        this.aliases.clear();
        this.aliases.addAll(aliases);
        return this;
    }

    public @Nonnull SettingBuilder summary(@Nonnull String summary) {
        Preconditions.checkNotNull(summary, "summary");
        this.summary = summary;
        return this;
    }

    public @Nonnull SettingBuilder description(@Nonnull String description) {
        Preconditions.checkNotNull(description, "description");
        this.description = description;
        return this;
    }

    public @Nonnull SettingBuilder type(@Nonnull Type type) {
        Preconditions.checkNotNull(type, "type");
        // clear the default value if it does not work with the new type
        if(this.defaultValue != null && !type.isInstance(this.defaultValue)) {
            this.defaultValue = null;
        }
        this.type = type;
        return this;
    }

    public @Nonnull SettingBuilder defaultValue(@Nonnull Object defaultValue) {
        Preconditions.checkNotNull(defaultValue, "default value");
        Preconditions.checkArgument(this.type != null && this.type.isInstance(defaultValue), "default value must be an instance of the type specified");
        this.defaultValue = defaultValue;
        return this;
    }

    public @Nonnull Setting get() throws IllegalStateException {
        Preconditions.checkState(this.name != null, "setting must have name");
        Preconditions.checkState(this.summary != null, "setting must have summary");
        Preconditions.checkState(this.type != null, "setting must have type");
        Preconditions.checkState(this.defaultValue != null, "setting must have a default value");

        return new SimpleSetting(this.name, this.aliases, this.summary, this.description, this.type, this.defaultValue);
    }
}
