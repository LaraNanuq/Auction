package com.teamenchaire.auction.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.bo.Withdrawal;
import com.teamenchaire.auction.dal.DALErrorCode;
import com.teamenchaire.auction.dal.ItemDAO;

/**
 * A {@code class} which implements CRUD methods for items in the database using
 * the JDBC driver.
 * 
 * @author Marin Taverniers
 */
public final class ItemDAOJdbcImpl implements ItemDAO {
    private static final String SQL_INSERT_ITEM = "INSERT INTO items (item_name, item_description, bid_start_date, bid_end_date, starting_price, selling_price, id_user, id_category)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_WITHDRAWAL = "INSERT INTO withdrawals (id_item, street, postal_code, city)"
            + " VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM items i" + " JOIN users u ON (i.id_user = u.id_user)"
            + " JOIN categories c ON (i.id_category = c.id_category)"
            + " JOIN withdrawals w ON (i.id_item = w.id_item)";

    private static final String SQL_SELECT_BY_NAME = SQL_SELECT_ALL + " WHERE (upper(item_name) LIKE ?)";
    private static final String SQL_SELECT_BY_CATEGORY = SQL_SELECT_ALL + " WHERE (c.id_category = ?)";
    private static final String SQL_SELECT_BY_NAME_AND_CATEGORY = SQL_SELECT_BY_NAME + " INTERSECT "
            + SQL_SELECT_BY_CATEGORY;

    /**
     * Constructs an {@code ItemDAOJdbcImpl}.
     */
    public ItemDAOJdbcImpl() {
        // Default constructor
    }

    @Override
    public void insert(final Item item) throws BusinessException {
        try (Connection connection = JdbcConnectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                insertItem(connection, item);
                connection.commit();
            } catch (final SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_INSERT, e);
        }
    }

    private void insertItem(final Connection connection, final Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ITEM,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDate(3, Date.valueOf(item.getStartDate()));
            statement.setDate(4, Date.valueOf(item.getEndDate()));
            statement.setInt(5, item.getStartingPrice());
            statement.setInt(6, item.getSellingPrice());
            statement.setInt(7, item.getSeller().getId());
            statement.setInt(8, item.getCategory().getId());
            statement.executeUpdate();
            final ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                item.setId(result.getInt(1));
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_WITHDRAWAL)) {
            statement.setInt(1, item.getId());
            statement.setString(2, item.getWithdrawalPoint().getStreet());
            statement.setString(3, item.getWithdrawalPoint().getPostalCode());
            statement.setString(4, item.getWithdrawalPoint().getCity());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(final Item item) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_UPDATE);
    }

    @Override
    public void delete(final Item item) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_DELETE);
    }

    @Override
    public List<Item> selectAll() throws BusinessException {
        final List<Item> items = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(SQL_SELECT_ALL);
            while (result.next()) {
                items.add(buildItem(result));
            }
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return items;
    }

    @Override
    public Item selectById(final Integer id) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_SELECT);
    }

    @Override
    public List<Item> selectBy(final String name) throws BusinessException {
        return querySelect(SQL_SELECT_BY_NAME, name, 0);
    }

    @Override
    public List<Item> selectBy(final Integer categoryId) throws BusinessException {
        return querySelect(SQL_SELECT_BY_CATEGORY, "", categoryId);
    }

    @Override
    public List<Item> selectBy(final String name, final Integer categoryId) throws BusinessException {
        return querySelect(SQL_SELECT_BY_NAME_AND_CATEGORY, name, categoryId);
    }

    private List<Item> querySelect(final String query, final String name, final Integer categoryId) throws BusinessException {
        final List<Item> items = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            switch (query) {
                case SQL_SELECT_BY_NAME:
                    statement.setString(1, "%" + name.toUpperCase() + "%");
                    break;
                case SQL_SELECT_BY_CATEGORY:
                    statement.setInt(1, categoryId);
                    break;
                case SQL_SELECT_BY_NAME_AND_CATEGORY:
                    statement.setString(1, "%" + name.toUpperCase() + "%");
                    statement.setInt(2, categoryId);
                    break;
                default:
            }
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                items.add(buildItem(result));
            }
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return items;
    }

    private static Item buildItem(final ResultSet result) throws SQLException {
        return new Item(result.getInt("id_item"), result.getString("item_name"), result.getString("item_description"),
                result.getDate("bid_start_date").toLocalDate(), result.getDate("bid_end_date").toLocalDate(),
                result.getInt("starting_price"), result.getInt("selling_price"), buildUser(result),
                buildCategory(result), buildWithdrawal(result));
    }

    private static User buildUser(final ResultSet result) throws SQLException {
        return new User(result.getInt("id_user"), result.getString("nickname"), result.getString("last_name"),
                result.getString("first_name"), result.getString("email"), result.getString("user_password"),
                result.getString("phone_number"), result.getString("street"), result.getString("postal_code"),
                result.getString("city"), result.getInt("credit"), result.getBoolean("is_admin"));
    }

    private static Category buildCategory(final ResultSet result) throws SQLException {
        return new Category(result.getInt("id_category"), result.getString("category_name"));
    }

    private static Withdrawal buildWithdrawal(final ResultSet result) throws SQLException {
        return new Withdrawal(result.getString("street"), result.getString("postal_code"), result.getString("city"));
    }
}

        /*
        final List<Item> items = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NAME)) {
            statement.setString(1, "%" + name.toUpperCase() + "%");
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                items.add(buildItem(result));
            }
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return items;*/

        /*
        final List<Item> items = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_CATEGORY)) {
            statement.setInt(1, categoryId);
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                items.add(buildItem(result));
            }
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return items;*/

        /*
        final List<Item> items = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NAME_AND_CATEGORY)) {
            statement.setString(1, "%" + name.toUpperCase() + "%");
            statement.setInt(2, categoryId);
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                items.add(buildItem(result));
            }
        } catch (final SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return items;*/