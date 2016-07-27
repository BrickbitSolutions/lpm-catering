package be.brickbit.lpm.catering.util;

import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static DateTimeFormatter getDateFormat(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }
}
