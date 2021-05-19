package com.teamenchaire.auction.bll;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Bid;
import com.teamenchaire.auction.dal.BidDAO;
import com.teamenchaire.auction.dal.DAOFactory;

public final class BidManager {
    private final BidDAO bidDAO;

    public BidManager() {
        this.bidDAO = DAOFactory.getBidDAO();
    }

    public List<Bid> getBids() throws BusinessException {
        return bidDAO.selectAll();
    }
}