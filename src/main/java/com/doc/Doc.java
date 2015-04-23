package com.doc;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

public final class Doc implements Serializable {

    private static final Doc EMPTY = new Doc();

    private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9]\\w*");

    private final Map<String, Field> fields;

    public Doc() {
        fields = Collections.emptyMap();
    }

    private Doc(Map<String, Field> fields) {
        this.fields = fields;
    }

    public static Doc empty() {
        return EMPTY;
    }

    public static DocBuilder aDoc() {
        return new DocBuilder();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T first(@NotNull String fieldName) {
        assertValidFieldName(fieldName);
        if (fields.containsKey(fieldName)) {
            Field field = fields.get(fieldName);
            return field.first();
        }
        return null;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <T> List<T> get(@NotNull String fieldName) {
        assertValidFieldName(fieldName);
        if (fields.containsKey(fieldName)) {
            Field field = fields.get(fieldName);
            return (List<T>) field.values();
        }
        return Collections.emptyList();
    }

    public boolean contains(@NotNull String fieldName) {
        return fields.containsKey(fieldName);
    }

    public boolean isEmpty() {
        return equals(EMPTY);
    }

    @Override
    public String toString() {
        return "Doc{" + fields + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Doc doc = (Doc) obj;

        return fields.equals(doc.fields);
    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }

    public Stream<Field> stream() {
        return StreamSupport.stream(fields.values().spliterator(), false);
    }

    public DocBuilder modify() {
        return new DocBuilder(fields);
    }

    /**
     * Convenient method to access field within fields, with a dot-syntax. ex: doc.$('parent.child')
     */
    public Field $(@Nullable String fullPath) {
        if (fullPath == null) {
            return Field.undefined();
        }

        List<String> paths = Arrays.asList(fullPath.split("\\."));
        if (paths.isEmpty()) {
            return Field.undefined();
        }

        String fieldName = paths.get(0);
        if (contains(fieldName)) {
            if (paths.size() == 1) {
                return fields.get(fieldName);
            } else {
                Object firstChild = first(fieldName);
                if (firstChild instanceof Doc) {
                    Doc doc = (Doc) firstChild;
                    List<String> subPaths = paths.subList(1, paths.size());
                    String subPathsStr = String.join(".", subPaths);
                    return doc.$(subPathsStr);
                }
            }
        }

        return Field.undefined();
    }

    public static final class DocBuilder {

        private final Map<String, Field> values;

        private DocBuilder() {
            values = new HashMap<>();
        }

        public DocBuilder(Map<String, Field> orig) {
            values = new HashMap<>(orig);
        }

        public DocBuilder with(@NotNull String field, Boolean... values) {
            assertValidFieldName(field);
            setValues(field, Type.DEFAULT, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, Integer... values) {
            assertValidFieldName(field);
            setValues(field, Type.INTEGER, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, Long... values) {
            assertValidFieldName(field);
            setValues(field, Type.LONG, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, BigInteger... values) {
            assertValidFieldName(field);
            setValues(field, Type.BIGINTEGER, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, Float... values) {
            assertValidFieldName(field);
            setValues(field, Type.FLOAT, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, Double... values) {
            assertValidFieldName(field);
            setValues(field, Type.DOUBLE, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, BigDecimal... values) {
            assertValidFieldName(field);
            setValues(field, Type.BIGDECIMAL, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, String... values) {
            assertValidFieldName(field);
            setValues(field, Type.DEFAULT, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, Date... values) {
            assertValidFieldName(field);
            setValues(field, Type.DATE, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, Doc... values) {
            assertValidFieldName(field);
            setValues(field, Type.DOC, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder with(@NotNull String field, DocBuilder... values) {
            assertValidFieldName(field);
            setValues(field, Type.DOC, values == null ? Collections.emptyList() : buildBuilders(Arrays.asList(values)));
            return this;
        }

        public DocBuilder withBooleans(@NotNull String field, Collection<Boolean> values) {
            assertValidFieldName(field);
            setValues(field, Type.DEFAULT, values);
            return this;
        }

        public DocBuilder withIntegers(@NotNull String field, Collection<Integer> values) {
            assertValidFieldName(field);
            setValues(field, Type.INTEGER, values);
            return this;
        }

        public DocBuilder withLongs(@NotNull String field, Collection<Long> values) {
            assertValidFieldName(field);
            setValues(field, Type.LONG, values);
            return this;
        }

        public DocBuilder withBigIntegers(@NotNull String field, Collection<BigInteger> values) {
            assertValidFieldName(field);
            setValues(field, Type.BIGINTEGER, values);
            return this;
        }

        public DocBuilder withFloats(@NotNull String field, Collection<Float> values) {
            assertValidFieldName(field);
            setValues(field, Type.FLOAT, values);
            return this;
        }

        public DocBuilder withDoubles(@NotNull String field, Collection<Double> values) {
            assertValidFieldName(field);
            setValues(field, Type.DOUBLE, values);
            return this;
        }

        public DocBuilder withBigDecimals(@NotNull String field, Collection<BigDecimal> values) {
            assertValidFieldName(field);
            setValues(field, Type.BIGDECIMAL, values);
            return this;
        }

        public DocBuilder withStrings(@NotNull String field, Collection<String> values) {
            assertValidFieldName(field);
            setValues(field, Type.DEFAULT, values);
            return this;
        }

        public DocBuilder withDates(@NotNull String field, Collection<Date> values) {
            assertValidFieldName(field);
            setValues(field, Type.DATE, values);
            return this;
        }

        public DocBuilder withDocs(@NotNull String field, Collection<Doc> values) {
            assertValidFieldName(field);
            setValues(field, Type.DOC, values);
            return this;
        }

        public DocBuilder withDocBuilders(@NotNull String field, Collection<DocBuilder> values) {
            assertValidFieldName(field);
            setValues(field, Type.DOC, buildBuilders(values));
            return this;
        }

        public DocBuilder withJson(@NotNull String field, String... values) {
            assertValidFieldName(field);
            setValues(field, Type.JSON, values == null ? Collections.emptyList() : Arrays.asList(values));
            return this;
        }

        public DocBuilder withJson(@NotNull String field, Collection<String> values) {
            assertValidFieldName(field);
            setValues(field, Type.JSON, values);
            return this;
        }

        public DocBuilder remove(@NotNull String field) {
            assertValidFieldName(field);
            values.remove(field);
            return this;
        }

        public DocBuilder with(@NotNull List<Field> fields) {
            Map<String, Field> map = fields.stream().collect(toMap(Field::name, f -> f));
            values.putAll(map);
            return this;
        }

        public Doc build() {
            if (values.isEmpty()) {
                return empty();
            }

            ImmutableSortedMap<String, Field> sortedMap = ImmutableSortedMap.copyOf(values);
            return new Doc(sortedMap);
        }

        private void setValues(String name, Type type, Collection<?> values) {
            if (values == null || values.isEmpty()) {
                this.values.put(name, new Field(name, type, Collections.emptyList()));
            } else if (values.size() == 1) {
                Object firstValue = values.iterator().next();
                this.values.put(name, new Field(name, type, Collections.singletonList(firstValue)));
            } else {
                this.values.put(name, new Field(name, type, Collections.unmodifiableList(new ArrayList<>(values))));
            }
        }

        private List<Doc> buildBuilders(Collection<DocBuilder> values) {
            if (values == null) {
                return Collections.emptyList();
            }

            return values.stream()
                    .map(builder -> builder == null ? null : builder.build())
                    .collect(Collectors.toList());
        }

    }

    public static final class Field implements Serializable {

        private static final Field UNDEFINED = new Field();

        private final String name;
        private final Type type;
        private final List<Object> values;

        private Field() {
            name = null;
            type = Type.DEFAULT;
            values = Collections.emptyList();
        }

        public Field(@NotNull String name, @NotNull Type type, @NotNull List<Object> values) {
            this.name = Preconditions.checkNotNull(name);
            this.type = Preconditions.checkNotNull(type);
            this.values = Preconditions.checkNotNull(values);
        }

        public static Field undefined() {
            return UNDEFINED;
        }

        @Nullable
        public String name() {
            return name;
        }

        public Type type() {
            return type;
        }

        @Nullable
        @SuppressWarnings("unchecked")
        public <T> T first() {
            if (values.isEmpty()) {
                return null;
            }
            return (T) values.iterator().next();
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public <T> List<T> values() {
            return (List<T>) values;
        }

        public boolean isUndefined() {
            return name == null;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Field field = (Field) obj;
            return Objects.equals(name, field.name) &&
                    Objects.equals(type, field.type) &&
                    Objects.equals(values, field.values);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type, values);
        }

        @Override
        public String toString() {
            return "Field{name='" + name + '\'' + ", type='" + type + '\'' + ", values=" + values + '}';
        }
    }
    private static void assertValidFieldName(@NotNull String fieldName) {
        if (fieldName == null) {
            throw new IllegalArgumentException("Field name must be not empty");
        } else if (!FIELD_NAME_PATTERN.matcher(fieldName).matches()) {
            throw new IllegalArgumentException("Field name syntax is invalid");
        }
    }
}
