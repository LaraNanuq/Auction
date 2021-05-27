package com.teamenchaire.auction.servlet.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.servlet.ServletDispatcher;
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
        String nickname = request.getPathInfo();
        User user = null;
        UserManager userManager = new UserManager();
        ServletDispatcher dispatcher = new ServletDispatcher(request, response);
        UserSession session = new UserSession(request);
        if ((nickname != null) && (nickname.length() > 1)) {
            nickname = nickname.replace("/", "").trim();
            try {
                user = userManager.getUserByNickname(nickname);
                if (user == null) {
                    dispatcher.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                if (session.isValid()) {
                    if (user.getId().equals(session.getUserId())) {
                        request.setAttribute("isEditable", true);
                    }
                }
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("exception", e);
            }
        } else {
            if (!session.isValid()) {
                dispatcher.redirectToServlet("/home");
                return;
            }
            try {
                user = userManager.getUserById(session.getUserId());
                request.setAttribute("isEditable", true);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("exception", e);
            }
        }
        request.setAttribute("user", user);
        dispatcher.forwardToJsp("/pages/user/Profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}