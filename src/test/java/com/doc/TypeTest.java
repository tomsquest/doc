package com.doc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeTest {

    @Test
    public void fromString_is_case_insensitive() throws Exception {
        assertThat(Type.fromString("json")).isEqualTo(Type.JSON);
        assertThat(Type.fromString("jSOn")).isEqualTo(Type.JSON);
    }
}