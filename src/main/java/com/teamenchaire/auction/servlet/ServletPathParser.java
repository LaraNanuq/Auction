package com.teamenchaire.auction.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@code class} which parses the path of a servlet request.
 * 
 * @author Marin Taverniers
 */
public final class ServletPathParser {
    private HttpServletRequest request;
    private String parameter;

    public ServletPathParser(HttpServletRequest request) {
        this.request = request;
        this.parameter = getParameter();
    }

    public String getString() {
        return parameter;
    }

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