package com.teamenchaire.auction.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An utility {@code class} which dispatches servlet requests to other
 * resources.
 * 
 * @author Marin Taverniers
 */
public class ServletDispatcher {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletDispatcher(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void forwardToJsp(String url) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF" + url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            sendError();
        }
    }

    public void redirectToServlet(String url) {
        try {
            response.sendRedirect(request.getContextPath() + url);
        } catch (IOException e) {
            e.printStackTrace();
            sendError();
        }
    }

    public void sendError(int code) {
        try {
            response.sendError(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendError() {
        sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}