package com.doc.jackson.types;

import com.google.common.collect.Maps;
import com.doc.Type;

import java.util.EnumMap;
import java.util.Map;

public final class TypeHandlers {

    private static final Map<Type, TypeHandler> handlers;

    public static final DefaultTypeHandler DEFAULT_TYPE_HANDLER = new DefaultTypeHandler();

    static {
        Map<Type, TypeHandler> m = new EnumMap<>(Type.class);
        m.put(Type.INTEGER, new IntegerTypeHandler());
        m.put(Type.LONG, new LongTypeHandler());
        m.put(Type.BIGINTEGER, new BigIntegerTypeHandler());
        m.put(Type.FLOAT, new FloatTypeHandler());
        m.put(Type.DOUBLE, new DoubleTypeHandler());
        m.put(Type.BIGDECIMAL, new BigDecimalTypeHandler());
        m.put(Type.DATE, new DateTypeHandler());
        m.put(Type.DOC, new DocTypeHandler());
        m.put(Type.JSON, new JsonTypeHandler());
        m.put(Type.DEFAULT, DEFAULT_TYPE_HANDLER);

        handlers = Maps.immutableEnumMap(m);
    }

    private TypeHandlers() {
    }

    public static TypeHandler get(Type type) {
        return handlers.getOrDefault(type, DEFAULT_TYPE_HANDLER);
    }
}
