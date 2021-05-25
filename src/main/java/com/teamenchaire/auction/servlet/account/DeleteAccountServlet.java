package com.teamenchaire.auction.servlet.account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.servlet.ServletDispatcher;
import com.teamenchaire.auction.servlet.ServletErrorCode;
import com.teamenchaire.auction.servlet.ServletParameterParser;

/**
 * A {@code Servlet} which handles requests to the page to delete an account.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/account/delete")
public final class DeleteAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("userId") != null) {
            ServletDispatcher.forwardToJsp(request, response, "/pages/account/Delete.jsp");
        } else {
            ServletDispatcher.redirectToServlet(request, response, "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        boolean confirmation = ServletParameterParser.getChecked(request, "confirmation");
        try {
            if (!confirmation) {
                throw new BusinessException(ServletErrorCode.ACCOUNT_DELETE_CONFIRMATION_INVALID);
            }
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            new UserManager().removeUser(userId);
            ServletDispatcher.redirectToServlet(request, response, "/logout");
            return;
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        doGet(request, response);
    }
}