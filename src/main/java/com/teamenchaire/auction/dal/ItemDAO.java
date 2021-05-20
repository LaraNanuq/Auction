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
    List<Item> selectBy(String itemName) throws BusinessException;

    List<Item> selectBy(Integer categoryId) throws BusinessException;

    List<Item> selectBy(String itemName, Integer categoryId) throws BusinessException;
}