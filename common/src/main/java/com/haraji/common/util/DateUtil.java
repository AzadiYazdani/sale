package com.haraji.common.util;

import com.haraji.common.exception.InvalidFormatException;

import java.time.*;

public class DateUtil {

    public static String convertToJalaliDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;

        JalaliCalendar.YearMonthDate jalaliDate = convertToJalaliYearMonthDate(dateTime.toLocalDate());

        return String.format("%04d/%02d/%02d %02d:%02d:%02d",
                jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDay(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }

    public static JalaliCalendar.YearMonthDate convertToJalaliYearMonthDate(LocalDate localDate) {
        if (localDate == null)
            return null;
        JalaliCalendar.YearMonthDate yearMonthDate = new JalaliCalendar.YearMonthDate(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        JalaliCalendar.YearMonthDate jalaliDate = JalaliCalendar.gregorianToJalali(yearMonthDate);
        jalaliDate.setMonth(jalaliDate.getMonth() + 1);
        return jalaliDate;
    }

    public static LocalDate getLocalDateFromString(String jalaliDate) throws InvalidFormatException {

        String[] numbers = jalaliDate.split("/");
        LocalDate localDate = convertJalaliDateToLocalDate(Integer.parseInt(numbers[2]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[0]));
        return localDate;
    }

    public static LocalDate convertJalaliDateToLocalDate(int year, int month, int day) throws InvalidFormatException {
        if (year < 1200) {
            throw new InvalidFormatException(String.format("Year %s not supported in jalali calendar", year));
        }
        if (month < 1 || month > 12) {
            throw new InvalidFormatException(String.format("Month %s not supported in jalali calendar", month));
        }
        if (day < 1 || day > 31) {
            throw new InvalidFormatException(String.format("Day %s not supported in jalali calendar", day));
        }

        if (month > 6 && day > 30) {
            throw new InvalidFormatException(String.format("Day %s of month %s not supported in jalali calendar", day, month));
        }

//        if (month == 12 && day == 30 && !JalaliCalendar.isLeepYear(year)) {
//            throw new InvalidFormatException(String.format("Day %s of month %s of year %s not supported in jalali calendar", day, month, year));
//        }
        JalaliCalendar.YearMonthDate jalaliDate = new JalaliCalendar.YearMonthDate(year, month - 1, day);
        JalaliCalendar.YearMonthDate gregorianDate = JalaliCalendar.jalaliToGregorian(jalaliDate);
        return LocalDate.of(gregorianDate.getYear(), gregorianDate.getMonth() + 1, gregorianDate.getDay());
    }

}
