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
 * A {@code Filter} which handles requests to pages that require a closed user
 * session.
 * 
 * @author Marin Taverniers
 */
@WebFilter({ "/account/login", "/account/reset-password", "/account/create" })
public final class UserSessionOpenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ServletDispatcher dispatcher = new ServletDispatcher(httpRequest, httpResponse, chain);
        if (new UserSession(httpRequest).isOpen()) {
            dispatcher.redirectToUrl("/account/edit");
        } else {
            dispatcher.invokeNextFilter();
        }
    }
}
