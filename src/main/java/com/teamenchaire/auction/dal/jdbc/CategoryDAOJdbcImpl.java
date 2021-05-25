package com.teamenchaire.auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.dal.CategoryDAO;
import com.teamenchaire.auction.dal.DALErrorCode;

/**
 * A {@code class} which implements CRUD methods for categories in the database
 * using the JDBC driver.
 * 
 * @author Marin Taverniers
 */
public class CategoryDAOJdbcImpl implements CategoryDAO {

    /* Select */
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM categories";
    
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM categories WHERE (id_category = ?)";

    /**
     * Constructs a {@code CategoryDAOJdbcImpl}.
     */
    public CategoryDAOJdbcImpl() {
        //
    }

    @Override
    public void insert(Category category) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_INSERT);
    }

    @Override
    public void update(Category category) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_UPDATE);
    }

    @Override
    public void delete(Category category) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_DELETE);
    }

    @Override
    public List<Category> selectAll() throws BusinessException {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(SQL_SELECT_ALL);
            while (result.next()) {
                categories.add(buildCategory(result));
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return categories;
    }

    @Override
    public Category selectById(Integer id) throws BusinessException {
        Category category = null;
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                category = buildCategory(result);
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return category;
    }

    private static Category buildCategory(ResultSet result) throws SQLException {
        return new Category(result.getInt("id_category"), result.getString("category_name"));
    }
}