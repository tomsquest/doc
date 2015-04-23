package com.doc.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.doc.Doc;
import com.doc.Docs;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.doc.Doc.aDoc;
import static org.assertj.core.api.Assertions.assertThat;

public class DocsJacksonMarshallingTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void registerJacksonModule() {
        mapper.registerModule(new DocJacksonModule());
    }

    @Test
    public void empty_docs() throws Exception {
        Docs docs = new Docs();

        assertCorrectlySerializeDeserialize(docs);
    }

    @Test
    public void one_doc() throws Exception {
        Doc doc = aDoc().with("myField", "myValue").build();
        Docs docs = new Docs(doc);

        assertCorrectlySerializeDeserialize(docs);
    }

    @Test
    public void two_docs() throws Exception {
        Doc doc1 = aDoc().with("field", "myValue").build();
        Doc doc2 = aDoc().with("field1", "1", "2").with("field2", "3", "4").build();
        Docs docs = new Docs(doc1, doc2);

        assertCorrectlySerializeDeserialize(docs);
    }

    private void assertCorrectlySerializeDeserialize(Docs docs) throws Exception {
        String json = mapper.writeValueAsString(docs);
        Docs actualDocs = mapper.readValue(json, Docs.class);
        assertThat(actualDocs).isEqualTo(docs);
    }
}
