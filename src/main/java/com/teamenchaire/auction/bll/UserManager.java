package com.teamenchaire.auction.bll;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.dal.DAOFactory;
import com.teamenchaire.auction.dal.UserDAO;

/**
 * A {@code class} which controls users using a data access object.
 *
 * @author Ayelen Dumas
 * @author Marin Taverniers
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
        checkNickname(nickname);
        checkLastName(lastName);
        checkFirstName(firstName);
        checkEmail(email);
        checkPhoneNumber(phoneNumber);
        checkStreet(street);
        checkPostalCode(postalCode);
        checkCity(city);
        checkPassword(password);
        checkNicknameExists(nickname);
        checkEmailExists(email);

        // Add
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
        checkPhoneNumber(phoneNumber);
        checkStreet(street);
        checkPostalCode(postalCode);
        checkCity(city);
        checkPassword(password);
        User user = getUserById(id);
        checkUser(user);
        if (!nickname.equals(user.getNickname())) {
            checkNicknameExists(nickname);
        }
        if (!email.equals(user.getEmail())) {
            checkEmailExists(email);
        }

        // Update
        user.setNickname(nickname);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setStreet(street);
        user.setPostalCode(postalCode);
        user.setCity(city);
        user.setPassword(password);
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

        // Remove
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

    public User getUserByLogin(String username, String password) throws BusinessException {
        checkUserName(username);
        checkPassword(password);
        User user = null;
        if (username.contains("@")) {
            user = getUserByEmail(username);
        } else {
            user = getUserByNickname(username);
        }
        if (user == null) {
            throw new BusinessException(BLLErrorCode.USER_UNKNOWN);
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_INVALID);
        }
        return user;
    }

    /* Validation */

    private void checkNickname(String nickname) throws BusinessException {
        if (isStringEmpty(nickname)) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_NULL);
        }
        if (!isAlphaNumeric(nickname)) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_INVALID);
        }
        if (exceedsLength(30, nickname)) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_TOO_LONG);
        }
    }

    private void checkNicknameExists(String nickname) throws BusinessException {
        if (getUserByNickname(nickname) != null) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }
    }

    private void checkLastName(String lastName) throws BusinessException {
        if (isStringEmpty(lastName)) {
            throw new BusinessException(BLLErrorCode.USER_LAST_NAME_NULL);
        }
        if (exceedsLength(30, lastName)) {
            throw new BusinessException(BLLErrorCode.USER_LAST_NAME_TOO_LONG);
        }
    }

    private void checkFirstName(String firstName) throws BusinessException {
        if (isStringEmpty(firstName)) {
            throw new BusinessException(BLLErrorCode.USER_FIRST_NAME_NULL);
        }
        if (exceedsLength(30, firstName)) {
            throw new BusinessException(BLLErrorCode.USER_FIRST_NAME_TOO_LONG);
        }
    }

    private void checkEmail(String email) throws BusinessException {
        if (isStringEmpty(email)) {
            throw new BusinessException(BLLErrorCode.USER_EMAIL_NULL);
        }
        if (exceedsLength(50, email)) {
            throw new BusinessException(BLLErrorCode.USER_EMAIL_TOO_LONG);
        }
    }

    private void checkEmailExists(String email) throws BusinessException {
        if (getUserByEmail(email) != null) {
            throw new BusinessException(BLLErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkPhoneNumber(String phoneNumber) throws BusinessException {
        if ((phoneNumber != null) && (exceedsLength(15, phoneNumber))) {
            throw new BusinessException(BLLErrorCode.USER_PHONE_NUMBER_TOO_LONG);
        }
    }

    private void checkStreet(String street) throws BusinessException {
        if (isStringEmpty(street)) {
            throw new BusinessException(BLLErrorCode.USER_STREET_NULL);
        }
        if (exceedsLength(50, street)) {
            throw new BusinessException(BLLErrorCode.USER_STREET_TOO_LONG);
        }
    }

    private void checkPostalCode(String postalCode) throws BusinessException {
        if (isStringEmpty(postalCode)) {
            throw new BusinessException(BLLErrorCode.USER_POSTAL_CODE_NULL);
        }
        if (exceedsLength(10, postalCode)) {
            throw new BusinessException(BLLErrorCode.USER_POSTAL_CODE_TOO_LONG);
        }
    }

    private void checkCity(String city) throws BusinessException {
        if (isStringEmpty(city)) {
            throw new BusinessException(BLLErrorCode.USER_CITY_NULL);
        }
        if (exceedsLength(30, city)) {
            throw new BusinessException(BLLErrorCode.USER_CITY_TOO_LONG);
        }
    }

    private void checkPassword(String password) throws BusinessException {
        if (isStringEmpty(password)) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_NULL);
        }
        if (exceedsLength(30, password)) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_TOO_LONG);
        }
    }

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

    private void checkUserName(String username) throws BusinessException {
        if (isStringEmpty(username)) {
            throw new BusinessException(BLLErrorCode.USER_USER_NAME_NULL);
        }
    }

    private boolean isStringEmpty(String s) {
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

    public boolean exceedsLength(int length, String s) {
        return (s.length() > length);
    }
}