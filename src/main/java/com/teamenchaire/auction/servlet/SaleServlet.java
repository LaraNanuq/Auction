package com.teamenchaire.auction.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.CategoryManager;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bll.UserManager;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.User;

/**
 * A {@code Servlet} which handles requests to the page to sell an item.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/Sale")
public final class SaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SaleServlet() {
        // Default constructor
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = new UserManager().getUser(1);

        try {
            request.setAttribute("categories", getCategories());
        } catch (final BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        if (request.getAttribute("street") == null) {
            request.setAttribute("street", user.getStreet());
            request.setAttribute("postalCode", user.getPostalCode());
            request.setAttribute("city", user.getCity());
        }
        try {
            request.getRequestDispatcher("/WEB-INF/Sale.jsp").forward(request, response);
        } catch (final ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = new UserManager().getUser(1);

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String name = request.getParameter("name");
        final String description = request.getParameter("description");
        final String categoryId = request.getParameter("categoryId");
        final String picture = request.getParameter("picture");
        final String price = request.getParameter("price");
        final String startDate = request.getParameter("startDate");
        final String endDate = request.getParameter("endDate");
        final String street = request.getParameter("street");
        final String postalCode = request.getParameter("postalCode");
        final String city = request.getParameter("city");
        try {
            final ItemManager itemManager = new ItemManager();
            itemManager.addItem(user.getId(), name, description, StringParser.parseInt(categoryId),
                    StringParser.parseInt(price), StringParser.parseDate(startDate), StringParser.parseDate(endDate),
                    street, postalCode, city);
            request.getRequestDispatcher("/").forward(request, response);
            return;
        } catch (final BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("picture", picture);
            request.setAttribute("price", price);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("street", street);
            request.setAttribute("postalCode", postalCode);
            request.setAttribute("city", city);
        } catch (final ServletException | IOException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    private List<Category> getCategories() throws BusinessException {
        final List<Category> categories = new CategoryManager().getCategories();
        categories.add(0, new Category(-1, ""));
        return categories;
    }
}