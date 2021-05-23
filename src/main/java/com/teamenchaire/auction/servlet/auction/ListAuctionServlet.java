package com.teamenchaire.auction.servlet.auction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.servlet.ParameterParser;
import com.teamenchaire.auction.servlet.ServletErrorCode;

/**
 * A {@code Servlet} which handles requests to the page to list auction.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/auction/list")
public final class ListAuctionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Category> categories = null;
        String name = ParameterParser.getTrimmedString(request, "name");
        Integer categoryId = ParameterParser.getInt(request, "categoryId");
        try {
            categories = new CategoryManager().getCategories();

            //HttpSession session = request.getSession(false);
            HttpSession session = request.getSession(true);
            Map<String, Map<String, Boolean>> groups = new HashMap<>();
            String selectedGroup = null;
            
            int userId = 1;
            Map<String, List<Item>> itemGroups = new HashMap<>();
            
            if (session == null) {
                // Anonymous user
                itemGroups.put("purchases", new ItemManager().getAllAvailableItems(name, categoryId));
            } else {
                
                // Logged user
                groups.put("purchases", new HashMap<>());
                groups.put("sales", new HashMap<>());
                selectedGroup = ParameterParser.getTrimmedString(request, "group");
                if ((selectedGroup == null) || (selectedGroup.isEmpty())) {
                    selectedGroup = "purchases";
                    groups.get("purchases").put("available", true);
                } else {
                    for (Entry<String, Map<String, Boolean>> group : groups.entrySet()) {
                        String[] selectedTypes = ParameterParser.getTrimmedStringArray(request, group.getKey());
                        if (selectedTypes != null) {
                            for (String selectedType : selectedTypes) {
                                group.getValue().put(selectedType, true);
                            }
                        }
                    }
                }
                request.setAttribute("isLogged", true);
                request.setAttribute("group", selectedGroup);
                for (Entry<String, Map<String, Boolean>> group : groups.entrySet()) {
                    request.setAttribute(group.getKey(), group.getValue());
                }
                if (groups.get(selectedGroup).isEmpty()) {
                    throw new BusinessException(ServletErrorCode.ITEMS_TYPE_EMPTY);
                }
                
                for (String subGroup : groups.get(selectedGroup).keySet()) {
                    itemGroups.put(subGroup, getItems(selectedGroup, subGroup, userId, name, categoryId));
                }
            }
            request.setAttribute("itemGroups", itemGroups);
            
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorCode", e.getCode());
        }
        
        // Forward
        request.setAttribute("categories", categories);
        request.setAttribute("name", name);
        request.setAttribute("categoryId", categoryId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/auction/List.jsp");
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

    private List<Item> getItems(String selectedGroup, String subGroupName, Integer userId, String name, Integer categoryId) throws BusinessException {
        ItemManager itemManager = new ItemManager();
        List<Item> items = new ArrayList<>();
        if (selectedGroup.equalsIgnoreCase("purchases")) {
            switch (subGroupName) {
                case "available":
                    items = itemManager.getAvailablePurchasesItems(userId, name, categoryId);
                    break;
                case "current":
                    items = itemManager.getCurrentPurchasesItems(userId, name, categoryId);
                    break;
                case "won":
                    items = itemManager.getWonPurchasesItems(userId, name, categoryId);
                    break;
                default:
            }
        } else if (selectedGroup.equalsIgnoreCase("sales")) {
            switch (subGroupName) {
                case "current":
                    items =  itemManager.getCurrentSalesItems(userId, name, categoryId);
                    break;
                case "future":
                    items =  itemManager.getFutureSalesItems(userId, name, categoryId);
                    break;
                case "ended":
                    items =  itemManager.getEndedSalesItems(userId, name, categoryId);
                    break;
                default:
            }
        }
        return items;
    }
}