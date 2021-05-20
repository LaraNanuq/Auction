package com.teamenchaire.auction.servlet;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class StringParser {
    
    private StringParser() {
    }

    public static LocalDate parseDate(final String s) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(s);
        } catch (final DateTimeParseException e) {
            //
        }
        return date;
    }

    public static Integer parseInt(final String s) {
        Integer number = null;
        try {
            number = Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            //
        }
        return number;
    }
}