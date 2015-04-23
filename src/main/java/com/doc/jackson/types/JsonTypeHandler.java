package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;

import java.io.IOException;

public class JsonTypeHandler implements TypeHandler {

    @Override
    public String read(JsonParser p) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        return treeNode.toString();
    }

    @Override
    public void write(JsonGenerator gen, Object value) throws IOException {
        gen.writeRawValue((String) value);
    }
}
