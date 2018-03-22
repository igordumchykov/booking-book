package com.jdum.booking.book.constants;

/**
 * @author idumchykov
 * @since 2/21/18
 */
public interface REST {

    String BOOKING_CREATE_PATH = "/create";
    String BOOKING_GET_PATH = "/get/{id}";

    String BUS_NUMBER_PARAM = "busNumber";
    String TRIP_DATE_PARAM = "tripDate";
}
