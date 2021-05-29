package com.teamenchaire.auction.bo;

import java.io.Serializable;

/**
 * A {@code class} which represents a withdrawal point of an item.
 * 
 * @author Marin Taverniers
 */
public class Withdrawal implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * @param street     The street of the withdrawal point
     * @param postalCode The postal code of the withdrawal point
     * @param city       The city of the withdrawal point
     */
    public Withdrawal(String street, String postalCode, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
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
     * Returns all information about this withdrawal point.
     * 
     * @return all information about the withdrawal point.
     */
    @Override
    public String toString() {
        return String.format("Withdrawal [street=%s, postalCode=%s, city=%s]", street, postalCode, city);
    }
}