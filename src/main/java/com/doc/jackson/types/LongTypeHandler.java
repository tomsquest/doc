package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public class LongTypeHandler implements TypeHandler {
    @Override
    public Long read(JsonParser p) throws IOException {
        return p.getLongValue();
    }
}
