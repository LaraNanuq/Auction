package com.teamenchaire.auction.dal;

import com.teamenchaire.auction.dal.jdbc.BidDAOJdbcImpl;
import com.teamenchaire.auction.dal.jdbc.CategoryDAOJdbcImpl;
import com.teamenchaire.auction.dal.jdbc.ItemDAOJdbcImpl;
import com.teamenchaire.auction.dal.jdbc.UserDAOJdbcImpl;

/**
 * A {@code class} which creates instances of data access objects using the JDBC
 * driver.
 * 
 * @author Marin Taverniers
 */
public final class DAOFactory {

    private DAOFactory() {
    }

    public static UserDAO getUserDAO() {
        return new UserDAOJdbcImpl();
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