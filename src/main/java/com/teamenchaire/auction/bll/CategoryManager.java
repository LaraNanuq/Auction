package com.teamenchaire.auction.bll;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.dal.CategoryDAO;
import com.teamenchaire.auction.dal.DAOFactory;

public final class CategoryManager {
    private final CategoryDAO categoryDAO;

    public CategoryManager() {
        this.categoryDAO = DAOFactory.getCategoryDAO();
    }

    public List<Category> getCategories() throws BusinessException {
        return categoryDAO.selectAll();
    }
}