package com.teamenchaire.auction.bll;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.dal.DAOFactory;
import com.teamenchaire.auction.dal.UserDAO;

/**
 * A {@code class} which controls users using a data access object.
 * 
 * @author Ayelen Dumas
 */
public final class UserManager {
    private static final Integer CREDIT_DEFAULT = 100;
    private UserDAO userDAO;

    /**
     * Constructs an {@code UserManager} using a data access object.
     */
    public UserManager() {
        this.userDAO = DAOFactory.getUserDAO();
    }

    public User addUser(String nickname, String lastName, String firstName, String email, String password,
            String phoneNumber, String street, String postalCode, String city) throws BusinessException {

        // Vérifications générales :
        checkNickname(nickname);
        checkLastName(lastName);
        checkFirstName(firstName);
        checkEmail(email);
        checkPassword(password);
        checkPhoneNumber(phoneNumber);
        checkStreet(street);
        checkPostalCode(postalCode);
        checkCity(city);

        // TODO
        // Vérifications spécifiques à l'insertion :
        // + Ajouter la vérification de pseudo unique
        // + Ajouter la vérification de mail unique

        // Si tout est bon :
        User user = new User(nickname, lastName, firstName, email, password, phoneNumber, street, postalCode, city,
                CREDIT_DEFAULT, false);
        userDAO.insert(user);
        return user;
    }

    public User updateUser(Integer id, String nickname, String lastName, String firstName, String email,
            String password, String phoneNumber, String street, String postalCode, String city)
            throws BusinessException {
        checkNickname(nickname);
        checkLastName(lastName);
        checkFirstName(firstName);
        checkEmail(email);
        checkPassword(password);
        checkPhoneNumber(phoneNumber);
        checkStreet(street);
        checkPostalCode(postalCode);
        checkCity(city);
        User user = getUserById(id);
        checkUser(user);
        user.setNickname(nickname);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setStreet(street);
        user.setPostalCode(postalCode);
        user.setCity(city);
        userDAO.update(user);
        return user;
    }

    public void removeUser(Integer id) throws BusinessException {
        User user = getUserById(id);
        checkUser(user);

        // TODO
        // Vérifications spécifiques à la suppression :
        // + Vérifier que l'utilisateur n'a pas d'articles en vente
        // + Vérifier que l'utilisateur n'a pas d'enchères en cours

        userDAO.delete(user);
    }

    public User getUserById(Integer id) throws BusinessException {
        checkId(id);
        return userDAO.selectById(id);
    }

    public User getUserByNickname(String nickname) throws BusinessException {
        checkNickname(nickname);
        return userDAO.selectByNickname(nickname);
    }

    public User getUserByEmail(String email) throws BusinessException {
        checkEmail(email);
        return userDAO.selectByEmail(email);
    }

    public User getUserByUserName(String userName, String password) throws BusinessException {
        checkUserName(userName);
        checkPassword(password);
        User user = null;
        if (userName.contains("@")) {
            user = getUserByEmail(userName);
        } else {
            user = getUserByNickname(userName);
        }
        if (user == null) {
            throw new BusinessException(BLLErrorCode.USER_UNKNOWN);
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_TOO_LONG);
        }
        return user;
    }

    /* Validation */

    // TODO

    // Définir les conditions de validation appelées à la création ou la mise à jour
    // (de manière non spécifique)

    private void checkUser(User user) throws BusinessException {
        if (user == null) {
            throw new BusinessException(BLLErrorCode.USER_NULL);
        }
    }

    private void checkId(Integer id) throws BusinessException {
        if (id == null) {
            throw new BusinessException(BLLErrorCode.USER_ID_NULL);
        }
    }

    private void checkNickname(String nickname) throws BusinessException {
        if (isStringNull(nickname)) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_NULL);
        }
        if ((!isAlphaNumeric(nickname)) || (isTooLong(30, nickname))) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_INVALID);
        }
    }

    private void checkLastName(String lastName) throws BusinessException {
        if (isStringNull(lastName)) {
            throw new BusinessException(BLLErrorCode.USER_LAST_NAME_NULL);
        }
        if (isTooLong(30, lastName)) {
            throw new BusinessException(BLLErrorCode.USER_LAST_NAME_TOO_LONG);
        }
    }

    private void checkFirstName(String firstName) throws BusinessException {
        if (isStringNull(firstName)) {
            throw new BusinessException(BLLErrorCode.USER_FIRST_NAME_NULL);
        }
        if (isTooLong(30, firstName)) {
            throw new BusinessException(BLLErrorCode.USER_FIRST_NAME_TOO_LONG);
        }
    }

    private void checkEmail(String email) throws BusinessException {
        if (isStringNull(email)) {
            throw new BusinessException(BLLErrorCode.USER_EMAIL_NULL);
        }
        if (isTooLong(50, email)) {
            throw new BusinessException(BLLErrorCode.USER_EMAIL_TOO_LONG);
        }
    }

    private void checkPassword(String password) throws BusinessException {
        if (isStringNull(password)) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_NULL);
        }
        if (isTooLong(30, password)) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_TOO_LONG);
        }
    }

    private void checkPhoneNumber(String phoneNumber) throws BusinessException {
        if ((phoneNumber != null) && (isTooLong(15, phoneNumber))) {
            throw new BusinessException(BLLErrorCode.PHONE_NUMBER_TOO_LONG);
        }
    }

    private void checkStreet(String street) throws BusinessException {
        if (isStringNull(street)) {
            throw new BusinessException(BLLErrorCode.USER_STREET_NULL);
        }
        if (isTooLong(50, street)) {
            throw new BusinessException(BLLErrorCode.USER_STREET_TOO_LONG);
        }
    }

    private void checkPostalCode(String postalCode) throws BusinessException {
        if (isStringNull(postalCode)) {
            throw new BusinessException(BLLErrorCode.USER_POSTAL_CODE_NULL);
        }
        if (isTooLong(10, postalCode)) {
            throw new BusinessException(BLLErrorCode.USER_POSTAL_CODE_TOO_LONG);
        }
    }

    private void checkCity(String city) throws BusinessException {
        if (isStringNull(city)) {
            throw new BusinessException(BLLErrorCode.USER_CITY_NULL);
        }
        if (isTooLong(30, city)) {
            throw new BusinessException(BLLErrorCode.USER_CITY_TOO_LONG);
        }
    }

    private void checkUserName(String userName) throws BusinessException {
        if (isStringNull(userName)) {
            throw new BusinessException(BLLErrorCode.USER_USER_NAME_NULL);
        }
    }

    private boolean isStringNull(String s) {
        return ((s == null) || (s.trim().isEmpty()));
    }

    public boolean isAlphaNumeric(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isTooLong(int limit, String s) {
        return (s.length() > limit);
    }
}