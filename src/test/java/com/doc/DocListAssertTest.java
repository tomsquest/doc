package com.doc;

import org.junit.Test;

import java.util.List;

import static com.doc.Doc.aDoc;
import static com.doc.DocListAssert.assertThat;
import static com.google.common.collect.Lists.newArrayList;

public class DocListAssertTest {

    @Test
    public void firstValues() throws Exception {
        Doc doc = aDoc().with("id", 1L).build();
        Doc doc2 = aDoc().with("id", 2L).build();

        List<Doc> docs = newArrayList(doc, doc2);

        assertThat(docs).firstValues("id").containsExactly(1L, 2L);
    }

    @Test(expected = AssertionError.class)
    public void firstValues_ko() throws Exception {
        Doc doc = aDoc().with("id", 1L).build();
        Doc doc2 = aDoc().with("id", 2L).build();

        List<Doc> docs = newArrayList(doc, doc2);

        assertThat(docs).firstValues("id").containsExactly(1L, 3L);
    }
}
