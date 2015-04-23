package com.doc;

import java.util.Locale;

public enum Type {

    DEFAULT(null),
    DOC("doc"),
    JSON("json"),
    INTEGER("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    BIGDECIMAL("bigdecimal"),
    BIGINTEGER("biginteger"),
    DATE("date");

    private final String representation;

    Type(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }

    public static Type fromString(String representation) {
        if (representation != null) {
            for (Type type : Type.values()) {
                if (representation.toLowerCase(Locale.ENGLISH).equals(type.toString())) {
                    return type;
                }
            }
        }

        return DEFAULT;
    }
}
