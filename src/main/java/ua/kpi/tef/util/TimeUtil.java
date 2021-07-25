package ua.kpi.tef.util;


import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static boolean isBetween(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}
