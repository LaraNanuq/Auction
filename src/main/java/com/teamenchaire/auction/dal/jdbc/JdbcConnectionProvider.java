package com.teamenchaire.auction.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.dal.DALErrorCode;

/**
 * A {@code class} which provides connections to the database using the JDBC
 * driver.
 * 
 * @author Marin Taverniers
 */
public final class JdbcConnectionProvider {
    private static final String CONTEXT_NAME = "JdbcPoolConnection";
    private static DataSource dataSource;

    private JdbcConnectionProvider() {
    }

    /**
     * Gets a connection to the database.
     * 
     * @return a connection to the database.
     * @throws BusinessException if a connection cannot be established.
     */
    public static Connection getConnection() throws BusinessException {
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.DB_CONNECTION, e);
        }
        return connection;
    }

    /**
     * Gets the data source which provides a connection to the database.
     * 
     * @return a data source which provides a connection to the database.
     * @throws BusinessException if the name of the context cannot be found.
     */
    private static DataSource getDataSource() throws BusinessException {
        if (dataSource == null) {
            try {
                final Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:comp/env/" + CONTEXT_NAME);
            } catch (final NamingException e) {
                throw new BusinessException(DALErrorCode.DB_DATASOURCE_CONTEXT, e);
            }
        }
        return dataSource;
    }
}