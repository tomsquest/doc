package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalTypeHandler implements TypeHandler {

    @Override
    public BigDecimal read(JsonParser p) throws IOException {
        return p.getDecimalValue();
    }
}
