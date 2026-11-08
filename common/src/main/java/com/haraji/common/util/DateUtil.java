package com.haraji.common.util;

import com.haraji.common.exception.InvalidFormatException;

import java.time.LocalDate;

public class DateUtil {

    public static String convertToJalaliDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) return null;

        JalaliCalendar.YearMonthDate jalaliDate = convertToJalaliYearMonthDate(dateTime.toLocalDate());

        return String.format("%04d/%02d/%02d %02d:%02d:%02d",
                jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDay(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }

    public static JalaliCalendar.YearMonthDate convertToJalaliYearMonthDate(LocalDate localDate) {
        if (localDate == null) return null;

        JalaliCalendar.YearMonthDate yearMonthDate = new JalaliCalendar.YearMonthDate(
                localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());

        JalaliCalendar.YearMonthDate jalaliDate = JalaliCalendar.gregorianToJalali(yearMonthDate);
        jalaliDate.setMonth(jalaliDate.getMonth() + 1);

        return jalaliDate;
    }

    public static LocalDate getLocalDateFromString(String jalaliDate) throws InvalidFormatException {
        if (jalaliDate == null || jalaliDate.isBlank()) {
            throw new InvalidFormatException("Jalali date is required.");
        }

        String[] numbers = jalaliDate.trim().split("/", -1);

        if (numbers.length != 3) {
            throw new InvalidFormatException("Invalid Jalali date format. Expected yyyy/MM/dd.");
        }

        try {
            return convertJalaliDateToLocalDate(
                    Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1]),
                    Integer.parseInt(numbers[2]));
        } catch (NumberFormatException exception) {
            throw new InvalidFormatException("Invalid Jalali date format.");
        }
    }

    public static LocalDate convertJalaliDateToLocalDate(int year, int month, int day) throws InvalidFormatException {
        if (year < 1200) {
            throw new InvalidFormatException(String.format("Year %s not supported in jalali calendar", year));
        }

        if (month < 1 || month > 12) {
            throw new InvalidFormatException(String.format("Month %s not supported in jalali calendar", month));
        }

        int maxDay = month <= 6 ? 31 : month <= 11 ? 30 : JalaliCalendar.isLeepYear(year) ? 30 : 29;

        if (day < 1 || day > maxDay) {
            throw new InvalidFormatException(String.format("Day %s of month %s of year %s not supported in jalali calendar", day, month, year));
        }

        JalaliCalendar.YearMonthDate jalaliDate = new JalaliCalendar.YearMonthDate(year, month - 1, day);
        JalaliCalendar.YearMonthDate gregorianDate = JalaliCalendar.jalaliToGregorian(jalaliDate);

        return LocalDate.of(gregorianDate.getYear(), gregorianDate.getMonth() + 1, gregorianDate.getDay());
    }
}
