package com.teamenchaire.auction.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.servlet.ServletDispatcher;
import com.teamenchaire.auction.servlet.ServletErrorCode;
import com.teamenchaire.auction.servlet.ServletParameterParser;
import com.teamenchaire.auction.servlet.UserSession;

/**
 * A {@code Servlet} which handles requests to the page to delete an account.
 * 
 * @author Ayelen Dumas
 */
@WebServlet("/account/delete")
public final class DeleteAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        if (!new UserSession(request).isValid()) {
            dispatcher.redirectToServlet("/home");
            return;
        }
        dispatcher.forwardToJsp("/pages/account/Delete.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletParameterParser parser = new ServletParameterParser(request);
        boolean confirmation = parser.getChecked("confirmation");
        UserSession session = new UserSession(request);
        try {
            checkConfirmation(confirmation);
            new UserManager().removeUser(session.getUserId());
            session.close();
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        doGet(request, response);
    }

    private void checkConfirmation(boolean confirmation) throws BusinessException {
        if (!confirmation) {
            throw new BusinessException(ServletErrorCode.ACCOUNT_DELETE_CONFIRMATION_NULL);
        }
    }
}