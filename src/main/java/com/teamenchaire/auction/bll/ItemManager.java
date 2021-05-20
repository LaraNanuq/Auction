package com.teamenchaire.auction.bll;

import java.time.LocalDate;
import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.bo.Withdrawal;
import com.teamenchaire.auction.dal.DAOFactory;
import com.teamenchaire.auction.dal.ItemDAO;

/**
 * A {@code class} which controls items using a data access object.
 * 
 * @author Marin Taverniers
 */
public final class ItemManager {
    private ItemDAO itemDAO;

    /**
     * Constructs an {@code ItemManager} using a data access object.
     */
    public ItemManager() {
        this.itemDAO = DAOFactory.getItemDAO();
    }

    public Item addItem(Integer sellerId, String name, String description, Integer categoryId, Integer price,
            LocalDate startDate, LocalDate endDate, String street, String postalCode, String city)
            throws BusinessException {
        User seller = new UserManager().getUser(sellerId);
        checkSeller(seller);
        checkName(name);
        checkDescription(description);
        Category category = new CategoryManager().getCategory(categoryId);
        checkCategory(category);
        checkPrice(price);
        checkDates(startDate, endDate);
        Withdrawal withdrawalPoint = new Withdrawal(street, postalCode, city);
        checkWithdrawalPoint(withdrawalPoint);
        Item item = new Item(name, description, startDate, endDate, price, price, seller, category, withdrawalPoint);
        itemDAO.insert(item);
        return item;
    }

    public List<Item> getItems() throws BusinessException {
        return itemDAO.selectAll();
    }

    public List<Item> getItems(String name) throws BusinessException {
        checkName(name);
        return itemDAO.selectBy(name);
    }

    public List<Item> getItems(Integer categoryId) throws BusinessException {
        Category category = new CategoryManager().getCategory(categoryId);
        checkCategory(category);
        return itemDAO.selectBy(categoryId);
    }

    public List<Item> getItems(String name, Integer categoryId) throws BusinessException {
        checkName(name);
        Category category = new CategoryManager().getCategory(categoryId);
        checkCategory(category);
        return itemDAO.selectBy(name, categoryId);
    }

    /* Validation */

    private void checkSeller(User seller) throws BusinessException {
        if ((seller == null) || (seller.getId() == null)) {
            throw new BusinessException(BLLErrorCode.ITEM_SELLER_NULL);
        }
    }

    private void checkName(String name) throws BusinessException {
        if (isStringNull(name)) {
            throw new BusinessException(BLLErrorCode.ITEM_NAME_NULL);
        }
    }

    private void checkDescription(String description) throws BusinessException {
        if (isStringNull(description)) {
            throw new BusinessException(BLLErrorCode.ITEM_DESCRIPTION_NULL);
        }
    }

    private void checkCategory(Category category) throws BusinessException {
        if ((category == null) || (category.getId() == null)) {
            throw new BusinessException(BLLErrorCode.ITEM_CATEGORY_NULL);
        }
    }

    private void checkPrice(Integer startingPrice) throws BusinessException {
        if ((startingPrice == null) || (startingPrice < 0)) {
            throw new BusinessException(BLLErrorCode.ITEM_STARTING_PRICE_INVALID);
        }
    }

    private void checkDates(LocalDate startDate, LocalDate endDate) throws BusinessException {
        if (startDate == null) {
            throw new BusinessException(BLLErrorCode.ITEM_START_DATE_NULL);
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new BusinessException(BLLErrorCode.ITEM_START_DATE_INVALID);
        }
        if (endDate == null) {
            throw new BusinessException(BLLErrorCode.ITEM_END_DATE_NULL);
        }
        if (startDate.isAfter(endDate)) {
            throw new BusinessException(BLLErrorCode.ITEM_DATES_INVALID);
        }
    }

    private void checkWithdrawalPoint(Withdrawal withdrawalPoint) throws BusinessException {
        if (withdrawalPoint == null) {
            throw new BusinessException(BLLErrorCode.ITEM_WITHDRAWAL_NULL);
        }
        if (isStringNull(withdrawalPoint.getStreet())) {
            throw new BusinessException(BLLErrorCode.ITEM_WITHDRAWAL_STREET_NULL);
        }
        if (isStringNull(withdrawalPoint.getPostalCode())) {
            throw new BusinessException(BLLErrorCode.ITEM_WITHDRAWAL_POSTAL_CODE_NULL);
        }
        if (isStringNull(withdrawalPoint.getCity())) {
            throw new BusinessException(BLLErrorCode.ITEM_WITHDRAWAL_CITY_NULL);
        }
    }

    private boolean isStringNull(String s) {
        return ((s == null) || (s.trim().isEmpty()));
    }
}