package com.teamenchaire.auction.ihm.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.ihm.session.UserSession;
import com.teamenchaire.auction.ihm.util.ServletDispatcher;

/**
 * A {@code Servlet} which handles requests to the page to log out of an
 * account.
 * 
 * @author Ayelen Dumas
 * @author Marin Taverniers
 */
@WebServlet("/account/logout")
public final class LogoutAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        new UserSession(request).close();
        new ServletDispatcher(request, response).redirectToUrl("/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}