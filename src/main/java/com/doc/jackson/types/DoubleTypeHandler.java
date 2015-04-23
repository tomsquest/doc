package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public class DoubleTypeHandler implements TypeHandler {

    @Override
    public Double read(JsonParser p) throws IOException {
        return p.getDoubleValue();
    }
}
