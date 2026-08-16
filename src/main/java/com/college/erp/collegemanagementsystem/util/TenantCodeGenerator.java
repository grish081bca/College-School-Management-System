package com.college.erp.collegemanagementsystem.util;

import java.security.SecureRandom;
import java.util.function.Predicate;

/**
 * @author grish
 *
 */
public final class TenantCodeGenerator {
    // Restrict to uppercase alphabet only per requirement (8 characters, letters only)
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 8;
    private static final int MAX_GENERATION_ATTEMPTS = 100;
    private static final SecureRandom RANDOM = new SecureRandom();

    private TenantCodeGenerator() {
    }

    public static String generate() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static String generateUniqueCode(Predicate<String> codeExistsChecker) {
        if (codeExistsChecker == null) {
            throw new IllegalArgumentException("codeExistsChecker must not be null");
        }

        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            String candidate = generate();
            if (!codeExistsChecker.test(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException("Unable to generate a unique tenant code after " + MAX_GENERATION_ATTEMPTS + " attempts");
    }
}
