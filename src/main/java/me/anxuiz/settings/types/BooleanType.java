package me.anxuiz.settings.types;


import me.anxuiz.settings.Toggleable;
import me.anxuiz.settings.Type;
import me.anxuiz.settings.TypeParseException;
import me.anxuiz.settings.util.TypeUtil;

public class BooleanType implements Type, Toggleable {
    public String getName() {
        return "Boolean";
    }

    public boolean isInstance(Object obj) {
        return obj instanceof Boolean;
    }

    public String print(Object obj) throws IllegalArgumentException {
        Boolean value = TypeUtil.getValue(obj, Boolean.class);
        return value ? "on" : "off";
    }

    public String serialize(Object obj) throws IllegalArgumentException {
        Boolean value = TypeUtil.getValue(obj, Boolean.class);
        return value ? "true" : "false";
    }

    public Object parse(String raw) throws TypeParseException {
        raw = raw.toLowerCase().trim();
        if(raw.equals("on") || raw.equals("true") || raw.equals("yes")) {
            return Boolean.valueOf(true);
        } else if (raw.equals("off") || raw.equals("false") || raw.equals("no")) {
            return Boolean.valueOf(false);
        } else {
            throw new TypeParseException();
        }
    }

    public Object getNextState(Object previous) throws IllegalArgumentException {
        Boolean value = TypeUtil.getValue(previous, Boolean.class);
        return Boolean.valueOf(!value);
    }
}
