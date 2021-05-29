package com.teamenchaire.auction.ihm.util;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
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

    /**
     * Constructs a {@code ServletParameterParser} with the specified servlet
     * request.
     * 
     * @param request The servlet request of the parser
     */
    public ServletParameterParser(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.request = request;
    }

    /**
     * Returns the string value of a parameter with the specified name in this
     * servlet request.
     * 
     * @param name The name of the parameter
     * @return the string value of the parameter.
     */
    public String getString(String name) {
        return request.getParameter(name);
    }

    /**
     * Returns the trimmed string value of a parameter with the specified name in
     * this servlet request.
     * 
     * @param name The name of the parameter
     * @return the string value of the parameter, trimmed.
     */
    public String getTrimmedString(String name) {
        String value = request.getParameter(name);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    /**
     * Returns a list of trimmed string values of a parameter with the specified
     * name in this servlet request.
     * 
     * @param name The name of the parameter
     * @return the list of string values of the parameter, trimmed.
     */
    public List<String> getTrimmedStringList(String name) {
        List<String> list = new ArrayList<>();
        String[] values = request.getParameterValues(name);
        if (values != null) {
            for (String value : values) {
                list.add(value.trim());
            }
        }
        return list;
    }

    /**
     * Returns the integer value of a parameter with the specified name in this
     * servlet request.
     * 
     * @param name The name of the parameter
     * @return the integer value of the parameter.
     */
    public Integer getInt(String name) {
        try {
            return Integer.parseInt(request.getParameter(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Returns the boolean value of a parameter with the specified name in this
     * servlet request.
     * 
     * @param name The name of the parameter
     * @return {@code true} if the value of the parameter equals {@code on},
     *         {@code true} or {@code 1}, {@code false} otherwise.
     */
    public boolean getChecked(String name) {
        String value = getTrimmedString(name);
        return ((value != null) && ((value.equalsIgnoreCase("on")) || (value.equalsIgnoreCase("true"))
                || (value.equalsIgnoreCase("1"))));
    }

    /**
     * Returns the date value of a parameter with the specified name in this servlet
     * request.
     * 
     * @param name The name of the parameter
     * @return the date value of the parameter.
     */
    public LocalDate getDate(String name) {
        try {
            return LocalDate.parse(request.getParameter(name));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}