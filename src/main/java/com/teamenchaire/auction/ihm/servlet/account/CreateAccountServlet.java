package com.teamenchaire.auction.ihm.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.ihm.ServletErrorCode;
import com.teamenchaire.auction.ihm.session.UserSession;
import com.teamenchaire.auction.ihm.util.ServletDispatcher;
import com.teamenchaire.auction.ihm.util.ServletParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to create an account.
 * 
 * @author Ayelen Dumas
 * @author Marin Taverniers
 */
@WebServlet("/account/create")
public final class CreateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        dispatcher.forwardToJsp("/pages/account/Create.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletParameterParser parser = new ServletParameterParser(request);
        String nickname = parser.getTrimmedString("nickname");
        String lastName = parser.getTrimmedString("lastName");
        String firstName = parser.getTrimmedString("firstName");
        String email = parser.getTrimmedString("email");
        String phoneNumber = parser.getTrimmedString("phoneNumber");
        String street = parser.getTrimmedString("street");
        String postalCode = parser.getTrimmedString("postalCode");
        String city = parser.getTrimmedString("city");
        String password = parser.getString("password");
        String passwordCheck = parser.getString("passwordCheck");
        try {
            checkPassword(password, passwordCheck);
            User user = new UserManager().addUser(nickname, lastName, firstName, email, password, phoneNumber, street,
                    postalCode, city);
            new UserSession(request).open(user.getId());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
            request.setAttribute("nickname", nickname);
            request.setAttribute("lastName", lastName);
            request.setAttribute("firstName", firstName);
            request.setAttribute("email", email);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("street", street);
            request.setAttribute("postalCode", postalCode);
            request.setAttribute("city", city);
        }
        doGet(request, response);
    }

    private void checkPassword(String password, String passwordCheck) throws BusinessException {
        if ((password != null) && (!password.equals(passwordCheck))) {
            throw new BusinessException(ServletErrorCode.ACCOUNT_SET_PASSWORD_CHECK_INVALID);
        }
    }
}