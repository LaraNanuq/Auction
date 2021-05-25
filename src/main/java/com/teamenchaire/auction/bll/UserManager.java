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

        // Vérifications spécifiques à l'insertion :
            // + Ajouter la vérification de pseudo unique
            // + Ajouter la vérification de mail unique

        // Si tout est bon :
        User user = new User(nickname, lastName, firstName, email, password, phoneNumber, street, postalCode, city,
                CREDIT_DEFAULT, false);
        userDAO.insert(user);
        return user;
    }

    public User updateUser(User user, String nickname, String lastName, String firstName, String email, String password,
            String phoneNumber, String street, String postalCode, String city) throws BusinessException {
        checkUser(user);
        checkNickname(nickname);
        checkLastName(lastName);
        checkFirstName(firstName);
        checkEmail(email);
        checkPassword(password);
        checkPhoneNumber(phoneNumber);
        checkStreet(street);
        checkPostalCode(postalCode);
        checkCity(city);

        // Si tout est bon, créer un objet "temporaire" pour faire la maj dans la bdd
        // (car en cas d'erreur, l'objet "user" ne doit pas encore bouger)
        // Donc pas de setter, mais je réfléchis encore si c'est une bonne solution...
        User userUpdated = new User(user.getId(), nickname, lastName, firstName, email, password, phoneNumber, street,
                postalCode, city, user.getCredit(), user.isAdmin());
        userDAO.update(userUpdated);
        return userUpdated;
    }

    public void removeUser(User user) throws BusinessException {
        checkUser(user);

        // Vérifications spécifiques à la suppression :
            // + Vérifier que l'utilisateur n'a pas d'articles en vente
            // + Vérifier que l'utilisateur n'a pas d'enchères en cours

        userDAO.delete(user);
    }

    public User getUserByNickname(String nickname) throws BusinessException {
        checkNickname(nickname);
        return userDAO.selectByNickname(nickname);
    }

    public User getUserByEmail(String email) throws BusinessException {
        checkEmail(email);
        return userDAO.selectByEmail(email);
    }

    // Plusieurs possibilités, là :
    // 1-Je vérifie les paramètres
    // 2-Je regarde s'il y a un @ dans le nom pour appeler la bonne méthode
    // 3-Je vérifie l'exactitude du mot de passe
    // 4-Une erreur différente est levée pour tous les cas
    //   Sinon, je retourne l'utilisateur "connecté"
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
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_INVALID);
        }
        return user;
    }

    /* Validation */

    // Définir les conditions de validation appelées à la création ou la mise à jour
    // (de manière non spécifique)

    // Penser à regarder le script de création de table pour vérifier si l'attribut
    // peut être nul et sa longueur maximum !

    // Créer des erreurs explicites dans BLLErrorCode

    // A toi de jouer (hihi) !

    private void checkUser(User user) throws BusinessException {

    }

    private void checkNickname(String nickname) throws BusinessException {
        if (nickname == null) {
            throw new BusinessException(BLLErrorCode.USER_NICKNAME_NULL);
        }
        // + Ajouter une vérification que le pseudo ne contient que des caractères
    }

    private void checkLastName(String lastName) throws BusinessException {

    }

    private void checkFirstName(String firstName) throws BusinessException {

    }

    private void checkEmail(String email) throws BusinessException {

    }

    private void checkPassword(String password) throws BusinessException {
        if ((password == null) || (password.isEmpty())) {
            throw new BusinessException(BLLErrorCode.USER_PASSWORD_NULL);
        }
    }

    private void checkPhoneNumber(String phoneNumber) throws BusinessException {

    }

    private void checkStreet(String street) throws BusinessException {

    }

    private void checkPostalCode(String postalCode) throws BusinessException {

    }

    private void checkCity(String city) throws BusinessException {

    }

    private void checkUserName(String userName) throws BusinessException {
        if (isStringNull(userName)) {
            throw new BusinessException(BLLErrorCode.USER_USER_NAME_NULL);
        }
    }

    private boolean isStringNull(String s) {
        return ((s == null) || (s.trim().isEmpty()));
    }
}