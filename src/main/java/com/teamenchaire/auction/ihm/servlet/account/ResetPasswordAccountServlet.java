package com.teamenchaire.auction.ihm.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.ihm.util.ServletDispatcher;

/**
 * A {@code Servlet} which handles requests to the page to reset the password of
 * an account.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/account/reset-password")
public final class ResetPasswordAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        new ServletDispatcher(request, response).forwardToJsp("/pages/account/ResetPassword.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}