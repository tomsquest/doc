package com.doc.jpa;

import com.doc.Doc;
import com.doc.jackson.DocJacksonModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class DocPersistenceConverter implements AttributeConverter<Doc, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new DocJacksonModule());
    }

    @Override
    public String convertToDatabaseColumn(Doc doc) {
        try {
            return MAPPER.writeValueAsString(doc);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Doc convertToEntityAttribute(String json) {
        try {
            return MAPPER.readValue(json, Doc.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
