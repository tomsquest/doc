package com.doc.jackson;

import com.doc.jackson.types.TypeHandlers;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.doc.Doc;
import com.doc.Type;

import java.io.IOException;

public class DocJacksonDeserializer extends StdDeserializer<Doc> {

    public DocJacksonDeserializer() {
        super(Doc.class);
    }

    @Override
    public Doc deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return (Doc) TypeHandlers.get(Type.DOC).read(p);
    }

}
