package com.teamenchaire.auction.dal;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.User;

/**
 * An {@code interface} which defines CRUD methods for users in the database.
 * 
 * @author Ayelen Dumas
 * @author Marin Taverniers
 */
public interface UserDAO extends GenericDAO<User> {
    User selectByNickname(String nickname) throws BusinessException;

    User selectByEmail(String email) throws BusinessException;
}