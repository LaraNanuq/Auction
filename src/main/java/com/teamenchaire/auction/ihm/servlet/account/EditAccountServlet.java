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
 * A {@code Servlet} which handles requests to the page to edit an account.
 * 
 * @author Ayelen Dumas
 * @author Marin Taverniers
 */
@WebServlet("/account/edit")
public final class EditAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        UserSession session = new UserSession(request);
        try {
            User user = new UserManager().getUserById(session.getUserId());
            request.setAttribute("nickname", user.getNickname());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("phoneNumber", user.getPhoneNumber());
            request.setAttribute("street", user.getStreet());
            request.setAttribute("postalCode", user.getPostalCode());
            request.setAttribute("city", user.getCity());
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        dispatcher.forwardToJsp("/pages/account/Edit.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        ServletParameterParser parser = new ServletParameterParser(request);
        Integer userId = new UserSession(request).getUserId();
        String nickname = parser.getTrimmedString("nickname");
        String lastName = parser.getTrimmedString("lastName");
        String firstName = parser.getTrimmedString("firstName");
        String email = parser.getTrimmedString("email");
        String phoneNumber = parser.getTrimmedString("phoneNumber");
        String street = parser.getTrimmedString("street");
        String postalCode = parser.getTrimmedString("postalCode");
        String city = parser.getTrimmedString("city");
        String oldPassword = parser.getString("oldPassword");
        String newPassword = parser.getString("newPassword");
        String newPasswordCheck = parser.getString("newPasswordCheck");
        try {
            if ((newPassword != null) && (!newPassword.isEmpty())) {
                checkNewPassword(newPassword, newPasswordCheck);
            } else {
                newPassword = oldPassword;
            }
            checkPassword(oldPassword, userId);
            new UserManager().updateUser(userId, nickname, lastName, firstName, email, newPassword, phoneNumber, street,
                    postalCode, city);
            dispatcher.redirectToUrl("/user/profile/" + nickname);
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
            dispatcher.forwardToJsp("/pages/account/Edit.jsp");
        }
    }

    private void checkNewPassword(String newPassword, String newPasswordCheck) throws BusinessException {
        if ((newPassword != null) && (!newPassword.equals(newPasswordCheck))) {
            throw new BusinessException(ServletErrorCode.ACCOUNT_SET_PASSWORD_CHECK_INVALID);
        }
    }

    private void checkPassword(String oldPassword, Integer userId) throws BusinessException {
        User user = new UserManager().getUserById(userId);
        if ((oldPassword == null) || (!oldPassword.equals(user.getPassword()))) {
            throw new BusinessException(ServletErrorCode.ACCOUNT_EDIT_OLD_PASSWORD_INVALID);
        }
    }
}