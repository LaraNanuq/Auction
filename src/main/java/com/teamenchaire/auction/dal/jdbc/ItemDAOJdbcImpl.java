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

    // Insert
    private static final String SQL_INSERT_ITEM =
            "INSERT INTO items"
            + " (item_name, item_description, bid_start_date, bid_end_date, starting_price, selling_price, id_user, id_category)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_WITHDRAWAL =
            "INSERT INTO withdrawals"
            + " (id_item, street, postal_code, city)"
            + " VALUES (?, ?, ?, ?)";

    // Select
    private static final String SQL_SELECT_ALL =
            "SELECT"
            + " i.id_item, i.item_name, i.item_description, i.bid_start_date, i.bid_end_date, i.starting_price, i.selling_price"
            + ", u.id_user, u.nickname, u.last_name, u.first_name, u.email, u.user_password, u.phone_number, u.street AS u_street, u.postal_code AS u_postal_code, u.city AS u_city, u.credit, u.is_admin"
            + ", c.id_category, c.category_name"
            + ", w.street AS w_street, w.postal_code AS w_postal_code, w.city AS w_city"
            + " FROM items i"
            + " JOIN users u ON (i.id_user = u.id_user)"
            + " JOIN categories c ON (i.id_category = c.id_category)"
            + " JOIN withdrawals w ON (i.id_item = w.id_item)";

    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE (i.id_item = ?)";

    private static final String SQL_WHERE_HAS_BEGAN = "dateDiff(day, getDate(), i.bid_start_date) <= 0";
    private static final String SQL_WHERE_HAS_ENDED = "dateDiff(day, getDate(), i.bid_end_date) < 0";

    private static final String SQL_SELECT_ALL_AVAILABLE = SQL_SELECT_ALL
            + " WHERE (" + SQL_WHERE_HAS_BEGAN + ") AND (NOT " + SQL_WHERE_HAS_ENDED + ")";

    private static final String SQL_SELECT_AVAILABLE_PURCHASES = SQL_SELECT_ALL
            + " WHERE (i.id_user != ?) AND (" + SQL_WHERE_HAS_BEGAN + ") AND (NOT " + SQL_WHERE_HAS_ENDED + ")";

    private static final String SQL_SELECT_CURRENT_PURCHASES = SQL_SELECT_ALL
            + " LEFT JOIN bids b ON (i.id_item = b.id_item)"
            + " WHERE (b.id_user = ?) AND (NOT " + SQL_WHERE_HAS_ENDED + ")";

    private static final String SQL_SELECT_WON_PURCHASES = SQL_SELECT_ALL
            + " LEFT JOIN bids b ON (i.id_item = b.id_item)"
            + " WHERE (b.id_user = ?) AND (b.bid_amount = i.selling_price) AND (" + SQL_WHERE_HAS_ENDED + ")";

    private static final String SQL_SELECT_CURRENT_SALES = SQL_SELECT_ALL
            + " WHERE (i.id_user = ?) AND (" + SQL_WHERE_HAS_BEGAN + ") AND (NOT " + SQL_WHERE_HAS_ENDED + ")";

    private static final String SQL_SELECT_FUTURE_SALES = SQL_SELECT_ALL
            + " WHERE (i.id_user = ?) AND (NOT " + SQL_WHERE_HAS_BEGAN + ")";

    private static final String SQL_SELECT_ENDED_SALES = SQL_SELECT_ALL
            + " WHERE (i.id_user = ?) AND (" + SQL_WHERE_HAS_ENDED + ")";

    // Additional select clause
    private static final String SQL_AND_WHERE_NAME =
            " AND (upper(item_name) LIKE ?)";

    private static final String SQL_AND_WHERE_CATEGORY =
            " AND (c.id_category = ?)";

    private static final String SQL_ORDER_BY =
            " ORDER BY i.bid_start_date DESC";

    /**
     * Constructs an {@code ItemDAOJdbcImpl}.
     */
    public ItemDAOJdbcImpl() {
        //
    }

    @Override
    public void insert(Item item) throws BusinessException {
        try (Connection connection = JdbcConnectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                insertItem(connection, item);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_INSERT, e);
        }
    }

    private void insertItem(Connection connection, Item item) throws SQLException {
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
            ResultSet result = statement.getGeneratedKeys();
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
    public void update(Item item) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_UPDATE);
    }

    @Override
    public void delete(Item item) throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_DELETE);
    }

    @Override
    public List<Item> selectAll() throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_SELECT);
    }

    @Override
    public Item selectById(Integer id) throws BusinessException {
        Item item = null;
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                item = buildItem(result);
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return item;
    }

    @Override
    public List<Item> selectAllAvailable(String name, Integer categoryId) throws BusinessException {
        return selectWithFilter(SQL_SELECT_ALL_AVAILABLE, null, name, categoryId);
    }

    @Override
    public List<Item> selectAvailablePurchases(Integer userId, String name, Integer categoryId)
            throws BusinessException {
        return selectWithFilter(SQL_SELECT_AVAILABLE_PURCHASES, userId, name, categoryId);
    }

    @Override
    public List<Item> selectCurrentPurchases(Integer userId, String name, Integer categoryId) throws BusinessException {
        return selectWithFilter(SQL_SELECT_CURRENT_PURCHASES, userId, name, categoryId);
    }

    @Override
    public List<Item> selectWonPurchases(Integer userId, String name, Integer categoryId) throws BusinessException {
        return selectWithFilter(SQL_SELECT_WON_PURCHASES, userId, name, categoryId);
    }

    @Override
    public List<Item> selectCurrentSales(Integer userId, String name, Integer categoryId) throws BusinessException {
        return selectWithFilter(SQL_SELECT_CURRENT_SALES, userId, name, categoryId);
    }

    @Override
    public List<Item> selectFutureSales(Integer userId, String name, Integer categoryId) throws BusinessException {
        return selectWithFilter(SQL_SELECT_FUTURE_SALES, userId, name, categoryId);
    }

    @Override
    public List<Item> selectEndedSales(Integer userId, String name, Integer categoryId) throws BusinessException {
        return selectWithFilter(SQL_SELECT_ENDED_SALES, userId, name, categoryId);
    }

    private List<Item> selectWithFilter(String query, Integer userId, String name, Integer categoryId)
            throws BusinessException {
        List<Item> items = new ArrayList<>();
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(buildFilterQuery(query, name, categoryId))) {
            int paramIndex = 1;
            if (userId != null) {
                statement.setInt(paramIndex, userId);
                paramIndex++;
            }
            if (name != null) {
                statement.setString(paramIndex, "%" + name.toUpperCase() + "%");
                paramIndex++;
            }
            if (categoryId != null) {
                statement.setInt(paramIndex, categoryId);
                paramIndex++;
            }
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                items.add(buildItem(result));
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return items;
    }

    private String buildFilterQuery(String query, String name, Integer categoryId) {
        StringBuilder filterQuery = new StringBuilder();
        filterQuery.append(query);
        if (name != null) {
            filterQuery.append(" ");
            filterQuery.append(SQL_AND_WHERE_NAME);
        }
        if (categoryId != null) {
            filterQuery.append(" ");
            filterQuery.append(SQL_AND_WHERE_CATEGORY);
        }
        filterQuery.append(SQL_ORDER_BY);
        return filterQuery.toString();
    }

    private static Item buildItem(ResultSet result) throws SQLException {
        return new Item(result.getInt("id_item"), result.getString("item_name"), result.getString("item_description"),
                result.getDate("bid_start_date").toLocalDate(), result.getDate("bid_end_date").toLocalDate(),
                result.getInt("starting_price"), result.getInt("selling_price"), buildUser(result),
                buildCategory(result), buildWithdrawal(result));
    }

    private static User buildUser(ResultSet result) throws SQLException {
        return new User(result.getInt("id_user"), result.getString("nickname"), result.getString("last_name"),
                result.getString("first_name"), result.getString("email"), result.getString("user_password"),
                result.getString("phone_number"), result.getString("u_street"), result.getString("u_postal_code"),
                result.getString("u_city"), result.getInt("credit"), result.getBoolean("is_admin"));
    }

    private static Category buildCategory(ResultSet result) throws SQLException {
        return new Category(result.getInt("id_category"), result.getString("category_name"));
    }

    private static Withdrawal buildWithdrawal(ResultSet result) throws SQLException {
        return new Withdrawal(result.getString("w_street"), result.getString("w_postal_code"),
                result.getString("w_city"));
    }
}