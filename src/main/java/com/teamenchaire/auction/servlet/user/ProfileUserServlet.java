package com.teamenchaire.auction.servlet.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.servlet.ServletDispatcher;
import com.teamenchaire.auction.servlet.ServletPathParser;
import com.teamenchaire.auction.servlet.UserSession;

/**
 * A {@code Servlet} which handles requests to the page to view an user profile.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/user/profile/*")
public final class ProfileUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        try {
            String nickname = new ServletPathParser(request).getString();
            UserSession session = new UserSession(request);
            UserManager userManager = new UserManager();
            User user;
            if ((nickname == null) || (nickname.isEmpty())) {
                if (!session.isValid()) {
                    dispatcher.redirectToServlet("/home");
                    return;
                }
                user = userManager.getUserById(session.getUserId());
            } else {
                user = userManager.getUserByNickname(nickname);
            }
            if (user == null) {
                dispatcher.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("user", user);
            if (user.getId().equals(session.getUserId())) {
                request.setAttribute("isEditable", true);
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        dispatcher.forwardToJsp("/pages/user/Profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}