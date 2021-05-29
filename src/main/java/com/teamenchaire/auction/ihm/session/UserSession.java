package com.teamenchaire.auction.ihm.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A {@code class} which manages an user session and its attributes.
 * 
 * @author Marin Taverniers
 */
public final class UserSession {
    private static final int INACTIVE_SESSION_TIMEOUT = 300;
    private HttpSession session;

    /**
     * Constructs an {@code UserSession} associated with the specified servlet
     * request.
     * 
     * @param request The request to which the user session is associated
     */
    public UserSession(HttpServletRequest request) {
        this.session = request.getSession();
    }

    /**
     * Opens this user session and sets the ID of its associated user to the
     * specified value.
     * 
     * @param userId The ID of the user associated with the user session
     */
    public void open(Integer userId) {
        session.setMaxInactiveInterval(INACTIVE_SESSION_TIMEOUT);
        session.setAttribute("isOpen", true);
        session.setAttribute("userId", userId);
    }

    /**
     * Closes this user session and deletes all its attributes.
     */
    public void close() {
        session.invalidate();
    }
    
    /**
     * Returns the ID of the user associated with this user session.
     * 
     * @return the ID of the user associated with the user session.
     */
    public Integer getUserId() {
        Integer userId;
        try {
            userId = (Integer) (session.getAttribute("userId"));
        } catch (ClassCastException e) {
            userId = null;
        }
        return userId;
    }

    /**
     * Checks if this user session is open.
     * 
     * @return {@code true} if the user session is open, {@code false} otherwise.
     */
    public boolean isOpen() {
        Boolean isOpen;
        try {
            isOpen = ((Boolean) session.getAttribute("isOpen"));
            if (isOpen == null) {
                isOpen = false;
            }
        } catch (ClassCastException e) {
            isOpen = false;
        }
        return isOpen.booleanValue();
    }
}