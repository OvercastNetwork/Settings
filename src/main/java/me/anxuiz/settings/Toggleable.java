package me.anxuiz.settings;

import javax.annotation.Nonnull;

public interface Toggleable {
    @Nonnull Object getNextState(@Nonnull Object previous) throws IllegalArgumentException;
}
