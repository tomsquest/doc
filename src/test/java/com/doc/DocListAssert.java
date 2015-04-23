package com.doc;

import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.ListAssert;

import java.util.List;

public class DocListAssert extends AbstractListAssert<DocListAssert, List<Doc>, Doc> {


    public DocListAssert(List<Doc> actual) {
        super(actual, DocListAssert.class);
    }

    public static DocListAssert assertThat(List<Doc> actual) {
        return new DocListAssert(actual);
    }

    public ListAssert<Object> firstValues(String fieldName) {
        isNotNull();

        return extracting(d -> {
            return d.first(fieldName);
        });
    }

}
