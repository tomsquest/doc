package com.doc;

import org.junit.Test;

public class DocAssertTest {

    @Test
    public void contains() throws Exception {
        Doc doc = Doc.aDoc().with("id", 1L).build();

        DocAssert.assertThat(doc).contains("id").isTrue();
    }

    @Test
    public void contains_ko() throws Exception {
        Doc doc = Doc.aDoc().with("id", 1L).build();

        DocAssert.assertThat(doc).contains("unknown").isFalse();
    }

    @Test
    public void first() throws Exception {
        Doc doc = Doc.aDoc().with("id", 1L).build();

        DocAssert.assertThat(doc).first("id").isEqualTo(1L);
    }
}
