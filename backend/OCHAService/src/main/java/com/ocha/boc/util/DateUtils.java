package com.ocha.boc.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {
    private static final String CURRENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS+07:00";

    public DateUtils() {
    }

    public static void main(String [] args) throws Exception {
        String date = getCurrentDate();
        System.err.println(date);
        Long longDate = stringDateToLong(date, null, CURRENT_DATE_FORMAT);
        System.err.println(longDate);
    }

    public static String getCurrentDate() throws Exception {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE_FORMAT);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static Long stringDateToLong(String sDate, String timezone, String format) {
        if (timezone == null) {
            timezone = "UTC";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(java.util.TimeZone.getTimeZone(timezone));
        Date date = null;
        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            log.error("Exception while parsing string datetime {} to milliseconds", sDate, e);
        }
        return date != null ? date.getTime() : 0L;
    }
}
