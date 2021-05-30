package com.teamenchaire.auction.ihm.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.BLLErrorCode;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.ihm.ServletErrorCode;
import com.teamenchaire.auction.ihm.util.ServletDispatcher;
import com.teamenchaire.auction.ihm.util.ServletParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to reset the password of
 * an account.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/account/reset-password")
public final class ResetPasswordAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        dispatcher.forwardToJsp("/pages/account/ResetPassword.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletParameterParser parser = new ServletParameterParser(request);
        String email = parser.getTrimmedString("email");
        String newPassword = parser.getString("newPassword");
        String newPasswordCheck = parser.getString("newPasswordCheck");
        try {
            checkPassword(newPassword, newPasswordCheck);
            UserManager userManager = new UserManager();
            User user = userManager.getUserByEmail(email);
            if (user == null) {
                throw new BusinessException(BLLErrorCode.USER_UNKNOWN);
            }
            userManager.updateUser(user.getId(), user.getNickname(), user.getLastName(), user.getFirstName(), user.getEmail(), newPassword, user.getPhoneNumber(), user.getStreet(), user.getPostalCode(), user.getCity());
            request.setAttribute("passwordUpdated", true);
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
            request.setAttribute("email", email);
        }
        doGet(request, response);
    }

    private void checkPassword(String password, String passwordCheck) throws BusinessException {
        if ((password != null) && (!password.equals(passwordCheck))) {
            throw new BusinessException(ServletErrorCode.ACCOUNT_SET_PASSWORD_CHECK_INVALID);
        }
    }
}