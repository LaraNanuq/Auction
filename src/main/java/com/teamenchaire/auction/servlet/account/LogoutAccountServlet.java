package com.teamenchaire.auction.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.servlet.ServletDispatcher;

/**
 * A {@code Servlet} which handles requests to the page to log out of an
 * account.
 * 
 * @author Ayelen Dumas
 */
@WebServlet("/account/logout")
public final class LogoutAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        ServletDispatcher.redirectToServlet(request, response, "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}