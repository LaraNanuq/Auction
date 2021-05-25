package com.teamenchaire.auction.servlet.account;

import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.servlet.ServletDispatcher;
import com.teamenchaire.auction.servlet.ServletErrorCode;
import com.teamenchaire.auction.servlet.ServletParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to edit an account.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/account/edit")
public final class EditAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            // Utilisation des getters plutôt que de "${sessionScope.user.?}",
            // puisque le formulaire doit garder les valeurs saisies par l'utilisateur
            request.setAttribute("nickname", user.getNickname());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("phoneNumber", user.getPhoneNumber());
            request.setAttribute("street", user.getStreet());
            request.setAttribute("postalCode", user.getPostalCode());
            request.setAttribute("city", user.getCity());
            ServletDispatcher.forwardToJsp(request, response, "/pages/account/Edit.jsp");
        } else {
            ServletDispatcher.redirectToServlet(request, response, "/account/login");
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
		String oldPassword = ServletParameterParser.getString(request, "oldPassword");
		String newPassword = ServletParameterParser.getString(request, "newPassword");
		String newPasswordCheck = ServletParameterParser.getString(request, "newPasswordCheck");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            if (!oldPassword.equals(user.getPassword())) {
                throw new BusinessException(ServletErrorCode.ACCOUNT_EDIT_OLD_PASSWORD_INVALID);
            }
            // Le changement de mot de passe peut rester vide, on ne le vérifie que si le champ a été rempli
            if ((newPassword != null) && (!newPassword.isEmpty())) {
                if (!newPassword.equals(newPasswordCheck)) {
                    throw new BusinessException(ServletErrorCode.ACCOUNT_CREATE_PASSWORD_CHECK_INVALID);
                }
            } else {
                newPassword = oldPassword;
            }
            user = new UserManager().updateUser(user, nickname, lastName, firstName, email, newPassword, phoneNumber, street, postalCode, city);
            session.setAttribute("user", user);
            ServletDispatcher.redirectToServlet(request, response, "/home");
            return;
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }

        // Ne pas appeler le doGet, puisqu'en cas d'erreur les dernières valeurs saisies doivent persister
        request.setAttribute("nickname", nickname);
        request.setAttribute("lastName", lastName);
        request.setAttribute("firstName", firstName);
        request.setAttribute("email", email);
        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("street", street);
        request.setAttribute("postalCode", postalCode);
        request.setAttribute("city", city);
        request.setAttribute("newPassword", newPassword);
        ServletDispatcher.forwardToJsp(request, response, "/pages/account/Edit.jsp");
    }
}