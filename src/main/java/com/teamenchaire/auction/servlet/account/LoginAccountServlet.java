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
        if (request.getSession().getAttribute("user") == null) {
            ServletDispatcher.forwardToJsp(request, response, "/pages/account/Login.jsp");
        } else {
            ServletDispatcher.redirectToServlet(request, response, "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userName = ServletParameterParser.getTrimmedString(request, "userName");
        String password = ServletParameterParser.getString(request, "password");
        boolean rememberMe = ServletParameterParser.getChecked(request, "rememberMe");
        try {
            User user = new UserManager().getUserByUserName(userName, password);
            request.getSession().setAttribute("user", user);
            // TODO : Handle "rememberMe"
            ServletDispatcher.redirectToServlet(request, response, "/home");
            return;
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        request.setAttribute("rememberMe", rememberMe);
        doGet(request, response);
    }
}