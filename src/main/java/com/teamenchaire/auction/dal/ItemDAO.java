package com.teamenchaire.auction.dal;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Item;

/**
 * An {@code interface} which defines CRUD methods for items in the database.
 * 
 * @author Marin Taverniers
 */
public interface ItemDAO extends GenericDAO<Item> {
    List<Item> selectAllAvailable(String name, Integer categoryId) throws BusinessException;

    List<Item> selectAvailablePurchases(Integer userId, String name, Integer categoryId) throws BusinessException;

    List<Item> selectCurrentPurchases(Integer userId, String name, Integer categoryId) throws BusinessException;

    List<Item> selectWonPurchases(Integer userId, String name, Integer categoryId) throws BusinessException;

    List<Item> selectCurrentSales(Integer userId, String name, Integer categoryId) throws BusinessException;

    List<Item> selectFutureSales(Integer userId, String name, Integer categoryId) throws BusinessException;

    List<Item> selectEndedSales(Integer userId, String name, Integer categoryId) throws BusinessException;
}