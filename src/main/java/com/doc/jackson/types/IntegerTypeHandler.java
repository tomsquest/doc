package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public class IntegerTypeHandler implements TypeHandler {
    @Override
    public Integer read(JsonParser p) throws IOException {
        return p.getIntValue();
    }
}
