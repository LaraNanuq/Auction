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
        checkName(name);
        checkDescription(description);
        Category category = new CategoryManager().getCategoryById(categoryId);
        checkCategory(category);
        checkPrice(price);
        checkDates(startDate, endDate);
        checkStreet(street);
        checkPostalCode(postalCode);
        checkCity(city);
        User seller = new UserManager().getUserById(sellerId);
        checkSeller(seller);

        // Add
        Withdrawal withdrawalPoint = new Withdrawal(street, postalCode, city);
        Item item = new Item(name, description, startDate, endDate, price, price, seller, category, withdrawalPoint);
        itemDAO.insert(item);
        return item;
    }

    public Item getItemById(Integer id) throws BusinessException {
        checkId(id);
        return itemDAO.selectById(id);
    }

    public List<Item> getAllAvailableItems(String name, Integer categoryId) throws BusinessException {
        return itemDAO.selectAllAvailable(name, categoryId);
    }

    public List<Item> getAvailablePurchasesItems(Integer userId, String name, Integer categoryId)
            throws BusinessException {
        checkUserId(userId);
        return itemDAO.selectAvailablePurchases(userId, name, categoryId);
    }

    public List<Item> getCurrentPurchasesItems(Integer userId, String name, Integer categoryId)
            throws BusinessException {
        checkUserId(userId);
        return itemDAO.selectCurrentPurchases(userId, name, categoryId);
    }

    public List<Item> getWonPurchasesItems(Integer userId, String name, Integer categoryId) throws BusinessException {
        checkUserId(userId);
        return itemDAO.selectWonPurchases(userId, name, categoryId);
    }

    public List<Item> getCurrentSalesItems(Integer userId, String name, Integer categoryId) throws BusinessException {
        checkUserId(userId);
        return itemDAO.selectCurrentSales(userId, name, categoryId);
    }

    public List<Item> getFutureSalesItems(Integer userId, String name, Integer categoryId) throws BusinessException {
        checkUserId(userId);
        return itemDAO.selectFutureSales(userId, name, categoryId);
    }

    public List<Item> getEndedSalesItems(Integer userId, String name, Integer categoryId) throws BusinessException {
        checkUserId(userId);
        return itemDAO.selectEndedSales(userId, name, categoryId);
    }

    /* Validation */

    private void checkName(String name) throws BusinessException {
        if (isStringNull(name)) {
            throw new BusinessException(BLLErrorCode.ITEM_NAME_NULL);
        }
        if (name.length() > 30) {
            throw new BusinessException(BLLErrorCode.ITEM_NAME_TOO_LONG);
        }
    }

    private void checkDescription(String description) throws BusinessException {
        if (isStringNull(description)) {
            throw new BusinessException(BLLErrorCode.ITEM_DESCRIPTION_NULL);
        }
        if (description.length() > 300) {
            throw new BusinessException(BLLErrorCode.ITEM_DESCRIPTION_TOO_LONG);
        }
    }

    private void checkCategory(Category category) throws BusinessException {
        if (category == null) {
            throw new BusinessException(BLLErrorCode.ITEM_CATEGORY_NULL);
        }
    }

    private void checkPrice(Integer startingPrice) throws BusinessException {
        if (startingPrice == null) {
            throw new BusinessException(BLLErrorCode.ITEM_STARTING_PRICE_NULL);
        }
        if (startingPrice < 0) {
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
            throw new BusinessException(BLLErrorCode.ITEM_END_DATE_INVALID);
        }
    }

    private void checkStreet(String street) throws BusinessException {
        if (isStringNull(street)) {
            throw new BusinessException(BLLErrorCode.ITEM_STREET_NULL);
        }
        if (street.length() > 30) {
            throw new BusinessException(BLLErrorCode.ITEM_STREET_TOO_LONG);
        }
    }

    private void checkPostalCode(String postalCode) throws BusinessException {
        if (isStringNull(postalCode)) {
            throw new BusinessException(BLLErrorCode.ITEM_POSTAL_CODE_NULL);
        }
        if (postalCode.length() > 15) {
            throw new BusinessException(BLLErrorCode.ITEM_POSTAL_CODE_TOO_LONG);
        }
    }

    private void checkCity(String city) throws BusinessException {
        if (isStringNull(city)) {
            throw new BusinessException(BLLErrorCode.ITEM_CITY_NULL);
        }
        if (city.length() > 30) {
            throw new BusinessException(BLLErrorCode.ITEM_CITY_TOO_LONG);
        }
    }

    private void checkSeller(User seller) throws BusinessException {
        if (seller == null) {
            throw new BusinessException(BLLErrorCode.ITEM_SELLER_NULL);
        }
    }

    private void checkId(Integer id) throws BusinessException {
        if (id == null) {
            throw new BusinessException(BLLErrorCode.ITEM_ID_NULL);
        }
    }

    private void checkUserId(Integer id) throws BusinessException {
        if (id == null) {
            throw new BusinessException(BLLErrorCode.ITEM_USER_ID_NULL);
        }
    }

    private boolean isStringNull(String s) {
        return ((s == null) || (s.trim().isEmpty()));
    }
}