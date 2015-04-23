package com.doc.jackson;

import com.doc.Doc;
import com.doc.Docs;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DocsJacksonSerializer extends JsonSerializer<Docs> {

    @Override
    public void serialize(Docs docs, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        docs.stream().forEach(d -> writeDoc(gen, d));
        gen.writeEndArray();
    }

    private void writeDoc(JsonGenerator gen, Doc doc) {
        try {
            gen.writeObject(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
