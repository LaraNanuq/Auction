package com.teamenchaire.auction.servlet.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.servlet.ServletDispatcher;

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
        if (nickname != null) {
            nickname = nickname.replace("/", "").trim();
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            
            try {
                user = new UserManager().getUserById(userId);
                if (nickname.equalsIgnoreCase(user.getNickname())) {
                    request.setAttribute("isEditable", true);
                } else {
                    user = null;
                }
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        
        if (user == null) {
            try {
                user = new UserManager().getUserByNickname(nickname);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("user", user);
        ServletDispatcher.forwardToJsp(request, response, "/pages/user/Profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}