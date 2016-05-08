package be.brickbit.lpm.catering.util;

import java.time.format.DateTimeFormatter;

/**
 * Created by jay on 08.05.16.
 */
public class DateUtils {
    public static DateTimeFormatter getDateFormat(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }
}
