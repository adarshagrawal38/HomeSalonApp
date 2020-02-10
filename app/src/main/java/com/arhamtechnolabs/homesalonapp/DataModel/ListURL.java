package com.arhamtechnolabs.homesalonapp.DataModel;

import android.widget.BaseAdapter;

public class ListURL {

    //    public static String BASE_URL = "http://192.168.43.62/home-salon/";
    public static String BASE_URL = "https://www.homesalon.in/admin/api/home-salon/";

    //INSERT API CALLS
    public static String INSERT_FRANCHISE = BASE_URL + "insert_franchise.php";
    public static String TIE_UP_INSERT = BASE_URL + "insert_tie_up.php";
    public static String INSERT_LOGIN_DETAILS = BASE_URL + "insert_login.php";
    public static String EDIT_USER_DETAILS = BASE_URL + "edit_user.php?phone_no=";
    public static String INSERT_BOOKING = BASE_URL + "insert_booking.php";
    public static String INSERT_PROFILE = BASE_URL + "insert_profile_profile.php";
    public static String CHECK_STATUS_PROFILE = BASE_URL + "check_status_profile.php?phone_no=";


    public static final String BOOKING_INSERT_URL = BASE_URL + "insert_booking.php?";
    //VIEW API CALLS
    public static String VIEW_GIFT_VOUCHERS = BASE_URL + "view_gift_voucher.php";
    public static String VIEW_USER_BY_PHONE = BASE_URL + "view_user_by_number.php?phone_no=";
    public static String VIEW_FEEDBACK = BASE_URL + "view-feedback.php";
    public static String VIEW_BANNER = BASE_URL + "view-banner.php";
    public static String VIEW_CATEGORY = BASE_URL + "view_category.php";
    public static String VIEW_SUBCATEGORY = BASE_URL + "view_sub_category.php?id=";
    public static String VIEW_SUB_SUB_CATEGORY = BASE_URL + "view_sub_sub_category.php?id=";
    public static String VIEW_BOOKING_ = BASE_URL + "view_booking.php?phone_no=";
    public static String VIEW_PROFILE_WITH_MOB = BASE_URL + "view_profile.php?phone_no=";
    public static String GET_USER_ID_VIA_MOBILE = BASE_URL  + "user_id_from_phone.php?phone_no=";

    //EditAPI CALls
    public static String VIEW_EDIT_USER = BASE_URL + "edit_user.php?phone_no=";

    public static String UPDATE_WALLET_BALANCE = BASE_URL + "refere_and_earn.php?refer_code=";
    public static String FETCH_ALL_SUB_SUB_CAT = BASE_URL + "view_all_sub_sub_cat.php";


    public static final String INSERT_PAYMENT_URL = BASE_URL + "insert_payment.php?" ;
    public static String PAY_VIA_WALLET = BASE_URL + "update_wallet.php?amount=";

}
