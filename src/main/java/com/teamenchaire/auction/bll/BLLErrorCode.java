package com.teamenchaire.auction.bll;

import com.teamenchaire.auction.BusinessErrorCode;

/**
 * An {@code enum} list of error codes for the business logic layer.
 * 
 * @author Marin Taverniers
 */
public enum BLLErrorCode implements BusinessErrorCode {

    /* User */
    USER_NICKNAME_NULL(20101),
    USER_NICKNAME_INVALID(20102),
    USER_NICKNAME_TOO_LONG(20103),
    USER_NICKNAME_ALREADY_EXISTS(20104),
    USER_LAST_NAME_NULL(20105),
    USER_LAST_NAME_TOO_LONG(20106),
    USER_FIRST_NAME_NULL(20107),
    USER_FIRST_NAME_TOO_LONG(20108),
    USER_EMAIL_NULL(20109),
    USER_EMAIL_TOO_LONG(20110),
    USER_EMAIL_ALREADY_EXISTS(20111),
    USER_PHONE_NUMBER_TOO_LONG(20112),
    USER_STREET_NULL(20113),
    USER_STREET_TOO_LONG(20114),
    USER_POSTAL_CODE_NULL(20115),
    USER_POSTAL_CODE_TOO_LONG(20116),
    USER_CITY_NULL(20117),
    USER_CITY_TOO_LONG(20118),
    USER_PASSWORD_NULL(20119),
    USER_PASSWORD_TOO_LONG(20120),
    USER_NULL(20121),
    USER_ID_NULL(20122),
    USER_USER_NAME_NULL(20123),
    USER_UNKNOWN(20124),
    USER_PASSWORD_INVALID(20125),

    /* Item */
    ITEM_NAME_NULL(20201),
    ITEM_NAME_TOO_LONG(20202),
    ITEM_DESCRIPTION_NULL(20203),
    ITEM_DESCRIPTION_TOO_LONG(20204),
    ITEM_CATEGORY_NULL(20205),
    ITEM_STARTING_PRICE_NULL(20206),
    ITEM_STARTING_PRICE_INVALID(20207),
    ITEM_START_DATE_NULL(20208),
    ITEM_START_DATE_INVALID(20209),
    ITEM_END_DATE_NULL(20210),
    ITEM_END_DATE_INVALID(20211),
    ITEM_STREET_NULL(20212),
    ITEM_STREET_TOO_LONG(20213),
    ITEM_POSTAL_CODE_NULL(20214),
    ITEM_POSTAL_CODE_TOO_LONG(20215),
    ITEM_CITY_NULL(20216),
    ITEM_CITY_TOO_LONG(20217),
    ITEM_SELLER_NULL(20218),
    ITEM_ID_NULL(20219),
    ITEM_USER_ID_NULL(20220),

    /* Category */
    CATEGORY_ID_NULL(20301);
    
    private int code;
    private String name;

    private BLLErrorCode(int code) {
        this.code = code;
        this.name = this.name();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}