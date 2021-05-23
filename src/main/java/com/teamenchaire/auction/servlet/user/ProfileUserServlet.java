package com.teamenchaire.auction.servlet.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@code Servlet} which handles requests to the page to view an user profile.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/user/profile/*")
public final class ProfileUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
    }
}