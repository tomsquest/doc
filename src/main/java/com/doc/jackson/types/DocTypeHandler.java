package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.doc.Doc;
import com.doc.Doc.Field;
import com.doc.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.doc.Doc.aDoc;

public class DocTypeHandler implements TypeHandler {

    @Override
    public Doc read(JsonParser p) throws IOException {
        List<Field> fields = new ArrayList<>();
        while (p.nextToken() != JsonToken.END_OBJECT) {
            Field field = readField(p);
            fields.add(field);
        }

        return aDoc().with(fields).build();
    }

    private Field readField(JsonParser p) throws IOException {
        String name = p.getText();
        p.nextToken(); // start object

        p.nextToken(); // start name of field "type"

        p.nextToken(); // start value of field "type"
        Type type = Type.fromString(p.getText());

        p.nextToken(); // start name of field "values"

        p.nextToken(); // start value of field "values"
        List<Object> values = readValues(type, p);

        p.nextToken(); // end object

        return new Field(name, type, values);
    }

    private List<Object> readValues(Type type, JsonParser p) throws IOException {
        List<Object> values = new ArrayList<>();
        while (p.nextToken() != JsonToken.END_ARRAY) {
            if (p.getCurrentToken() == JsonToken.VALUE_NULL) {
                values.add(null);
            } else {
                Object read = TypeHandlers.get(type).read(p);
                values.add(read);
            }
        }

        return values;
    }
}
