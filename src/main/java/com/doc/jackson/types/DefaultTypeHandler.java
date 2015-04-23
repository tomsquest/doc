package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public class DefaultTypeHandler implements TypeHandler {

    @Override
    public Object read(JsonParser p) throws IOException {
        return p.readValueAs(Object.class);
    }

}
