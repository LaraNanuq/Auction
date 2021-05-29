package com.teamenchaire.auction.ihm.servlet.sale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.ihm.util.ServletDispatcher;

/**
 * A {@code Servlet} which handles requests to the page to delete a sale.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/sale/delete/*")
public final class DeleteSaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        new ServletDispatcher(request, response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}