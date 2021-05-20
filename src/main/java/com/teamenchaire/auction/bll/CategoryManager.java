package com.teamenchaire.auction.bll;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.dal.CategoryDAO;
import com.teamenchaire.auction.dal.DAOFactory;

/**
 * A {@code class} which controls categories using a data access object.
 * 
 * @author Marin Taverniers
 */
public final class CategoryManager {
    private final CategoryDAO categoryDAO;

    /**
     * Constructs a {@code CategoryManager} using a data access object.
     */
    public CategoryManager() {
        this.categoryDAO = DAOFactory.getCategoryDAO();
    }

    public List<Category> getCategories() throws BusinessException {
        return categoryDAO.selectAll();
    }

    public Category getCategory(final Integer id) throws BusinessException {
        checkId(id);
        return categoryDAO.selectById(id);
    }

    private void checkId(final Integer id) throws BusinessException {
        if (id == null) {
            throw new BusinessException(BLLErrorCode.CATEGORY_ID_NULL);
        }
    }
}