package com.teamenchaire.auction.ihm.session;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.ihm.util.ServletDispatcher;

/**
 * A {@code Filter} which handles requests to pages that require an open user
 * session.
 * 
 * @author Marin Taverniers
 */
@WebFilter({ "/account/edit", "/account/delete", "/sale/*" })
public final class UserSessionNotOpenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ServletDispatcher dispatcher = new ServletDispatcher(httpRequest, httpResponse, chain);
        if (!new UserSession(httpRequest).isOpen()) {
            dispatcher.redirectToTemporaryUrl("/account/login");
        } else {
            dispatcher.invokeNextFilter();
        }
    }
}
