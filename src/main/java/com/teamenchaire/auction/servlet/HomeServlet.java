package com.teamenchaire.auction.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.CategoryManager;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.Item;

/**
 * A {@code Servlet} which handles requests to the home page.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/")
public final class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public HomeServlet() {
        // Default constructor
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String itemName = request.getParameter("itemName");
        final String categoryId = request.getParameter("categoryId");
        try {
            request.setAttribute("categories", getCategories());
            request.setAttribute("items", getItems(itemName, StringParser.parseInt(categoryId)));
        } catch (final BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        request.setAttribute("itemName", itemName);
        request.setAttribute("categoryId", categoryId);
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Home.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (final ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        doGet(request, response);
    }

    private List<Category> getCategories() throws BusinessException {
        final List<Category> categories = new CategoryManager().getCategories();
        categories.add(0, new Category(-1, "Toutes"));
        return categories;
    }

    private List<Item> getItems(final String itemName, final Integer categoryId) throws BusinessException {
        final ItemManager itemManager = new ItemManager();
        if ((itemName != null) && (!itemName.isEmpty()) && (categoryId != null) && (categoryId != -1)) {
            return itemManager.getItems(itemName, categoryId);
        }
        if ((itemName != null) && (!itemName.isEmpty())) {
            return itemManager.getItems(itemName);
        }
        if ((categoryId != null) && (categoryId != -1)) {
            return itemManager.getItems(categoryId);
        }
        return itemManager.getItems();
    }
}