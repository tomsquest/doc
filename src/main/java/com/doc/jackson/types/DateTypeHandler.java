package com.doc.jackson.types;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTypeHandler implements TypeHandler {

    public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public Date read(JsonParser p) throws IOException {
        try {
            return new SimpleDateFormat(ISO_DATETIME_FORMAT, Locale.ENGLISH).parse(p.getText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(JsonGenerator gen, Object value) throws IOException {
        Date date = (Date) value;
        String dateStr = new SimpleDateFormat(ISO_DATETIME_FORMAT, Locale.ENGLISH).format(date);
        gen.writeString(dateStr);
    }
}
