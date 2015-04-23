package com.doc;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Docs {

    private static final Docs EMPTY = new Docs();
    
    private final List<Doc> docs;

    public Docs() {
        docs = Collections.emptyList();
    }

    public Docs(Doc... docs) {
        if (docs == null) {
            this.docs = Collections.emptyList();
        } else {
            this.docs = setDocs(Arrays.asList(docs));
        }
    }

    public Docs(Collection<Doc> docs) {
        this.docs = setDocs(docs);
    }

    private List<Doc> setDocs(Collection<Doc> docs) {
        if (docs == null) {
            return Collections.emptyList();
        } else if (docs.size() == 1) {
            return Collections.singletonList(docs.iterator().next());
        } else {
            return Collections.unmodifiableList(new ArrayList<>(docs));
        }
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public int size() {
        return docs.size();
    }

    public Stream<Doc> stream() {
        return StreamSupport.stream(docs.spliterator(), false);
    }

    public static Docs empty() {
        return EMPTY;
    }

    @Override
    public String toString() {
        return "Docs{docs=" + docs + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Docs other = (Docs) o;
        return docs.equals(other.docs);
    }

    @Override
    public int hashCode() {
        return docs.hashCode();
    }
}
