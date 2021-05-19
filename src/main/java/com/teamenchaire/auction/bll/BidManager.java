package com.teamenchaire.auction.bll;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Bid;
import com.teamenchaire.auction.dal.BidDAO;
import com.teamenchaire.auction.dal.DAOFactory;

/**
 * A {@code class} which controls bids using a data access object.
 * 
 * @author Marin Taverniers
 */
public final class BidManager {
    private final BidDAO bidDAO;

    /**
     * Constructs a {@code BidManager} using a data access object.
     */
    public BidManager() {
        this.bidDAO = DAOFactory.getBidDAO();
    }

    public List<Bid> getBids() throws BusinessException {
        return bidDAO.selectAll();
    }
}