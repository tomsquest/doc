package com.doc.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.doc.Doc;
import com.doc.Docs;

import java.io.IOException;
import java.util.List;

public class DocsJacksonDeserializer extends StdDeserializer<Docs> {

    public DocsJacksonDeserializer() {
        super(Docs.class);
    }

    @Override
    public Docs deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        List<Doc> list = readValues(p);
        return new Docs(list);
    }

    private List<Doc> readValues(JsonParser p) throws IOException {
        return p.readValueAs(new TypeReference<List<Doc>>() {
        });
    }
}
