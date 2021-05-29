package com.teamenchaire.auction.ihm.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@code class} which manages cookies of a servlet.
 * 
 * @author Marin Taverniers
 */
public final class ServletCookieManager {
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs a {@code ServletCookieManager} with specified servlet request and
     * response.
     * 
     * @param request  The servlet request of the cookie
     * @param response The servlet response of the cookie
     */
    public ServletCookieManager(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Adds a cookie to this servlet response with specified name and value, or
     * overwrites it if it already exists.
     * 
     * @param name  The name of the cookie
     * @param value The value of the cookie
     */
    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * Deletes the cookie of this servlet request with the specified name.
     * 
     * @param name The name of the cookie
     */
    public void deleteCookie(String name) {
        Cookie cookie = getCookie(name);
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * Returns the cookie of this servlet request with the specified name.
     * 
     * @param name The name of the cookie
     * @return the cookie of the servlet request.
     */
    public Cookie getCookie(String name) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie requestCookie : cookies) {
                if (requestCookie.getName().equalsIgnoreCase(name)) {
                    cookie = requestCookie;
                    break;
                }
            }
        }
        return cookie;
    }
}