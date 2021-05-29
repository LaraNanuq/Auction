package com.teamenchaire.auction.ihm.servlet.auction;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bll.ItemManager;
import com.teamenchaire.auction.bo.Item;

/**
 * An {@code interface} which defines a filtered group of items.
 * 
 * @author Marin Taverniers
 */
public interface ItemsGroup {
    final ItemManager itemManager = new ItemManager();

    /**
     * Returns the list of items that match specified user, name and category
     * filters.
     * 
     * @param userId     The ID of the user to search for or to ignore
     * @param name       The name to search for
     * @param categoryId The ID of the category to search for
     * @return the list of items matching filters.
     * @throws BusinessException if the database request cannot be performed.
     */
    List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException;

    /**
     * Returns the name of this group.
     * 
     * @return the name of the group.
     */
    String getGroupName();

    /**
     * An {@code enum} list of item groups.
     * 
     * @author Marin Taverniers
     */
    public enum RadioGroups {
        PURCHASES("purchases"), SALES("sales");

        private String groupName;

        private RadioGroups(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupName() {
            return groupName;
        }
    }

    /**
     * An {@code enum} list of item groups related to purchases.
     * 
     * @author Marin Taverniers
     */
    public enum Purchases implements ItemsGroup {
        AVAILABLE {
            @Override
            public List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException {
                return itemManager.getAvailablePurchasesItems(userId, name, categoryId);
            }

            @Override
            public String getGroupName() {
                return "available";
            }
        },

        CURRENT {
            @Override
            public List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException {
                return itemManager.getCurrentPurchasesItems(userId, name, categoryId);
            }

            @Override
            public String getGroupName() {
                return "current";
            }
        },

        WON {
            @Override
            public List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException {
                return itemManager.getWonPurchasesItems(userId, name, categoryId);
            }

            @Override
            public String getGroupName() {
                return "won";
            }
        };
    }

    /**
     * An {@code enum} list of item groups related to sales.
     * 
     * @author Marin Taverniers
     */
    public enum Sales implements ItemsGroup {
        CURRENT {
            @Override
            public List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException {
                return itemManager.getCurrentSalesItems(userId, name, categoryId);
            }

            @Override
            public String getGroupName() {
                return "current";
            }
        },

        FUTURE {
            @Override
            public List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException {
                return itemManager.getFutureSalesItems(userId, name, categoryId);
            }

            @Override
            public String getGroupName() {
                return "future";
            }
        },

        ENDED {
            @Override
            public List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException {
                return itemManager.getEndedSalesItems(userId, name, categoryId);
            }

            @Override
            public String getGroupName() {
                return "ended";
            }
        };
    }
}