package com.teamenchaire.auction.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A {@code class} which manages the session of an user and its attributes.
 * 
 * @author Marin Taverniers
 */
public class UserSession {
    private HttpSession session;

    public UserSession(HttpServletRequest request) {
        this.session = request.getSession();
    }

    public Integer getUserId() {
        Integer userId = null;
        try {
            userId = (Integer) (session.getAttribute("userId"));
        } catch (ClassCastException e) {
            userId = null;
        }
        return userId;
    }

    public void setUserId(Integer userId) {
        session.setAttribute("userId", userId);
        session.setAttribute("isValid", true);
    }

    public void close() {
        session.invalidate();
    }

    public boolean isValid() {
        Object oIsValid = session.getAttribute("isValid");
        boolean isValid = false;
        if (oIsValid != null) {
            try {
                isValid = ((boolean) oIsValid);
            } catch (ClassCastException e) {
                isValid = false;
            }
        }
        return isValid;
    }
}