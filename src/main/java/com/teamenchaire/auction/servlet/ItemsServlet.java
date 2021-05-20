package com.teamenchaire.auction.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.CategoryManager;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bo.Item;

/**
 * A {@code Servlet} which handles requests to the home page.
 * 
 * @author Marin Taverniers
 */
@WebServlet(urlPatterns = {"", "/index", "/home", "/items"})
public final class ItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String name = ParameterParser.getTrimmedString(request, "name");
        Integer categoryId = ParameterParser.getInt(request, "categoryId");
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());

            //HttpSession session = request.getSession(false);
            HttpSession session = request.getSession(true);
            boolean isLogged = (session != null);
            request.setAttribute("isLogged", isLogged);
            if (!isLogged) {

                // Disconnected user
                request.setAttribute("items", getItems(name, categoryId));
            } else {
                String[] test = request.getParameterValues("purchases");
                System.out.println(Arrays.toString(test));
                String[] test2 = request.getParameterValues("sales");
                System.out.println(Arrays.toString(test2));

                // Connected user
                String type = ParameterParser.getTrimmedString(request, "type");
                Boolean availablePurchases = ParameterParser.isChecked(request, "availablePurchases");
                //if (availablePurchases == null) {
                    //availablePurchases = true;
                //}
                Boolean currentPurchases = ParameterParser.isChecked(request, "currentPurchases");
                //if (currentPurchases == null) {
                    //currentPurchases = true;
                //}
                Boolean wonPurchases = ParameterParser.isChecked(request, "wonPurchases");
                Boolean currentSales = ParameterParser.isChecked(request, "currentSales");
                Boolean futureSales = ParameterParser.isChecked(request, "futureSales");
                Boolean endedSales = ParameterParser.isChecked(request, "endedSales");
                if ((type == null) || (type.equalsIgnoreCase("purchases"))) {
                    // Erreur si aucune checkbox
                } else {
                    // Erreur si aucune checkbox
                }
                request.setAttribute("type", type);
                //request.setAttribute("purchases", purchases);
                //request.setAttribute("sales", sales);
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        request.setAttribute("name", name);
        request.setAttribute("categoryId", categoryId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Items.jsp");
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