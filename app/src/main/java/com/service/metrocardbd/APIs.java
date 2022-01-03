package com.service.metrocardbd;

public class APIs {
    //private static final String DOMAIN = "http://192.168.43.229:8000/api/";
    private static final String DOMAIN = "http://metrobangla.herokuapp.com/api/";
    private static final String URL_REGISTER_COMPLAINT = DOMAIN + "add_new_complaint";
    private static final String URL_GET_TRAIN_LINE = DOMAIN + "retrieve_train_line";
    private static final String URL_GET_STATIONS = DOMAIN + "retrieve_station";
    private static final String URL_GET_FARE_AND_ROUTES = DOMAIN + "retrieve_routes";
    private static final String URL_CHECK_LOGIN = DOMAIN + "login";
    private static final String URL_GET_CARD_BALANCE = DOMAIN + "retrieve_balance";
    private static final String URL_RECHARGE_BALANCE = DOMAIN + "recharge_balance";
    private static final String URL_RETRIEVE_RECHARGE_HISTORY = DOMAIN + "retrieve_recharge_history";
    private static  final String URL_CHANGE_PIN=DOMAIN+"change_pin";
    private static  final String URL_CARD_USER_PHONE=DOMAIN+"forgot_password_getPhone";
    private  static  final String URL_UPDATE_CARD_PIN=DOMAIN+"update_card_pin";
    private static  final String URl_RETRIEV_TRAVEL_HISTORY=DOMAIN+"retrieve_travel_history";


    public static String getUrlRegisterComplaint() {
        return URL_REGISTER_COMPLAINT;
    }

    public static String getUrlGetStations() {
        return URL_GET_STATIONS;
    }

    public static String getUrlGetFareAndRoutes() {
        return URL_GET_FARE_AND_ROUTES;
    }

    public static String getUrlCheckLogin() {
        return URL_CHECK_LOGIN;
    }

    public static String getUrlGetCardBalance() {
        return URL_GET_CARD_BALANCE;
    }

    public static String getUrlRechargeBalance() {
        return URL_RECHARGE_BALANCE;
    }

    public static String getUrlGetTrainLine() {
        return URL_GET_TRAIN_LINE;
    }

    public static String getUrlRetrieveRechargeHistory() { return URL_RETRIEVE_RECHARGE_HISTORY; }

    public static String getUrlChangePin() { return URL_CHANGE_PIN; }

    public static String getUrlCardUserPhone() { return URL_CARD_USER_PHONE; }

    public static String getUrlUpdateCardPin() { return URL_UPDATE_CARD_PIN; }

    public static String getURl_RETRIEV_TRAVEL_HISTORY() { return URl_RETRIEV_TRAVEL_HISTORY; }
}

