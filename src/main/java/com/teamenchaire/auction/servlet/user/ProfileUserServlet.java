package com.teamenchaire.auction.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.bll.UserManager;

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
        System.out.println(request.getPathInfo());
        request.setAttribute("user", new UserManager().getUser(1));
        request.getSession().setAttribute("user", new UserManager().getUser(1));
        try {
            request.getRequestDispatcher("/WEB-INF/pages/user/Profile.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}