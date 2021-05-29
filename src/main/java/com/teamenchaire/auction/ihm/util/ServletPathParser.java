package com.teamenchaire.auction.ihm.util;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@code class} which parses the path of a servlet request.
 * 
 * @author Marin Taverniers
 */
public final class ServletPathParser {
    private HttpServletRequest request;
    private String parameter;

    /**
     * Constructs a {@code ServletPathParser} with the specified servlet request.
     * 
     * @param request The servlet request of the parser
     */
    public ServletPathParser(HttpServletRequest request) {
        this.request = request;
        this.parameter = getParameter();
    }

    /**
     * Returns the string value of the extra path of this servlet request.
     * 
     * @return the string value of the extra path, without the {@code /} character.
     */
    public String getString() {
        return parameter;
    }

    /**
     * Returns the integer value of the extra path of this servlet request.
     * 
     * @return the integer value of the extra path, without the {@code /} character.
     */
    public Integer getInt() {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getParameter() {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            pathInfo = pathInfo.substring(1);
            if (pathInfo.endsWith("/")) {
                pathInfo = pathInfo.substring(0, pathInfo.length() - 1);
            }
        }
        return pathInfo;
    }
}