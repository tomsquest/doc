package com.doc.jackson;

import com.doc.Doc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.google.common.collect.Lists.newArrayList;
import static com.doc.Doc.aDoc;
import static org.assertj.core.api.Assertions.assertThat;

public class DocJacksonMarshallingTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void registerJacksonModule() {
        mapper.registerModule(new DocJacksonModule());
    }

    @Test
    public void empty_doc() throws Exception {
        Doc doc = Doc.empty();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void one_field() throws Exception {
        Doc doc = aDoc()
                .with("myField", "myValue")
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void many_fields() throws Exception {
        Doc doc = aDoc()
                .with("string", "value", "value2")
                .with("long", 123L, 234L)
                .with("double", 1.23, 2.34)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void nested_doc() throws Exception {
        Doc childChild = aDoc()
                .with("childChildField", "childFieldValue")
                .build();

        Doc child = aDoc()
                .with("childField", childChild)
                .build();

        Doc parent = aDoc()
                .with("myChild", child)
                .build();

        assertCorrectSerializeDeserialize(parent);
    }

    @Test
    public void int_are_deserialized_as_int() throws Exception {
        Doc doc = aDoc()
                .with("val", 1)
                .build();

        Doc actualDoc = mapper.readValue(mapper.writeValueAsString(doc), Doc.class);

        assertThat((Object) actualDoc.first("val")).isInstanceOf(Integer.class);
    }

    @Test
    public void long_are_deserialized_as_long() throws Exception {
        Doc doc = aDoc()
                .with("val", 1L)
                .build();

        Doc actualDoc = mapper.readValue(mapper.writeValueAsString(doc), Doc.class);

        assertThat((Object) actualDoc.first("val")).isInstanceOf(Long.class);
    }

    @Test
    public void float_are_deserialized_as_float() throws Exception {
        Doc doc = aDoc()
                .with("val", 1.1f)
                .build();

        Doc actualDoc = mapper.readValue(mapper.writeValueAsString(doc), Doc.class);

        assertThat((Object) actualDoc.first("val")).isInstanceOf(Float.class);
    }

    @Test
    public void double_are_deserialized_as_double() throws Exception {
        Doc doc = aDoc()
                .with("val", 1.1d)
                .build();

        Doc actualDoc = mapper.readValue(mapper.writeValueAsString(doc), Doc.class);
        
        assertThat((Object) actualDoc.first("val")).isInstanceOf(Double.class);
    }

    @Test
    public void date_are_deserialized_as_date() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse("2000-01-02T03:04:05Z");

        Doc doc = aDoc()
                .with("val", date)
                .build();

        Doc actualDoc = mapper.readValue(mapper.writeValueAsString(doc), Doc.class);

        assertThat((Date) actualDoc.first("val")).isEqualTo(date);
    }

    @Test
    public void raw_json_object() throws Exception {
        String raw = "{\"foo\":\"bar\"}";

        Doc doc = aDoc()
                .withJson("json", raw)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void two_raw_json_objects() throws Exception {
        String raw = "{\"foo\":\"bar\"}";
        String raw2 = "{\"bing\":\"bang\"}";

        Doc doc = aDoc()
                .withJson("json", raw, raw2)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void raw_json_array() throws Exception {
        String raw = "[1,2]";

        Doc doc = aDoc()
                .with("json", raw)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void raw_json_value() throws Exception {
        String raw = "\"blue\"";

        Doc doc = aDoc()
                .with("json", raw)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void null_value() throws Exception {
        Doc doc = aDoc()
                .with("field", (String) null)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void null_typed_value() throws Exception {
        Doc doc = aDoc()
                .with("field", (Long) null)
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    @Test
    public void collection_of_null_typed_value() throws Exception {
        Doc doc = aDoc()
                .withLongs("field", newArrayList((Long) null))
                .build();

        assertCorrectSerializeDeserialize(doc);
    }

    private Doc assertCorrectSerializeDeserialize(Doc doc) throws Exception {
        String json = mapper.writeValueAsString(doc);
        Doc actualDoc = mapper.readValue(json, Doc.class);
        assertThat(actualDoc).isEqualTo(doc);
        return actualDoc;
    }
}
