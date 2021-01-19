package com.exercise.cfs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parse(String date) throws ParseException {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return FORMATTER.parse(date);
    }

}
