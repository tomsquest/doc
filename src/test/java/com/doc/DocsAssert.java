package com.doc;

import org.assertj.core.api.AbstractAssert;

public class DocsAssert extends AbstractAssert<DocsAssert, Docs> {

    public DocsAssert(Docs actual) {
        super(actual, DocsAssert.class);
    }

    public static DocsAssert assertThat(Docs actual) {
        return new DocsAssert(actual);
    }

    public DocsAssert isEmpty() {
        isNotNull();

        if (actual.size() > 0) {
            failWithMessage("Expected docs to be empty but has <%s> size", actual.size());
        }

        return this;
    }

    public DocsAssert isNotEmpty() {
        isNotNull();

        if (actual.size() == 0) {
            failWithMessage("Expected docs to be not empty");
        }

        return this;
    }
}
