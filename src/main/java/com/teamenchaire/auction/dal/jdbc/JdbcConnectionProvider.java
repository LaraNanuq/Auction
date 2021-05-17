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
 * A {@code class} which provides a connection to the database using the JDBC
 * driver.
 * 
 * @author Marin Taverniers
 */
public class JdbcConnectionProvider {
    private static DataSource dataSource;

    private JdbcConnectionProvider() {
    }

    public static Connection getConnection() throws BusinessException {
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.DB_CONNECTION, e);
        }
        return connection;
    }

    private static DataSource getDataSource() throws BusinessException {
        if (dataSource == null) {
            try {
                final Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:comp/env/" + "JdbcPoolConnection");
            } catch (final NamingException e) {
                throw new BusinessException(DALErrorCode.DB_DATASOURCE_CONTEXT, e);
            }
        }
        return dataSource;
    }
}