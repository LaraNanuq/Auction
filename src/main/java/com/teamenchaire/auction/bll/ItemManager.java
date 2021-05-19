package com.teamenchaire.auction.bll;

import java.util.List;

import com.teamenchaire.auction.BusinessException;
import com.teamenchaire.auction.bo.Category;
import com.teamenchaire.auction.bo.Item;
import com.teamenchaire.auction.bo.User;
import com.teamenchaire.auction.bo.Withdrawal;
import com.teamenchaire.auction.dal.DAOFactory;
import com.teamenchaire.auction.dal.ItemDAO;

public final class ItemManager {
    private final ItemDAO itemDAO;

    public ItemManager() {
        this.itemDAO = DAOFactory.getItemDAO();
    }

    public void addItem(final Item item) throws BusinessException {
        checkItem(item);
        if (item.getId() != null) {
            throw new BusinessException(BLLErrorCode.ITEM_ALREADY_EXISTS);
        }
        itemDAO.insert(item);
    }

    public List<Item> getItems() throws BusinessException {
        return itemDAO.selectAll();
    }

    private void checkItem(final Item item) throws BusinessException {
        if (item == null) {
            throw new BusinessException(BLLErrorCode.ITEM_NULL);
        }
        if (isStringNull(item.getName())) {
            throw new BusinessException(BLLErrorCode.ITEM_NAME_NULL);
        }
        if (isStringNull(item.getDescription())) {
            throw new BusinessException(BLLErrorCode.ITEM_DESCRIPTION_NULL);
        }
        if (item.getStartDate() == null) {
            throw new BusinessException(BLLErrorCode.ITEM_START_DATE_NULL);
        }
        if (item.getEndDate() == null) {
            throw new BusinessException(BLLErrorCode.ITEM_END_DATE_NULL);
        }
        if (item.getStartDate().isAfter(item.getEndDate().minusDays(1))) {
            throw new BusinessException(BLLErrorCode.ITEM_DATES_INVALID);
        }
        final Integer startingPrice = item.getStartingPrice();
        if ((startingPrice == null) || (startingPrice < 0)) {
            throw new BusinessException(BLLErrorCode.ITEM_STARTING_PRICE_INVALID);
        }
        final Integer sellingPrice = item.getSellingPrice();
        if ((sellingPrice == null) || (sellingPrice < 0) || (sellingPrice < startingPrice)) {
            throw new BusinessException(BLLErrorCode.ITEM_SELLING_PRICE_INVALID);
        }
        final User seller = item.getSeller();
        if ((seller == null) || (seller.getId() == null)) {
            throw new BusinessException(BLLErrorCode.ITEM_SELLER_NULL);
        }
        final Category category = item.getCategory();
        if ((category == null) || (category.getId() == null)) {
            throw new BusinessException(BLLErrorCode.ITEM_CATEGORY_NULL);
        }
        checkWithdrawalPoint(item.getWithdrawalPoint());
    }

    private void checkWithdrawalPoint(final Withdrawal withdrawalPoint) throws BusinessException {
        if (withdrawalPoint == null) {
            throw new BusinessException(BLLErrorCode.WITHDRAWAL_NULL);
        }
        if (isStringNull(withdrawalPoint.getStreet())) {
            throw new BusinessException(BLLErrorCode.WITHDRAWAL_STREET_NULL);
        }
        if (isStringNull(withdrawalPoint.getPostalCode())) {
            throw new BusinessException(BLLErrorCode.WITHDRAWAL_POSTAL_CODE_NULL);
        }
        if (isStringNull(withdrawalPoint.getCity())) {
            throw new BusinessException(BLLErrorCode.WITHDRAWAL_CITY_NULL);
        }
    }

    private boolean isStringNull(final String s) {
        return ((s == null) || (s.trim().isEmpty()));
    }
}