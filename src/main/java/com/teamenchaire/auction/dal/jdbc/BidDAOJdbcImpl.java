package com.teamenchaire.auction.dal.jdbc;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Bid;
import com.teamenchaire.auction.dal.BidDAO;

public final class BidDAOJdbcImpl implements BidDAO {

    public BidDAOJdbcImpl() {
        // Default constructor
    }
    
    @Override
    public void insert(Bid object) throws BusinessException {
    }

    @Override
    public void update(Bid object) throws BusinessException {
    }

    @Override
    public void delete(Bid object) throws BusinessException {
    }

    @Override
    public List<Bid> selectAll() throws BusinessException {
        return null;
    }

    @Override
    public Bid select(Integer id) throws BusinessException {
        return null;
    }
}