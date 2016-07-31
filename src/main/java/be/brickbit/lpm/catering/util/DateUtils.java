package be.brickbit.lpm.catering.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static DateTimeFormatter getDateFormat(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    public static Long calculateDifference(LocalDateTime startDate, LocalDateTime endDate){
        return startDate.until(endDate, ChronoUnit.SECONDS);
    }
}