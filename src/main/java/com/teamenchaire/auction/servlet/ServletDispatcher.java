package com.teamenchaire.auction.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An utility {@code class} which dispatches servlet requests to other resources.
 * 
 * @author Marin Taverniers
 */
public class ServletDispatcher {

    private ServletDispatcher() {
    }

    public static void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String url) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF" + url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            sendError(response, e);
        }
    }

    public static void redirectToServlet(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            response.sendRedirect(request.getContextPath() + url);
        } catch (IOException e) {
            sendError(response, e);
        }
    }

    private static void sendError(HttpServletResponse response, Throwable cause) {
        cause.printStackTrace();
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}