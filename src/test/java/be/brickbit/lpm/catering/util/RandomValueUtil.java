package be.brickbit.lpm.catering.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RandomValueUtil {
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String randomString(){
        return randomString(10);
    }

    public static String randomEmail(){
        return randomString(15) + "@mail.com";
    }

    public static Integer randomInt(){
        return randomInt(0, Integer.MAX_VALUE);
    }

    public static Integer randomInt(int min, int max){
        return RandomUtils.nextInt(min, max);
    }

    public static BigDecimal randomDecimal(){
        return randomDecimal(0.0, Double.MAX_VALUE);
    }

    public static BigDecimal randomDecimal(Double min, Double max){
        return BigDecimal.valueOf(RandomUtils.nextDouble(min, max));
    }

    public static Long randomLong(){
        return randomLong(0, Long.MAX_VALUE);
    }

    public static Long randomLong(long min, long max){
        return RandomUtils.nextLong(min, max);
    }

    public static LocalDate randomLocalDate(){
        return LocalDate.now().plusDays(randomLong(0, 1024));
    }

    public static LocalDateTime randomLocalDateTime(){
        return LocalDateTime.now().plusHours(randomInt()).plusMinutes(randomInt()).plusSeconds(randomInt());
    }
}
