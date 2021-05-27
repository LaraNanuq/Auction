package com.teamenchaire.auction.servlet;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@code class} which parses parameters of a servlet request.
 * 
 * @author Marin Taverniers
 */
public final class ServletParameterParser {
    private HttpServletRequest request;

    public ServletParameterParser(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.request = request;
    }

    public String getString(String parameter) {
        return request.getParameter(parameter);
    }

    public String getTrimmedString(String parameter) {
        String value = request.getParameter(parameter);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    public List<String> getTrimmedStringList(String parameter) {
        List<String> list = new ArrayList<>();
        String[] values = request.getParameterValues(parameter);
        if (values != null) {
            for (String value : values) {
                list.add(value.trim());
            }
        }
        return list;
    }

    public Integer getInt(String parameter) {
        try {
            return Integer.parseInt(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean getChecked(String parameter) {
        String value = getTrimmedString(parameter);
        return ((value != null) && ((value.equalsIgnoreCase("on")) || (value.equalsIgnoreCase("true"))));
    }

    public LocalDate getDate(String parameter) {
        try {
            return LocalDate.parse(request.getParameter(parameter));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public LocalTime getTime(String parameter) {
        try {
            return LocalTime.parse(request.getParameter(parameter));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}