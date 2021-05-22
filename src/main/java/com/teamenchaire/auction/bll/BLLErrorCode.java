package com.teamenchaire.auction.bll;

/**
 * A {@code class} which defines error codes for the business logic layer.
 * 
 * @author Marin Taverniers
 */
public final class BLLErrorCode {

    private BLLErrorCode() {
    }

    //public static final int ITEM_NULL = 20101;
    //public static final int ITEM_ALREADY_EXISTS = 20102;

    public static final int ITEM_SELLER_NULL = 20102;
    public static final int ITEM_NAME_NULL = 20103;
    public static final int ITEM_NAME_TOO_LONG = 20104;
    public static final int ITEM_DESCRIPTION_NULL = 20105;
    public static final int ITEM_DESCRIPTION_TOO_LONG = 20106;
    public static final int ITEM_CATEGORY_NULL = 20107;
    public static final int ITEM_STARTING_PRICE_INVALID = 20108;
    public static final int ITEM_START_DATE_NULL = 20109;
    public static final int ITEM_START_DATE_INVALID = 20110;
    public static final int ITEM_END_DATE_NULL = 20111;
    public static final int ITEM_DATES_INVALID = 20112;
    public static final int ITEM_WITHDRAWAL_NULL = 20113;
    public static final int ITEM_WITHDRAWAL_STREET_NULL = 20114;
    public static final int ITEM_WITHDRAWAL_STREET_TOO_LONG = 20115;
    public static final int ITEM_WITHDRAWAL_POSTAL_CODE_NULL = 20116;
    public static final int ITEM_WITHDRAWAL_POSTAL_CODE_TOO_LONG = 20117;
    public static final int ITEM_WITHDRAWAL_CITY_NULL = 20118;
    public static final int ITEM_WITHDRAWAL_CITY_TOO_LONG = 20119;

    public static final int CATEGORY_ID_NULL = 20201;

    public static final int USER_ID_NULL = 20301;
}