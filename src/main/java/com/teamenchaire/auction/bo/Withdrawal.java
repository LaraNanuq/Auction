package com.teamenchaire.auction.bo;

import java.io.Serializable;

/**
 * A {@code class} which represents an item withdrawal.
 * 
 * @author Marin Taverniers
 */
public class Withdrawal implements Serializable {
    private Integer id;
    private String street;
    private String postalCode;
    private String city;

    /**
     * Constructs a {@code Withdrawal} with empty information.
     */
    public Withdrawal() {
    }

    /**
     * Constructs a {@code Withdrawal} with specified information.
     * 
     * @param street     The street of the withdrawal
     * @param postalCode The postal code of the withdrawal
     * @param city       The city oof the withdrawal
     */
    public Withdrawal(String street, String postalCode, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    /**
     * Constructs a {@code Withdrawal} with specified information.
     * 
     * @param id         The id of the withdrawal
     * @param street     The street of the withdrawal
     * @param postalCode The postal code of the withdrawal
     * @param city       The city oof the withdrawal
     */
    public Withdrawal(Integer id, String street, String postalCode, String city) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    /**
     * Gets all information about this withdrawal.
     * 
     * @return all information about the withdrawal.
     */
    @Override
    public String toString() {
        return String.format("Withdrawal [id=%d, street=%s, postalCode=%s, city=%s]", id, street, postalCode, city);
    }
}