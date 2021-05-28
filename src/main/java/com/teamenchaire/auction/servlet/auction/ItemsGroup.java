package com.teamenchaire.auction.servlet.auction;

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

    List<Item> getItems(Integer userId, String name, Integer categoryId) throws BusinessException;

    String getGroupName();

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