package com.teamenchaire.auction.bo;

import java.io.Serializable;

/**
 * A {@code class} which represents an user who can sell and buy items.
 * 
 * @author Marin Taverniers
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nickname;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String phoneNumber;
    private String street;
    private String postalCode;
    private String city;
    private Integer credit;
    private Boolean isAdmin;

    /**
     * Constructs an {@code User} with empty information.
     */
    public User() {
    }

    /**
     * Constructs an {@code User} with specified information.
     * 
     * @param nickname    The nickname of the user
     * @param lastName    The last name of the user
     * @param firstName   The first name of the user
     * @param email       The email of the user
     * @param password    The password of the user
     * @param phoneNumber The phone number of the user
     * @param street      The street of the address of the user
     * @param postalCode  The postal code of the address of the user
     * @param city        The city of the address of the user
     * @param credit      The credit amount of the user
     * @param isAdmin     If the user is an administrator
     */
    public User(String nickname, String lastName, String firstName, String email, String password, String phoneNumber,
            String street, String postalCode, String city, Integer credit, Boolean isAdmin) {
        this(null, nickname, lastName, firstName, email, password, phoneNumber, street, postalCode, city, credit,
                isAdmin);
    }

    /**
     * Constructs an {@code User} with specified information.
     * 
     * @param id          The id of the user
     * @param nickname    The nickname of the user
     * @param lastName    The last name of the user
     * @param firstName   The first name of the user
     * @param email       The email of the user
     * @param password    The password of the user
     * @param phoneNumber The phone number of the user
     * @param street      The street of the address of the user
     * @param postalCode  The postal code of the address of the user
     * @param city        The city of the address of the user
     * @param credit      The credit amount of the user
     * @param isAdmin     If the user is an administrator
     */
    public User(Integer id, String nickname, String lastName, String firstName, String email, String password,
            String phoneNumber, String street, String postalCode, String city, Integer credit, Boolean isAdmin) {
        this.id = id;
        this.nickname = nickname;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.credit = credit;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Gets all information about this user.
     * 
     * @return all information about the user.
     */
    @Override
    public String toString() {
        return String.format(
                "User [id=%d, nickname=%s, lastName=%s, firstName=%s, email=%s, password=%s, phoneNumber=%s, street=%s, postalCode=%s, city=%s, credit=%d, isAdmin=%s]",
                id, nickname, lastName, firstName, email, password, phoneNumber, street, postalCode, city, credit,
                isAdmin);
    }
}