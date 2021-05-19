package com.teamenchaire.auction.dal;

import com.teamenchaire.auction.dal.jdbc.BidDAOJdbcImpl;
import com.teamenchaire.auction.dal.jdbc.CategoryDAOJdbcImpl;
import com.teamenchaire.auction.dal.jdbc.ItemDAOJdbcImpl;

/**
 * A {@code class} which creates DAO objects using the JDBC technology.
 * 
 * @author Marin Taverniers
 */
public final class DAOFactory {

    private DAOFactory() {
    }

    public static ItemDAO getItemDAO() {
        return new ItemDAOJdbcImpl();
    }

    public static CategoryDAO getCategoryDAO() {
        return new CategoryDAOJdbcImpl();
    }

    public static BidDAO getBidDAO() {
        return new BidDAOJdbcImpl();
    }
}