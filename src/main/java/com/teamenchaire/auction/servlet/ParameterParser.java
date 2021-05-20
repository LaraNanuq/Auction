package com.teamenchaire.auction.servlet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

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
}