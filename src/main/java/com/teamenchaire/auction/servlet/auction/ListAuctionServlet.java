package com.teamenchaire.auction.servlet.auction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.CategoryManager;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.servlet.ServletDispatcher;
import com.teamenchaire.auction.servlet.ServletErrorCode;
import com.teamenchaire.auction.servlet.ServletParameterParser;
import com.teamenchaire.auction.servlet.UserSession;
import com.teamenchaire.auction.servlet.auction.ItemsGroup.RadioGroups;
import com.teamenchaire.auction.servlet.auction.ItemsGroup.Purchases;
import com.teamenchaire.auction.servlet.auction.ItemsGroup.Sales;

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
        ServletParameterParser parser = new ServletParameterParser(request);
        UserSession session = new UserSession(request);
        String name = parser.getTrimmedString("name");
        Integer categoryId = parser.getInt("categoryId");
        String group = parser.getTrimmedString("group");
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());
            Map<String, List<Item>> itemGroups;
            if (!session.isValid()) {

                // Get all available items
                group = RadioGroups.PURCHASES.getGroupName();
                itemGroups = getItemGroupsAsGuest(name, categoryId);
            } else {

                // Get selected subgroups
                List<String> selPurchasesGroupKeys = parser.getTrimmedStringList(RadioGroups.PURCHASES.getGroupName());
                List<String> selSalesGroupKeys = parser.getTrimmedStringList(RadioGroups.SALES.getGroupName());
                Map<ItemsGroup, Boolean> purchasesGroups = getSelectedGroups(selPurchasesGroupKeys, Purchases.values());
                Map<ItemsGroup, Boolean> salesGroups = getSelectedGroups(selSalesGroupKeys, Sales.values());

                // Get selected group
                RadioGroups selectedGroup = getSelectedGroup(group);
                if (selectedGroup == null) {
                    selectedGroup = RadioGroups.PURCHASES;
                    purchasesGroups.put(Purchases.AVAILABLE, true);
                    salesGroups.put(Sales.CURRENT, true);
                    group = selectedGroup.getGroupName();
                }
                request.setAttribute("purchasesGroups", purchasesGroups);
                request.setAttribute("salesGroups", salesGroups);

                // Get items from selected subgroups
                Integer userId = session.getUserId();
                if (selectedGroup == RadioGroups.PURCHASES) {
                    itemGroups = getItemGroupsAsUser(userId, name, categoryId, purchasesGroups);
                } else {
                    itemGroups = getItemGroupsAsUser(userId, name, categoryId, salesGroups);
                }
            }
            request.setAttribute("itemGroups", itemGroups);
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        request.setAttribute("name", name);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("group", group);
        new ServletDispatcher(request, response).forwardToJsp("/pages/auction/List.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    private RadioGroups getSelectedGroup(String selectedGroupKey) {
        RadioGroups selectedGroup = null;
        if (selectedGroupKey != null) {
            for (RadioGroups group : RadioGroups.values()) {
                if (selectedGroupKey.equals(group.getGroupName())) {
                    selectedGroup = group;
                    break;
                }
            }
        }
        return selectedGroup;
    }

    private Map<ItemsGroup, Boolean> getSelectedGroups(List<String> selGroupKeys, ItemsGroup[] values) {
        Map<ItemsGroup, Boolean> groups = new LinkedHashMap<>();
        for (ItemsGroup group : values) {
            groups.put(group, selGroupKeys.contains(group.getGroupName()));
        }
        return groups;
    }

    private Map<String, List<Item>> getItemGroupsAsGuest(String name, Integer categoryId) throws BusinessException {
        Map<String, List<Item>> groups = new LinkedHashMap<>();
        groups.put(Purchases.AVAILABLE.getGroupName(), new ItemManager().getAllAvailableItems(name, categoryId));
        return groups;
    }

    private Map<String, List<Item>> getItemGroupsAsUser(Integer userId, String name, Integer categoryId,
            Map<ItemsGroup, Boolean> groups) throws BusinessException {
        Map<String, List<Item>> itemGroups = new LinkedHashMap<>();
        for (ItemsGroup group : groups.keySet()) {
            if (groups.get(group).booleanValue()) {
                itemGroups.put(group.getGroupName(), group.getItems(userId, name, categoryId));
            }
        }
        if (itemGroups.isEmpty()) {
            throw new BusinessException(ServletErrorCode.AUCTION_LIST_GROUP_NULL);
        }
        return itemGroups;
    }
}