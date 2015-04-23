package com.doc;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class DocAssert extends AbstractAssert<DocAssert, Doc> {

    public DocAssert(Doc actual) {
        super(actual, DocAssert.class);
    }

    public static DocAssert assertThat(Doc actual) {
        return new DocAssert(actual);
    }

    public AbstractBooleanAssert contains(String name) {
        isNotNull();

        return Assertions.assertThat(actual.contains(name)).as("Expected Doc's field <%s> to exist in doc <%s>", name, actual);
    }

    public AbstractObjectAssert first(String name) {
        isNotNull();
        contains(name).isTrue();

        return Assertions.assertThat((Object) actual.first(name));
    }
}
