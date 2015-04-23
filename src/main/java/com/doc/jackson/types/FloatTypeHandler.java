package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public class FloatTypeHandler implements TypeHandler {
    @Override
    public Float read(JsonParser p) throws IOException {
        return p.getFloatValue();
    }
}
