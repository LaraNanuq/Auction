package com.teamenchaire.auction.dal;

import java.util.List;

import com.teamenchaire.auction.BusinessException;

/**
 * An {@code interface} which defines CRUD methods for data access objects in
 * the database.
 * 
 * @author Marin Taverniers
 */
public interface GenericDAO<T> {
    void insert(T object) throws BusinessException;

    void update(T object) throws BusinessException;

    void delete(T object) throws BusinessException;

    List<T> selectAll() throws BusinessException;

    T select(Integer id) throws BusinessException;
}