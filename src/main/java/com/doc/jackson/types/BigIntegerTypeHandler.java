package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.math.BigInteger;

public class BigIntegerTypeHandler implements TypeHandler {

    @Override
    public BigInteger read(JsonParser p) throws IOException {
        return p.getBigIntegerValue();
    }
}
