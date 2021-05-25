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
import com.teamenchaire.auction.servlet.ServletErrorCode;
import com.teamenchaire.auction.servlet.ServletParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to create an account.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/account/create")
public final class CreateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") == null) {
            ServletDispatcher.forwardToJsp(request, response, "/pages/account/Create.jsp");
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
		String nickname = ServletParameterParser.getTrimmedString(request, "nickname");
		String lastName = ServletParameterParser.getTrimmedString(request, "lastName");
		String firstName = ServletParameterParser.getTrimmedString(request, "firstName");
		String email = ServletParameterParser.getTrimmedString(request, "email");
		String phoneNumber = ServletParameterParser.getTrimmedString(request, "phoneNumber");
		String street = ServletParameterParser.getTrimmedString(request, "street");
		String postalCode = ServletParameterParser.getTrimmedString(request, "postalCode");
		String city = ServletParameterParser.getTrimmedString(request, "city");
		String password = ServletParameterParser.getString(request, "password");
		String passwordCheck = ServletParameterParser.getString(request, "passwordCheck");
        try {
            if (!password.equals(passwordCheck)) {
                throw new BusinessException(ServletErrorCode.ACCOUNT_CREATE_PASSWORD_CHECK_INVALID);
            }
            User user = new UserManager().addUser(nickname, lastName, firstName, email, password, phoneNumber, street, postalCode, city);
            request.getSession().setAttribute("user", user);
            ServletDispatcher.redirectToServlet(request, response, "/home");
            return;
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        request.setAttribute("nickname", nickname);
        request.setAttribute("lastName", lastName);
        request.setAttribute("firstName", firstName);
        request.setAttribute("email", email);
        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("street", street);
        request.setAttribute("postalCode", postalCode);
        request.setAttribute("city", city);
        request.setAttribute("password", password);
        doGet(request, response);
    }
}