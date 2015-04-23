package com.doc.jackson;

import com.doc.Docs;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.doc.Doc;

public class DocJacksonModule extends SimpleModule {

    public DocJacksonModule() {
        super("DocModule");

        addSerializer(Doc.class, new DocJacksonSerializer());
        addDeserializer(Doc.class, new DocJacksonDeserializer());

        addSerializer(Docs.class, new DocsJacksonSerializer());
        addDeserializer(Docs.class, new DocsJacksonDeserializer());
    }
}
