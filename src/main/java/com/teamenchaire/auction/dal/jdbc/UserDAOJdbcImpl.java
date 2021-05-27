package com.teamenchaire.auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.dal.DALErrorCode;
import com.teamenchaire.auction.dal.UserDAO;

/**
 * A {@code class} which implements CRUD methods for users in the database using
 * the JDBC driver.
 * 
 * @author Ayelen Dumas
 */
public final class UserDAOJdbcImpl implements UserDAO {

    // Insert
    private static final String SQL_INSERT =
            "INSERT INTO users"
            + " (nickname, last_name, first_name, email, user_password, phone_number, street, postal_code, city, credit, is_admin)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Update
    private static final String SQL_UPDATE =
            "UPDATE users"
            + " SET nickname = ?, last_name = ?, first_name = ?, email = ?, user_password = ?, phone_number = ?, street = ?, postal_code = ?, city = ?, credit = ?, is_admin = ?"
            + " WHERE (id_user = ?)";

    // Delete
    private static final String SQL_DELETE =
            "DELETE FROM users"
            + " WHERE (id_user = ?)";

    // Select
    private static final String SQL_SELECT_ALL =
            "SELECT"
            + " id_user, nickname, last_name, first_name, email, user_password, phone_number, street, postal_code, city, credit, is_admin"
            + " FROM users";

    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE (id_user = ?)";
        
    private static final String SQL_SELECT_BY_NICKNAME = SQL_SELECT_ALL
            + " WHERE (upper(nickname) = ?)";

    private static final String SQL_SELECT_BY_EMAIL = SQL_SELECT_ALL
            + " WHERE (upper(email) = ?)";

    /**
     * Constructs a {@code UserDAOJdbcImpl}.
     */
    public UserDAOJdbcImpl() {
        //
    }

    @Override
    public void insert(User user) throws BusinessException {
        try (Connection connection = JdbcConnectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getNickname());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getFirstName());
                statement.setString(4, user.getEmail());
                statement.setString(5, user.getPassword());
                statement.setString(6, user.getPhoneNumber());
                statement.setString(7, user.getStreet());
                statement.setString(8, user.getPostalCode());
                statement.setString(9, user.getCity());
                statement.setInt(10, user.getCredit());
                statement.setBoolean(11, user.isAdmin());
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    user.setId(result.getInt(1));
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_INSERT, e);
        }
    }

    @Override
    public void update(User user) throws BusinessException {
        try (Connection connection = JdbcConnectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
                statement.setString(1, user.getNickname());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getFirstName());
                statement.setString(4, user.getEmail());
                statement.setString(5, user.getPassword());
                statement.setString(6, user.getPhoneNumber());
                statement.setString(7, user.getStreet());
                statement.setString(8, user.getPostalCode());
                statement.setString(9, user.getCity());
                statement.setInt(10, user.getCredit());
                statement.setBoolean(11, user.isAdmin());
                statement.setInt(12, user.getId());
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_UPDATE, e);
        }
    }

    @Override
    public void delete(User user) throws BusinessException {
        try (Connection connection = JdbcConnectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
                statement.setInt(1, user.getId());
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_DELETE, e);
        }
    }

    @Override
    public List<User> selectAll() throws BusinessException {
        throw new BusinessException(DALErrorCode.SQL_SELECT);
    }

    @Override
    public User selectById(Integer id) throws BusinessException {
        User user = null;
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = buildUser(result);
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return user;
    }

    @Override
    public User selectByNickname(String nickname) throws BusinessException {
        User user = null;
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NICKNAME)) {
            statement.setString(1, nickname.toUpperCase());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = buildUser(result);
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return user;
    }

    @Override
    public User selectByEmail(String email) throws BusinessException {
        User user = null;
        try (Connection connection = JdbcConnectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            statement.setString(1, email.toUpperCase());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = buildUser(result);
            }
        } catch (SQLException e) {
            throw new BusinessException(DALErrorCode.SQL_SELECT, e);
        }
        return user;
    }

    private static User buildUser(ResultSet result) throws SQLException {
        return new User(result.getInt("id_user"), result.getString("nickname"), result.getString("last_name"),
                result.getString("first_name"), result.getString("email"), result.getString("user_password"),
                result.getString("phone_number"), result.getString("street"), result.getString("postal_code"),
                result.getString("city"), result.getInt("credit"), result.getBoolean("is_admin"));
    }
}