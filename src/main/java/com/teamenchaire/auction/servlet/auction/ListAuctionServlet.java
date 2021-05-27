package com.teamenchaire.auction.servlet.auction;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

/**
 * A {@code Servlet} which handles requests to the page to list auction.
 * 
 * @author Marin Taverniers
 */
@WebServlet("/auction/list")
public final class ListAuctionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private enum ItemsSubGroups {
        PURCHASES_AVAILABLE("available", ItemsGroups.PURCHASES), PURCHASES_CURRENT("current", ItemsGroups.PURCHASES),
        PURCHASES_WON("won", ItemsGroups.PURCHASES), SALES_CURRENT("current", ItemsGroups.SALES),
        SALES_FUTURE("future", ItemsGroups.SALES), SALES_ENDED("ended", ItemsGroups.SALES);

        private String key;
        private ItemsGroups parentGroup;

        private ItemsSubGroups(String key, ItemsGroups parentGroup) {
            this.key = key;
            this.parentGroup = parentGroup;
        }

        public String getKey() {
            return key;
        }

        public ItemsGroups getParentGroup() {
            return parentGroup;
        }
    }

    public interface RadioGroups {
        String getGroupName();
    }

    private enum ItemsGroups {
        PURCHASES("purchases"), SALES("sales");

        private String groupName;

        private ItemsGroups(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupName() {
            return groupName;
        }
    }

    public enum PurchasesGroups implements RadioGroups {
        AVAILABLE("available"), CURRENT("current"), WON("won");

        private String groupName;

        private PurchasesGroups(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupName() {
            return groupName;
        }
    }

    public enum SalesGroups implements RadioGroups {
        CURRENT("current"), FUTURE("future"), ENDED("ended");

        private String groupName;

        private SalesGroups(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupName() {
            return groupName;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletParameterParser parser = new ServletParameterParser(request);
        String name = parser.getTrimmedString("name");
        Integer categoryId = parser.getInt("categoryId");
        request.setAttribute("name", name);
        request.setAttribute("categoryId", categoryId);
        try {
            request.setAttribute("categories", new CategoryManager().getCategories());

            // Get items
            UserSession session = new UserSession(request);
            Map<String, List<Item>> items = new LinkedHashMap<>();
            if (!session.isValid()) {
                request.setAttribute("group", ItemsGroups.PURCHASES.getGroupName());
                items.put(PurchasesGroups.AVAILABLE.getGroupName(),
                        new ItemManager().getAllAvailableItems(name, categoryId));

            } else {

                // Convert subgroups from query parameters to a boolean map
                // ([purchases=available&sales=current] => [purchases.available = true,
                // sales.current = true])
                Map<PurchasesGroups, Boolean> purchasesSubGroups = new LinkedHashMap<>();
                List<String> selectedPurchasesSubGroups = parser
                        .getTrimmedStringList(ItemsGroups.PURCHASES.getGroupName());
                for (PurchasesGroups subGroup : PurchasesGroups.values()) {
                    purchasesSubGroups.put(subGroup, selectedPurchasesSubGroups.contains(subGroup.getGroupName()));
                }

                Map<SalesGroups, Boolean> salesSubGroups = new LinkedHashMap<>();
                List<String> selectedSalesSubGroups = parser.getTrimmedStringList(ItemsGroups.SALES.getGroupName());
                for (SalesGroups subGroup : SalesGroups.values()) {
                    salesSubGroups.put(subGroup, selectedSalesSubGroups.contains(subGroup.getGroupName()));
                }

                ItemsGroups selectedItemsGroup = ItemsGroups.PURCHASES;
                String selectedItemsGroupKey = parser.getTrimmedString("group");
                if ((selectedItemsGroupKey == null) || (selectedItemsGroupKey.isEmpty())) {
                    // selectedItemsGroup = ItemsGroups.PURCHASES;
                    purchasesSubGroups.put(PurchasesGroups.AVAILABLE, true);
                } else {
                    for (ItemsGroups group : ItemsGroups.values()) {
                        if (selectedItemsGroupKey.equals(group.getGroupName())) {
                            selectedItemsGroup = group;
                            break;
                        }
                    }
                }

                request.setAttribute("group", selectedItemsGroup.getGroupName());
                request.setAttribute("purchasesGroups", purchasesSubGroups);
                request.setAttribute("salesGroups", salesSubGroups);

                if ((selectedItemsGroup == ItemsGroups.PURCHASES) && (!purchasesSubGroups.containsValue(true))) {
                    throw new BusinessException(ServletErrorCode.AUCTION_LIST_GROUP_NULL);
                }
                if ((selectedItemsGroup == ItemsGroups.SALES) && (!salesSubGroups.containsValue(true))) {
                    throw new BusinessException(ServletErrorCode.AUCTION_LIST_GROUP_NULL);
                }

                ItemManager itemManager = new ItemManager();
                Integer userId = session.getUserId();

                if (selectedItemsGroup == ItemsGroups.PURCHASES) {
                    for (Entry<PurchasesGroups, Boolean> group : purchasesSubGroups.entrySet()) {
                        if (group.getValue().booleanValue()) {
                            switch (group.getKey()) {
                                case AVAILABLE:
                                    items.put(group.getKey().getGroupName(),
                                            itemManager.getAvailablePurchasesItems(userId, name, categoryId));
                                    break;
                                case CURRENT:
                                    items.put(group.getKey().getGroupName(),
                                            itemManager.getCurrentPurchasesItems(userId, name, categoryId));
                                    break;
                                case WON:
                                    items.put(group.getKey().getGroupName(),
                                            itemManager.getWonPurchasesItems(userId, name, categoryId));
                                    break;
                                default:
                            }
                        }
                    }
                }
                if (selectedItemsGroup == ItemsGroups.SALES) {
                    for (Entry<SalesGroups, Boolean> group : salesSubGroups.entrySet()) {
                        if (group.getValue().booleanValue()) {
                            switch (group.getKey()) {
                                case CURRENT:
                                    items.put(group.getKey().getGroupName(),
                                            itemManager.getCurrentSalesItems(userId, name, categoryId));
                                    break;
                                case FUTURE:
                                    items.put(group.getKey().getGroupName(),
                                            itemManager.getFutureSalesItems(userId, name, categoryId));
                                    break;
                                case ENDED:
                                    items.put(group.getKey().getGroupName(),
                                            itemManager.getEndedSalesItems(userId, name, categoryId));
                                    break;
                                default:
                            }
                        }
                    }
                }
            }
            request.setAttribute("items", items);

        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }
        new ServletDispatcher(request, response).forwardToJsp("/pages/auction/List.jsp");
    }

    /*
     * 
     * if (userId == null) { // Anonymous user itemGroups.put("purchases", new
     * ItemManager().getAllAvailableItems(name, categoryId)); } else {
     * 
     * // Logged user groups.put("purchases", new HashMap<>()); groups.put("sales",
     * new HashMap<>()); selectedGroup = parser.getTrimmedString("group"); if
     * ((selectedGroup == null) || (selectedGroup.isEmpty())) { selectedGroup =
     * "purchases"; groups.get("purchases").put("available", true); } else { for
     * (Entry<String, Map<String, Boolean>> group : groups.entrySet()) { String[]
     * selectedTypes = parser.getTrimmedStringArray(group.getKey()); if
     * (selectedTypes != null) { for (String selectedType : selectedTypes) {
     * group.getValue().put(selectedType, true); } } } }
     * request.setAttribute("isLogged", true); request.setAttribute("group",
     * selectedGroup); for (Entry<String, Map<String, Boolean>> group :
     * groups.entrySet()) { request.setAttribute(group.getKey(), group.getValue());
     * } if (groups.get(selectedGroup).isEmpty()) { throw new
     * BusinessException(ServletErrorCode.AUCTION_LIST_FILTER_GROUP_EMPTY); }
     * 
     * for (String subGroup : groups.get(selectedGroup).keySet()) {
     * itemGroups.put(subGroup, getItems(selectedGroup, subGroup, userId, name,
     * categoryId)); } } request.setAttribute("itemGroups", itemGroups);
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    /*
     * private List<Item> getItems(String selectedGroup, String subGroupName,
     * Integer userId, String name, Integer categoryId) throws BusinessException {
     * ItemManager itemManager = new ItemManager(); List<Item> items = new
     * ArrayList<>(); if (selectedGroup.equalsIgnoreCase("purchases")) { switch
     * (subGroupName) { case "available": items =
     * itemManager.getAvailablePurchasesItems(userId, name, categoryId); break; case
     * "current": items = itemManager.getCurrentPurchasesItems(userId, name,
     * categoryId); break; case "won": items =
     * itemManager.getWonPurchasesItems(userId, name, categoryId); break; default: }
     * } else if (selectedGroup.equalsIgnoreCase("sales")) { switch (subGroupName) {
     * case "current": items = itemManager.getCurrentSalesItems(userId, name,
     * categoryId); break; case "future": items =
     * itemManager.getFutureSalesItems(userId, name, categoryId); break; case
     * "ended": items = itemManager.getEndedSalesItems(userId, name, categoryId);
     * break; default: } } return items; }
     */
}