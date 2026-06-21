package com.college.erp.collegemanagementsystem.enums;

/**
 * @author grish
 */

public enum ResponseStatus {
    SUCCESS("M0000", "Success"),

    FAILURE("M0001", "Failure"),

    INTERNAL_SERVER_ERROR("M0001", "Internal Server Error. Please contact Administrator"),

    INVALID_SESSION("M0003", "Invalid Session"),

    BAD_REQUEST("M0004", "Bad Request"),

    VALIDATION_FAILED("M0005", "Validation Failed"),

    UNAUTHORIZED_USER("M0006", "Un-authorized User");


    private ResponseStatus() {

    }

    private String key;

    private String value;

    private ResponseStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static ResponseStatus getEnumByValue(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (ResponseStatus v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }

    public static ResponseStatus getEnumByKey(String key) {
        if (key == null)
            throw new IllegalArgumentException();
        for (ResponseStatus v : values())
            if (key.equalsIgnoreCase(v.getKey()))
                return v;
        throw new IllegalArgumentException();
    }
}
