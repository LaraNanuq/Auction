package com.teamenchaire.auction.bo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A {@code class} which represents an item sold by an user.
 * 
 * @author Marin Taverniers
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer startingPrice;
    private Integer sellingPrice;
    private User seller;
    private Category category;
    private Withdrawal withdrawalPoint;

    /**
     * Constructs an {@code Item} with empty information.
     */
    public Item() {
    }

    /**
     * Constructs an {@code Item} with specified information.
     * 
     * @param name            The name of the item
     * @param description     The description of the item
     * @param startDate       The bid start date of the item
     * @param endDate         The bid end date of the item
     * @param startingPrice   The starting price of the item
     * @param sellingPrice    The selling price of the item
     * @param seller          The user selling the item
     * @param category        The category of the item
     * @param withdrawalPoint The withdrawal point of the item
     */
    public Item(String name, String description, LocalDate startDate, LocalDate endDate, Integer startingPrice,
            Integer sellingPrice, User seller, Category category, Withdrawal withdrawalPoint) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.sellingPrice = sellingPrice;
        this.seller = seller;
        this.category = category;
        this.withdrawalPoint = withdrawalPoint;
    }

    /**
     * Constructs an {@code Item} with specified information.
     * 
     * @param id              The id of the item
     * @param name            The name of the item
     * @param description     The description of the item
     * @param startDate       The bid start date of the item
     * @param endDate         The bid end date of the item
     * @param startingPrice   The starting price of the item
     * @param sellingPrice    The selling price of the item
     * @param seller          The user selling the item
     * @param category        The category of the item
     * @param withdrawalPoint The withdrawal point of the item
     */
    public Item(Integer id, String name, String description, LocalDate startDate, LocalDate endDate,
            Integer startingPrice, Integer sellingPrice, User seller, Category category, Withdrawal withdrawalPoint) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.sellingPrice = sellingPrice;
        this.seller = seller;
        this.category = category;
        this.withdrawalPoint = withdrawalPoint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Integer startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Withdrawal getWithdrawalPoint() {
        return withdrawalPoint;
    }

    public void setWithdrawalPoint(Withdrawal withdrawalPoint) {
        this.withdrawalPoint = withdrawalPoint;
    }

    /**
     * Gets all information about this item.
     * 
     * @return all information about the item.
     */
    @Override
    public String toString() {
        return String.format(
                "Item [id=%d, name=%s, description=%s, startDate=%s, endDate=%s, startingPrice=%d, sellingPrice=%d, seller=%s, category=%s, withdrawalPoint=%s]",
                id, name, description, startDate, endDate, startingPrice, sellingPrice, seller, category,
                withdrawalPoint);
    }
}