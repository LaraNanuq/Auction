package com.teamenchaire.auction.bo;

import java.io.Serializable;

/**
 * A {@code class} which represents an item category.
 * 
 * @author Marin Taverniers
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String name;

    /**
     * Constructs a {@code Category} with empty information.
     */
    public Category() {
    }

    /**
     * Constructs a {@code Category} with specified information.
     * 
     * @param name The name of the category
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Constructs a {@code Category} with specified information.
     * 
     * @param id   The id of the category
     * @param name The name of the category
     */
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    /**
     * Gets all information about this category.
     * 
     * @return all information about the category.
     */
    @Override
    public String toString() {
        return String.format("Category [id=%d, name=%s]", id, name);
    }
}