package com.ecommerce.backend.constant;

public class Constant {

    public static final Integer USER_KIND_ADMIN = 1;
    public static final Integer USER_KIND_MANAGER = 2;
    public static final Integer USER_KIND_CUSTOMER = 5;

    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;


    public static final String MEDIA_RESOURCE_TYPE_IMAGE = "image";

    public static final Integer MEDIA_RESOURCE_KIND_IMAGE = 1;

    public static final Integer CATEGORY_LEVEL_1 = 1;

    public static final Integer CATEGORY_STATUS_DELETE = -2;

    public static final Integer PRICING_STRATEGY_STATE_NOT_APPLY = 0;
    public static final Integer PRICING_STRATEGY_STATE_APPLY = 1;

    public static final Integer NATION_KIND_PROVINCE = 1;
    public static final Integer NATION_KIND_DISTRICT = 2;
    public static final Integer NATION_KIND_WARD = 3;

    public static final Integer ADDRESS_NOT_DEFAULT = 0;
    public static final Integer ADDRESS_DEFAULT = 1;

    public static final Integer ORDER_STATE_CANCEL = -100;
    public static final Integer ORDER_STATE_WAIT_CONFIRM = 0;
    public static final Integer ORDER_STATE_CONFIRMED = 100;
    public static final Integer ORDER_STATE_DELIVERING = 200;
    public static final Integer ORDER_STATE_DELIVERED = 300;

    public static final Integer REVIEW_STATE_PENDING = 0;
    public static final Integer REVIEW_STATE_ACTIVE = 1;
}
