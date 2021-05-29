package com.teamenchaire.auction.ihm.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.ihm.session.UserSession;
import com.teamenchaire.auction.ihm.util.ServletCookieManager;
import com.teamenchaire.auction.ihm.util.ServletDispatcher;
import com.teamenchaire.auction.ihm.util.ServletParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to log into an account.
 * 
 * @author Ayelen Dumas
 * @author Marin Taverniers
 */
@WebServlet("/account/login")
public final class LoginAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new ServletCookieManager(request, response).getCookie("username");
        if (cookie != null) {
            request.setAttribute("username", cookie.getValue());
            request.setAttribute("rememberMe", true);
        }
        new ServletDispatcher(request, response).forwardToJsp("/pages/account/Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        ServletParameterParser parser = new ServletParameterParser(request);
        String username = parser.getTrimmedString("username");
        String password = parser.getString("password");
        boolean rememberMe = parser.getChecked("rememberMe");
        try {
            User user = new UserManager().getUserByLogin(username, password);
            ServletCookieManager cookieManager = new ServletCookieManager(request, response);
            if (rememberMe) {
                cookieManager.addCookie("username", username);
            } else {
                cookieManager.deleteCookie("username");
            }
            new UserSession(request).open(user.getId());
            dispatcher.redirectToUrl("/home");
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
            request.setAttribute("username", username);
            request.setAttribute("rememberMe", rememberMe);
            dispatcher.forwardToJsp("/pages/account/Login.jsp");
        }
    }
}