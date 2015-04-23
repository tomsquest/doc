package com.doc;

import org.junit.Test;

import java.util.List;

import static com.doc.Doc.aDoc;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class DocsTest {

    @Test
    public void stream() throws Exception {
        Doc doc1 = aDoc().with("id", 1L).build();
        Doc doc2 = aDoc().with("id", 2L).build();

        List<Object> ids = new Docs(doc1, doc2).stream().map(doc -> doc.first("id")).collect(toList());

        assertThat(ids).containsOnly(1L, 2L);
    }
    
}