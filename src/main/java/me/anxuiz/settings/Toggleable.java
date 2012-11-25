package me.anxuiz.settings;

public interface Toggleable {
    Object getNextState(Object previous) throws IllegalArgumentException;
}
