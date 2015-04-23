package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public interface TypeHandler {

    Object read(JsonParser p) throws IOException;

    default void write(JsonGenerator gen, Object value) throws IOException {
        gen.writeObject(value);
    }
}
