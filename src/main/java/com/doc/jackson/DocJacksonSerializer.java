package com.doc.jackson;

import com.doc.Doc;
import com.doc.Doc.Field;
import com.doc.Type;
import com.doc.jackson.types.TypeHandlers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class DocJacksonSerializer extends JsonSerializer<Doc> {

    @Override
    public void serialize(Doc doc, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        writeFields(doc, gen);

        gen.writeEndObject();
    }

    private void writeFields(Doc doc, JsonGenerator gen) {
        doc.stream().forEach(f -> {
            try {
                writeField(f, gen);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void writeField(Field field, JsonGenerator gen) throws IOException {
        gen.writeObjectFieldStart(field.name());

        gen.writeStringField("type", field.type().toString());

        gen.writeArrayFieldStart("values");
        writeValues(gen, field.type(), field.values());
        gen.writeEndArray();

        gen.writeEndObject();
    }

    private void writeValues(JsonGenerator gen, Type type, List<Object> values) throws IOException {
        for (Object value : values) {
            if (value == null) {
                gen.writeNull();
            } else {
                TypeHandlers.get(type).write(gen, value);
            }
        }
    }
}
