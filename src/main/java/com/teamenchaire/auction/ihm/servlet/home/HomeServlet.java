package com.teamenchaire.auction.ihm.servlet.home;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.ihm.util.ServletDispatcher;

/**
 * A {@code Servlet} which handles requests to the home page.
 * 
 * @author Marin Taverniers
 */
@WebServlet(urlPatterns = { "", "/index", "/home" })
public final class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        new ServletDispatcher(request, response).redirectToUrl("/auction/list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}