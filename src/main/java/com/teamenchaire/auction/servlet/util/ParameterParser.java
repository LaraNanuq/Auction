package com.teamenchaire.auction.servlet.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

/**
 * An utility {@code class} which parses parameters for HTTP requests.
 * 
 * @author Marin Taverniers
 */
public final class ParameterParser {

    private ParameterParser() {
    }

    public static String getString(HttpServletRequest request, String parameter) {
        return request.getParameter(parameter);
    }

    public static String getTrimmedString(HttpServletRequest request, String parameter) {
        String value = request.getParameter(parameter);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    public static Integer getInt(HttpServletRequest request, String parameter) {
        try {
            return Integer.parseInt(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static LocalDate getDate(HttpServletRequest request, String parameter) {
        try {
            return LocalDate.parse(request.getParameter(parameter));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalTime getTime(HttpServletRequest request, String parameter) {
        try {
            return LocalTime.parse(request.getParameter(parameter));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static String[] getTrimmedStringArray(HttpServletRequest request, String parameter) {
        String[] values = request.getParameterValues(parameter);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
        }
        return values;
    }
}