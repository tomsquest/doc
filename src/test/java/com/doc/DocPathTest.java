package com.doc;

import com.doc.Doc.Field;
import org.junit.Test;

import static com.doc.Doc.aDoc;
import static com.doc.Doc.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class DocPathTest {

    @Test
    public void path() throws Exception {
        Doc d = aDoc().with("field", "value").build();

        assertThat(d.$("field").name()).isEqualTo("field");
        assertThat((String) d.$("field").first()).isEqualTo("value");
        assertThat(d.$("field").values()).containsOnly("value");
    }

    @Test
    public void path_of_path() throws Exception {
        Doc d = aDoc()
                .with("child", aDoc().with("field", "value"))
                .build();

        assertThat(d.$("child.field").name()).isEqualTo("field");
        assertThat((String) d.$("child.field").first()).isEqualTo("value");
        assertThat(d.$("child.field").values()).containsOnly("value");
    }

    @Test
    public void path_of_path_of_path() throws Exception {
        Doc d = aDoc().with("child",
                aDoc().with("childChild",
                        aDoc().with("field", "value")))
                .build();

        assertThat(d.$("child.childChild.field").name()).isEqualTo("field");
        assertThat((String) d.$("child.childChild.field").first()).isEqualTo("value");
        assertThat(d.$("child.childChild.field").values()).containsOnly("value");
    }

    @Test
    public void undefined_path() throws Exception {
        assertThat(empty().$("field")).isEqualTo(Field.undefined());
    }

    @Test
    public void undefined_path_of_child_path() throws Exception {
        Doc d = aDoc().with("field", "value").build();

        assertThat(d.$("field.unknown")).isEqualTo(Field.undefined());
    }

    @Test
    public void child_path_of_undefined_path() throws Exception {
        assertThat(empty().$("unknown.field")).isEqualTo(Field.undefined());
    }

    @Test
    public void is_undefined() throws Exception {
        assertThat(empty().$("field").isUndefined()).isTrue();
        assertThat(empty().$("field.field").isUndefined()).isTrue();
    }

    @Test
    public void is_defined() throws Exception {
        Doc d = aDoc().with("field", "value").build();

        assertThat(d.$("field").isUndefined()).isFalse();
    }

    @Test
    public void null_value() throws Exception {
        Doc d = aDoc().with("field", (String) null).build();

        assertThat(d.$("field").isUndefined()).isFalse();
        assertThat((String) d.$("field").first()).isNull();
        assertThat(d.$("field").values()).containsOnly((String) null);
    }

    @Test
    public void invalid_path() throws Exception {
//        assertThat(empty().$(null).isUndefined()).isTrue();
//        assertThat(empty().$("").isUndefined()).isTrue();
//        assertThat(empty().$(".").isUndefined()).isTrue();
//        assertThat(empty().$(".a").isUndefined()).isTrue();
//        assertThat(empty().$(".a.").isUndefined()).isTrue();

        Doc d = aDoc().with("field", (String) null).build();
        assertThat(d.$("field.").isUndefined()).isFalse();
    }
}
