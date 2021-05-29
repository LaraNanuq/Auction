package com.teamenchaire.auction.ihm.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@code class} which dispatches servlet request and response to other
 * resources.
 * 
 * @author Marin Taverniers
 */
public final class ServletDispatcher {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    /**
     * Constructs a {@code ServletDispatcher} with specified servlet request and
     * response.
     * 
     * @param request  The servlet request of the dispatcher
     * @param response The servlet response of the dispatcher
     */
    public ServletDispatcher(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, null);
    }

    /**
     * Constructs a {@code ServletDispatcher} with specified servlet request,
     * response and filter chain.
     * 
     * @param request  The servlet request of the dispatcher
     * @param response The servlet response of the dispatcher
     * @param chain    The filter chain of the dispatcher
     */
    public ServletDispatcher(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        this.request = request;
        this.response = response;
        this.chain = chain;
    }

    /**
     * Forwards the servlet request of this dispatcher to the JSP located at the
     * specified URL.
     * 
     * @param url The URL of the JSP to which redirect the servlet request to,
     *            without the "/WEB-INF" path
     */
    public void forwardToJsp(String url) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF" + url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            sendError();
        }
    }

    /**
     * Redirects the client of this dispatcher to the specified URL, by setting a
     * {@code redirectTo} request parameter containing the current URL, which must
     * be manually maintained between queries.
     * 
     * @param url The URL to which redirect the client to, without the server path
     */
    public void redirectToTemporaryUrl(String url) {
        try {
            url += "?redirectTo=" + URLEncoder.encode(request.getServletPath(), "UTF-8");
            response.sendRedirect(request.getContextPath() + url);
        } catch (IOException e) {
            e.printStackTrace();
            sendError();
        }
    }

    /**
     * Redirects the client of this dispatcher to any pending URL read from a
     * {@code redirectTo} request parameter, or to the specified URL if there is no
     * pending redirection.
     * 
     * @param url The URL to which redirect the client to, without the server path
     */
    public void redirectToUrl(String url) {
        String pendingUrl = new ServletParameterParser(request).getString("redirectTo");
        if ((pendingUrl != null) && (!pendingUrl.isEmpty())) {
            url = pendingUrl;
        }
        try {
            response.sendRedirect(request.getContextPath() + url);
        } catch (IOException e) {
            e.printStackTrace();
            sendError();
        }
    }

    /**
     * Invokes the next filter in the chain of this dispatcher, if it exists.
     */
    public void invokeNextFilter() {
        if (chain != null) {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
                sendError();
            }
        }
    }

    /**
     * Sends an error to the client of this dispatcher with the specified code.
     * 
     * @param code The error code to send
     */
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