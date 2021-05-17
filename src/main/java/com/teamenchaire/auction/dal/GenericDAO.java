package com.teamenchaire.auction.dal;

import java.util.List;

import com.teamenchaire.auction.BusinessException;

/**
 * A {@code interface} which defines generic methods for DAO objects.
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