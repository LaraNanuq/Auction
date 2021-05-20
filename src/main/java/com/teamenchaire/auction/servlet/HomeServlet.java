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
import com.teamenchaire.auction.bo.Item;

/**
 * A {@code Servlet} which handles requests to the home page.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/")
public final class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String filterName = ParameterParser.getTrimmedString(request, "filterName");
        Integer filterCategoryId = ParameterParser.getInt(request, "filterCategoryId");
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());
            request.setAttribute("items", getItems(filterName, filterCategoryId));
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        request.setAttribute("filterName", filterName);
        request.setAttribute("filterCategoryId", filterCategoryId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Home.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    private List<Item> getItems(String name, Integer categoryId) throws BusinessException {
        ItemManager itemManager = new ItemManager();
        if ((name != null) && (!name.isEmpty())) {
            if ((categoryId != null)) {
                return itemManager.getItems(name, categoryId);
            }
            return itemManager.getItems(name);
        }
        if (categoryId != null) {
            return itemManager.getItems(categoryId);
        }
        return itemManager.getItems();
    }
}