package com.haraji.security.util;

import com.haraji.common.exception.InvalidFormatException;
import com.haraji.security.constant.IdentifierType;

import java.util.regex.Pattern;

public class CommonUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{10,15}$");


    public static void validateIdentifier(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new InvalidFormatException();
        }

        // محدودیت طول برای جلوگیری از حملات
        if (identifier.length() > 100) {
            throw new InvalidFormatException();
        }
    }

    public static IdentifierType detectLoginType(String identifier) {
        String cleanIdentifier = identifier.trim().toLowerCase();

        // اول بررسی می‌کنیم آیا ایمیل معتبر است
        if (EMAIL_PATTERN.matcher(cleanIdentifier).matches()) {
            return IdentifierType.EMAIL;
        }

        // سپس شماره تلفن را بررسی می‌کنیم
        String digitsOnly = cleanIdentifier.replaceAll("[^0-9]", "");
        if (PHONE_PATTERN.matcher(digitsOnly).matches()) {
            return IdentifierType.PHONE;
        }

        // اگر هیچکدام نبود، خطا
        throw new InvalidFormatException();
    }

    public static String normalizeIdentifier(String identifier, IdentifierType type) {
        return switch (type) {
            case EMAIL -> identifier.toLowerCase().trim();
            case PHONE -> {
                // حذف همه غیر اعداد
                String digits = identifier.replaceAll("[^0-9]", "");
                // حذف صفرهای اول در صورت نیاز
                yield normalizePhoneNumber(digits);
            }
        };
    }

    private static String normalizePhoneNumber(String digits) {
        // حذف همه غیر اعداد قبلاً انجام شده، پس digits فقط عدد است.

        // اگر شماره با پیش‌شماره کشوری ایران شروع شده (مثلاً 98 یا +98) آن را حذف کنیم.
        // همچنین اگر با صفر شروع شده، صفر را حذف کنیم.

        // فرض کنیم می‌خواهیم شماره‌های ایرانی را به صورت 10 رقمی (بدون صفر اول) ذخیره کنیم.
        // یعنی: 9123456789

        // اگر شماره با '98' شروع شود (کد کشور ایران) و طول آن 12 رقم باشد، دو رقم اول را حذف می‌کنیم.
        if (digits.startsWith("98") && digits.length() == 12) {
            digits = digits.substring(2);
        }
        // اگر شماره با '0' شروع شود و طول آن 11 رقم باشد، صفر اول را حذف می‌کنیم.
        else if (digits.startsWith("0") && digits.length() == 11) {
            digits = digits.substring(1);
        }
        // در غیر این صورت، همان رقم‌ها را نگه می‌داریم.

        // اطمینان حاصل کنیم که شماره نهایی فقط رقم است و طول مناسبی دارد.
        // می‌توانیم بررسی کنیم که آیا شماره 10 رقم است (برای ایران) یا خیر.
        // اگر نه، ممکن است شماره متعلق به کشور دیگری باشد، که در این صورت شاید بخواهیم همان را ذخیره کنیم.

        return digits;
    }

    private String maskIdentifier(String identifier) {
        // ماسک کردن برای لاگ‌ها
        if (identifier == null) return "null";
        if (identifier.length() <= 3) return "***";

        return identifier.substring(0, 3) + "***" +
                identifier.substring(identifier.length() - 2);
    }
}
