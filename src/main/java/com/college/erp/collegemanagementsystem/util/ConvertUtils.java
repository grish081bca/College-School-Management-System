package com.college.erp.collegemanagementsystem.util;

/**
 * @author grish
 */

public final class ConvertUtils {

    public ConvertUtils() {}

    public static String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String finalValue = value.trim();
        return finalValue.isEmpty() ? null : finalValue;
    }

    public static String normalizeToLowerCase(String value) {
        String normalized = normalizeText(value);
        return normalized == null ? null : normalized.toLowerCase();
    }

    public static String normalizeToUppercase(String value) {
        String normalized = normalizeText(value);
        return normalized == null ? null : normalized.toUpperCase();
    }
}
