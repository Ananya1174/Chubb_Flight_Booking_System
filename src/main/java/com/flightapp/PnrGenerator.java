package com.flightapp;

import java.security.SecureRandom;

public class PnrGenerator {
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RAND = new SecureRandom();
    private PnrGenerator() {
        throw new UnsupportedOperationException("Utility class – do not instantiate");
    }
    public static String generate(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ALPHANUM.charAt(RAND.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    public static String generatePnr() {
        return "PNR" + generate(7);
    }
}
