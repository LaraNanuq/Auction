package com.teamenchaire.auction.bo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A {@code class} which represents a bid from an user on an item.
 * 
 * @author Marin Taverniers
 */
public class Bid implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private LocalDate date;
    private Integer amount;

    /**
     * Constructs a {@code Bid} with empty information.
     */
    public Bid() {
    }

    /**
     * Constructs a {@code Bid} with specified information.
     * 
     * @param date   The date of the bid
     * @param amount The amount of the bid
     */
    public Bid(LocalDate date, Integer amount) {
        this(null, date, amount);
    }

    /**
     * Constructs a {@code Bid} with specified information.
     * 
     * @param id     The id of the bid
     * @param date   The date of the bid
     * @param amount The amount of the bid
     */
    public Bid(Integer id, LocalDate date, Integer amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets all information about this bid.
     * 
     * @return all information about the bid.
     */
    @Override
    public String toString() {
        return String.format("Bid [id=%d, date=%s, amount=%d]", id, date, amount);
    }
}