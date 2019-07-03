package com.ocha.boc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
    public static Long  getCurrentUnixEpochTime() {
        return System.currentTimeMillis()/1000;
    }

    public static Boolean isFutureDay(Long userTime, String timezoneId) throws Exception {
        Long currentUnixTime = DateUtils.getCurrentUnixEpochTime(timezoneId);
        if(userTime <= currentUnixTime) {
            return false;
        }
        return true;
    }
    public static Boolean isNowBeforeMinutes(Long userTime, Long minutes) {
        Long currentUnixTime = DateUtils.getCurrentUnixEpochTime();
        if((userTime - currentUnixTime) > minutes ) {
            return true;
        }
        return false;
    }
    public static Boolean isNowBeforeMinutes(Long startTime, Long minutes, String timezoneId) throws  Exception {
        if(isFutureDay(startTime, timezoneId) == false) {
            logger.error("Your time is not VALID");
            return false;
        }
        Long currentUnixTime = DateUtils.getCurrentUnixEpochTime();
        String currentDate = unixEpochTimeToStrDate(currentUnixTime, timezoneId);
        currentUnixTime = strDateToUnixEpochTime(currentDate, timezoneId);
        Long count = currentUnixTime - startTime;
        if(count > 0 && (count < minutes*60) ) {
            return false;
        }
        return true;
    }

    public static String minutesToTime(Long minutes) {
        String startTime = "00:00";
        Long h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
        Long m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
        String sMinutes = new Long(m).toString();
        if(m < 10) {
            sMinutes = "0" + sMinutes;
        }
        String newTime = h+":"+sMinutes;
        logger.debug("newTime: " + newTime);
        return newTime;
    }
    public static Long sTimeToMinutes(String sTime) {
        String[] tmp = sTime.split(":");
        Integer hours = Integer.parseInt(tmp[0]);
        Long minutes = Long.parseLong(tmp[1]) + hours*60;
        return minutes;
    }
    public static Long getCurrentUnixEpochTime(String timezone) {
        try {
            Long current = getCurrentTimeMillis() / 1000;
            logger.debug("epoch {}", current);
            String formattedDate = unixEpochTimeToStrDate(current, timezone);
            logger.debug("formattedDate {}", formattedDate);
            return strDateToUnixEpochTime(formattedDate, timezone);
        }
        catch (Exception e) {
            logger.error("getCurrentUnixEpochTime error", e.getMessage());
            return 0L;
        }
    }
    public static String unixEpochTimeToStrDate(Long unixSeconds) {
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        logger.debug("formattedDate {}",  formattedDate);
        return formattedDate;
    }
    public static String unixEpochTimeToStrDate(Long unixSeconds, String timezone) {
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String unixEpochTimeToCalendarStrDate(Long unixSeconds, String timezone) {
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String unixEpochTimeToCalendarStrDate(Long unixSeconds, String timezone, String format) {
        if(timezone == null) {
            timezone = "UTC";
        }
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static Long strDateToUnixEpochTime(String str, String timezone, String format)  {
        try {
            if (timezone == null) {
                timezone = "UTC";
            }
            SimpleDateFormat df = new SimpleDateFormat(format);
            df.setTimeZone(TimeZone.getTimeZone(timezone));
            Date date = df.parse(str);
            long epoch = date.getTime() / 1000;
            System.out.println(epoch);
            return epoch;
        }
        catch (Exception e) {
            logger.error("strDateToUnixEpochTime error", e.getMessage());
            return 0L;
        }
    }

    public static Long strDateToUnixEpochTime(String str, String timezone) {
        try {
            if (timezone == null) {
                timezone = "UTC";
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone(timezone));
            Date date = df.parse(str);
            long epoch = date.getTime() / 1000;
            System.out.println(epoch);
            return epoch;
        } catch (Exception e) {
            logger.error("strDateToUnixEpochTime error", e.getMessage());
            return 0L;
        }
    }
    private static Date unixEpochTimeToShortDate(Long unixSeconds, String timezone) throws ParseException {
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strTargetDate = dateFormat.format(date);
        Date targetDate = dateFormat.parse(strTargetDate);
        logger.debug("targetDate: {}", targetDate.toString());
        return targetDate;
    }
    public static Date unixEpochTimeToDate(Long unixSeconds, String timezone) throws ParseException {
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String formattedDate = sdf.format(date);
        Date targetDate = sdf.parse(formattedDate);
        return targetDate;
    }

    public static boolean isWeekendDay(Long iDate, String timezone) throws Exception {
        Boolean ret = Boolean.FALSE;
        int iDayOfWeek = getDayOfWeek(iDate, timezone);
        if(iDayOfWeek == Calendar.SATURDAY || iDayOfWeek == Calendar.SUNDAY) {
            ret = Boolean.TRUE;
        }
        return ret;
    }

    public static List<String> showTimeZoneAvailableIDs() {
        List<String> timeZones = Arrays.asList(TimeZone.getAvailableIDs());
        for (String x : timeZones) {
            logger.debug("timezone {}", x);
        }
        return timeZones;
    }
    public static List<String> getTimeZoneAvailableIDs() {
        List<String> timeZones = Arrays.asList(TimeZone.getAvailableIDs());
        return timeZones;
    }
    public  static boolean isValidTimeZone(String timeZoneId) {
        String[] validIDs = TimeZone.getAvailableIDs();
        for (String str : validIDs) {
            if (str != null && str.equals(timeZoneId)) {
                logger.debug("Valid ID");
                return true;
            }
        }
        logger.warn("Invalid Timezone ID");
        return false;
    }

    public static List<Long> getDatesBetweenDates(Long iStartDate, Long iEndDate, String timeZone) throws ParseException
    {
        Date startDate = unixEpochTimeToDate(iStartDate, timeZone);
        Date endDate = unixEpochTimeToDate(iEndDate, timeZone);
        List<Long> dates = new ArrayList<Long>();
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        calendar.setTimeZone(tz);
        calendar.setTime(startDate);
        while (calendar.getTime().before(endDate))
        {
            Date result = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            //check whether date is weekend
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            if(weekDay == Calendar.SUNDAY || weekDay == Calendar.SATURDAY) {
                logger.debug("getEpochesBetweenDates ignored date {}", calendar.getTime());
            }
            else {
                dates.add(result.getTime()/1000);
            }
        }
        Date result = calendar.getTime();
        dates.add(result.getTime()/1000);
        return dates;
    }
    public static int getDayOfWeek(Long iStartDate, String timeZone) throws ParseException
    {
        Date startDate = unixEpochTimeToDate(iStartDate, timeZone);
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        calendar.setTimeZone(tz);
        calendar.setTime(startDate);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay;
    }
    public static boolean isWeekend(Long iStartDate, String timeZone) throws ParseException
    {
        Date startDate = unixEpochTimeToDate(iStartDate, timeZone);
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        calendar.setTimeZone(tz);
        calendar.setTime(startDate);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if(weekDay == Calendar.SUNDAY || weekDay == Calendar.SATURDAY) {
            logger.debug("weekend date {}", calendar.getTime());
            return true;
        }
        return false;
    }
    public static boolean isToday(Long epochTime, String timezoneId) throws Exception {
        Long iCurrentDate = getCurrentUnixEpochTime(timezoneId);
        Date today = unixEpochTimeToShortDate(iCurrentDate, timezoneId);
        Date targetDate = unixEpochTimeToShortDate(epochTime, timezoneId);
        if(today.compareTo(targetDate) == 0) {
            return true;
        }
        return false;
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();
    }

    public static void main(String args[]) throws Exception {

    }
}
