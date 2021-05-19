package com.teamenchaire.auction.dal.jdbc;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Bid;
import com.teamenchaire.auction.dal.BidDAO;
import com.teamenchaire.auction.dal.DALErrorCode;

/**
 * A {@code class} which implements CRUD methods for bids in the database using
 * the JDBC driver.
 * 
 * @author Marin Taverniers
 */
public final class BidDAOJdbcImpl implements BidDAO {

    /**
     * Constructs a {@code BidDAOJdbcImpl}.
     */
    public BidDAOJdbcImpl() {
        // Default constructor
    }

    @Override
    public void insert(final Bid bid) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_INSERT);
    }

    @Override
    public void update(final Bid bid) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_UPDATE);
    }

    @Override
    public void delete(final Bid bid) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_DELETE);
    }

    @Override
    public List<Bid> selectAll() throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_SELECT);
    }

    @Override
    public Bid select(final Integer id) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_SELECT);
    }
}