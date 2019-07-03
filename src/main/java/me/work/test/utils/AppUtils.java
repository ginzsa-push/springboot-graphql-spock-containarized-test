package me.work.test.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AppUtils {

    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getNDaysAgoDate(int days) {

        // convert date to localdatetime
        LocalDateTime localDateTime = new Date()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .minusDays(days);

         return dateFormat.format(localDateTime);
    }
}
