package com.teamenchaire.auction.servlet.account;

import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.servlet.ServletDispatcher;
import com.teamenchaire.auction.servlet.ServletParameterParser;
import com.teamenchaire.auction.servlet.UserSession;

/**
 * A {@code Servlet} which handles requests to the page to log into an account.
 * 
 * @author Ayelen Dumas
 */
@WebServlet("/account/login")
public final class LoginAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        if (!new UserSession(request).isValid()) {
            dispatcher.forwardToJsp("/pages/account/Login.jsp");
        } else {
            dispatcher.redirectToServlet("/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletParameterParser parser = new ServletParameterParser(request);
        String userName = parser.getTrimmedString("userName");
        String password = parser.getString("password");
        boolean rememberMe = parser.getChecked("rememberMe");
        try {
            User user = new UserManager().getUserByUserName(userName, password);
            new UserSession(request).setUserId(user.getId());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
            request.setAttribute("userName", userName);
            request.setAttribute("password", password);
            request.setAttribute("rememberMe", rememberMe);
        }
        doGet(request, response);
    }
}