package com.doc;

import com.doc.Doc.DocBuilder;
import com.doc.Doc.Field;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static com.doc.Doc.aDoc;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class DocTest {

    @Test
    public void empty_doc() throws Exception {
        Doc doc = aDoc().build();

        assertThat(doc).isEqualTo(new Doc());
        assertThat(doc).isEqualTo(Doc.empty());
        assertThat(doc.isEmpty()).isTrue();
    }

    @Test
    public void primitive_fields() throws Exception {
        Doc doc = aDoc().with("string", "value")
                .with("boolean", true)
                .with("long", 123L)
                .with("double", 1.23)
                .build();

        assertThat((Object) doc.first("string")).isEqualTo("value");
        assertThat((Object) doc.first("boolean")).isEqualTo(true);
        assertThat((Object) doc.first("long")).isEqualTo(123L);
        assertThat((Object) doc.first("double")).isEqualTo(1.23);
    }

    @Test
    public void field_name_cannot_start_with_underscore() throws Exception {
        assertInvalidFieldName("_b");
    }

    @Test
    public void field_name_contains_word_characters_only() throws Exception {
        assertInvalidFieldName(null);
        assertInvalidFieldName(" ");
        assertInvalidFieldName("a b");
        assertInvalidFieldName("a*");
        assertInvalidFieldName("a-b");
    }

    private void assertInvalidFieldName(String field) {
        try {
            aDoc().with(field, "value").build();
            fail();
        } catch (IllegalArgumentException ignored) {
            // ok
        }

        Doc doc = aDoc().with("field", "value").build();

        try {
            doc.first(field);
            fail();
        } catch (IllegalArgumentException ignored) {
            // ok
        }

        try {
            doc.first(field);
            fail();
        } catch (IllegalArgumentException ignored) {
            // ok
        }
    }

    @Test
    public void first_of_unknown_field() throws Exception {
        assertThat((Object) Doc.empty().first("unknownField")).isNull();
    }

    @Test
    public void get_of_unknown_field() throws Exception {
        assertThat(Doc.empty().get("unknownField")).isEmpty();
    }

    @Test
    public void accept_null_value() throws Exception {
        Doc doc = aDoc().with("aField", (Boolean) null).build();

        assertThat(doc.get("aField")).isEqualTo(newArrayList((Object) null));
    }

    @Test
    public void accept_null_values() throws Exception {
        Doc doc = aDoc().with("aField", (Boolean[]) null).build();

        assertThat(doc.get("aField")).isEmpty();
    }

    @Test
    public void accept_null_collection() throws Exception {
        Doc doc = aDoc().withBooleans("aField", null).build();

        assertThat(doc.get("aField")).isEmpty();
    }

    @Test
    public void accept_empty_collection() throws Exception {
        Doc doc = aDoc().withBooleans("aField", Collections.emptyList()).build();

        assertThat(doc.get("aField")).isEmpty();
    }

    @Test
    public void field_is_a_Doc() throws Exception {
        Doc childDoc = aDoc().with("childField", "childValue").build();

        Doc parent = aDoc().with("child", childDoc).build();

        assertThat((Object) parent.first("child")).isEqualTo(childDoc);
    }

    @Test
    public void builder_fields_are_built() throws Exception {
        DocBuilder builder = aDoc().with("child", true);
        Doc d = aDoc().with("aBuilder", builder).build();

        assertThat(d.<Doc>first("aBuilder")).isEqualTo(builder.build());
    }

    @Test
    public void builder_fields_with_null() throws Exception {
        DocBuilder builder = aDoc().with("child", true);
        Doc d = aDoc().with("aBuilder", builder, null, builder).build();

        assertThat(d.get("aBuilder")).isEqualTo(newArrayList(builder.build(), null, builder.build()));
    }

    @Test
    public void multiple_values_with_varargs() throws Exception {
        Doc doc = aDoc()
                .with("booleans", true, false)
                .with("longs", 1L, 2L)
                .with("doubles", 1.1, 2.1)
                .with("strings", "value", "value2")
                .with("docs",
                        aDoc().with("a", "a").build(),
                        aDoc().with("b", "b").build())
                .build();

        assertThat(doc.get("strings")).containsExactly("value", "value2");
        assertThat(doc.get("booleans")).containsExactly(true, false);
        assertThat(doc.get("longs")).containsExactly(1L, 2L);
        assertThat(doc.get("doubles")).containsExactly(1.1, 2.1);
        assertThat(doc.get("docs")).containsExactly(aDoc().with("a", "a").build(), aDoc().with("b", "b").build());
    }

    @Test
    public void multiple_values_with_collection() throws Exception {
        Doc doc = aDoc()
                .withBooleans("booleans", newArrayList(true, false))
                .withLongs("longs", newArrayList(1L, 2L))
                .withDoubles("doubles", newArrayList(1.1, 2.1))
                .withStrings("strings", newArrayList("value", "value2"))
                .withDocs("docs", newArrayList(
                        aDoc().with("a", "a").build(),
                        aDoc().with("b", "b").build()))
                .build();

        assertThat(doc.get("strings")).containsExactly("value", "value2");
        assertThat(doc.get("booleans")).containsExactly(true, false);
        assertThat(doc.get("longs")).containsExactly(1L, 2L);
        assertThat(doc.get("doubles")).containsExactly(1.1, 2.1);
        assertThat(doc.get("docs")).containsExactly(aDoc().with("a", "a").build(), aDoc().with("b", "b").build());
    }

    @Test
    public void equality_of_field_is_order_independent() throws Exception {
        Doc order = aDoc().with("first", "first").with("second", "second").build();

        Doc orderInvert = aDoc().with("second", "second").with("first", "first").build();

        assertThat(order).isEqualTo(orderInvert);
    }

    @Test
    public void equality_of_values_is_order_dependent() throws Exception {
        Doc firstSecond = aDoc().with("aField", "first", "second").build();
        Doc secondFirst = aDoc().with("aField", "second", "first").build();

        assertThat(firstSecond).isNotEqualTo(secondFirst);
    }    

    @Test
    public void streamable() throws Exception {
        Doc doc = aDoc()
                .with("field1", "yes")
                .with("field2", "no")
                .build();

        assertThat(doc.stream().collect(toList())).extracting("name").containsExactly("field1", "field2");
    }

    @Test
    public void contain_field_name() throws Exception {
        Doc doc = aDoc().with("aField", "yes").build();

        assertThat(doc.contains("aField")).isTrue();
        assertThat(doc.contains("unknown")).isFalse();
    }

    @Test
    public void sort_fields_by_name() throws Exception {
        Doc doc = aDoc().with("b", "aValue").with("a", "aValue").build();

        Optional<Field> first = doc.stream().findFirst();

        assertThat(first.get().name()).isEqualTo("a");
    }

    @Test
    public void get_returns_immutable_list() throws Exception {
        Doc doc = aDoc().with("field", "value").build();

        try {
            doc.get("field").add("another");
            fail("not immutable");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void field_values_is_immutable() throws Exception {
        Doc doc = aDoc().with("field", "value").build();

        List<Field> fields = doc.stream().collect(toList());

        try {
            fields.get(0).values().add("another");
            fail("not immutable");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void immutable_when_build_with_an_array() throws Exception {
        Doc doc = aDoc().with("field", new String[]{"a"}).build();

        try {
            doc.get("field").add("another");
            fail("not immutable");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void immutable_when_build_with_a_mutable_collection() throws Exception {
        List<String> mutableCollection = new ArrayList<>();
        mutableCollection.add("aValue");
        Doc doc = aDoc().withStrings("field", mutableCollection).build();

        try {
            doc.get("field").add("another");
            fail("not immutable");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void modify_copies_an_existing_doc() throws Exception {
        Doc doc = aDoc().with("field", "one").build();

        Doc modified = doc.modify().with("field", "two").with("another", true).build();

        assertThat((Object) modified.first("field")).isEqualTo("two");
        assertThat((Object) modified.first("another")).isEqualTo(true);
    }
    @Test
    public void modify_does_not_alter_the_copied_doc() throws Exception {
        Doc doc = aDoc().with("field", "one").build();

        doc.modify().with("field", "two").build();

        assertThat((Object) doc.first("field")).isEqualTo("one");
    }


    @Test
    public void remove_a_field_in_a_doc() throws Exception {
        Doc doc = aDoc().with("field", "one").build();

        Doc removed = doc.modify().remove("field").build();

        assertThat(removed).isEqualTo(Doc.empty());
    }
}
