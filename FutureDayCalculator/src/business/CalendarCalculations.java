package business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalendarCalculations {
    public static long calculateFutureDays(LocalDate startDate, LocalDate endDate) {
        long weekDays = 0;
        if (startDate.isBefore(endDate)) {
            while (startDate.isBefore(endDate)) {
                startDate = startDate.plus(1, ChronoUnit.DAYS);
                if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++weekDays;
                }
            }
        } else {
            while (startDate.isAfter(endDate)) {
                startDate = startDate.minus(1, ChronoUnit.DAYS);
                if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++weekDays;
                }
            }
        }
        return weekDays;
    }

    public static long calculateFutureDays(LocalDate startDate, int daysOut) {
        long weekDays = 0;
        int counter = 0;
        if (daysOut > counter) {
            while (counter < daysOut) {
                startDate = startDate.plus(1, ChronoUnit.DAYS);
                if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++weekDays;
                }
                ++counter;
            }
        } else {
            while (counter > daysOut) {
                startDate = startDate.minus(1, ChronoUnit.DAYS);
                if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++weekDays;
                }
                --counter;
            }
        }
        return weekDays;
    }
}
