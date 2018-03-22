package com.jdum.booking.book.utils;

import com.jdum.booking.book.model.*;
import com.jdum.booking.common.dto.BookingRecordDTO;
import com.jdum.booking.common.dto.PassengerDTO;
import com.jdum.booking.common.dto.PriceDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Sets.newHashSet;
import static com.jdum.booking.book.constants.Constants.*;
import static java.util.Optional.ofNullable;

/**
 * @author idumchykov
 * @since 2/22/18
 */
public class TestDataCreator {

    public static String FIRST_NAME = "TestName";
    public static String LAST_NAME = "TestSurname";
    public static String GENDER = Gender.MALE.name();
    public static String BUS_NUMBER = "BH100";
    public static String ORIGIN = "SFO";
    public static String DESTINATION = "NYC";
    public static String PRICE_AMOUNT = "100";
    public static String TAMPERED_PRICE_AMOUNT = "99";
    public static String TRIP_DATE = "22-Jan-16";
    public static int INVENTORY_AVAILABLE = 100;
    public static Long BOOK_ID = 1L;
    public static BookingStatus BOOKING_STATUS = BookingStatus.BOOKING_CONFIRMED;

    public static Map<String, Object> constructMessage(Inventory inventory) {
        Map<String, Object> bookingDetails = new HashMap<>();
        bookingDetails.put(BUS_NUMBER_MSG, BUS_NUMBER);
        bookingDetails.put(TRIP_DATE_MSG, TRIP_DATE);
        bookingDetails.put(NEW_INVENTORY_MSG, inventory.getBookableInventory());
        return bookingDetails;
    }

    public static BookingRecordDTO constructBookingDTO() {

        BookingRecordDTO bookingRecordDTO = new BookingRecordDTO()
                .setBusNumber(BUS_NUMBER)
                .setTripDate(TRIP_DATE)
                .setOrigin(ORIGIN)
                .setDestination(DESTINATION)
                .setBookingDate(new Date())
                .setPrice(PRICE_AMOUNT);

        PassengerDTO passenger = constructPassengerDTO(bookingRecordDTO);
        bookingRecordDTO.setPassengers(newHashSet(passenger));

        return bookingRecordDTO;
    }

    public static PassengerDTO constructPassengerDTO(BookingRecordDTO bookingRecordDTO) {

        PassengerDTO passenger = new PassengerDTO()
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setGender(GENDER);

        ofNullable(bookingRecordDTO).ifPresent(booking -> passenger.setBookingRecord(bookingRecordDTO));

        return passenger;
    }

    public static BookingRecord constructBooking() {

        BookingRecord bookingRecord = new BookingRecord()
                .setTripDate(TRIP_DATE)
                .setBusNumber(BUS_NUMBER)
                .setOrigin(ORIGIN)
                .setDestination(DESTINATION)
                .setBookingDate(new Date())
                .setPrice(PRICE_AMOUNT);

        Passenger passenger = constructPassenger(bookingRecord);
        bookingRecord.setPassengers(newHashSet(passenger));

        return bookingRecord;
    }

    public static Passenger constructPassenger(BookingRecord bookingRecord) {

        Passenger passenger = new Passenger()
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setGender(Gender.MALE);

        ofNullable(bookingRecord).ifPresent(booking -> passenger.setBookingRecord(bookingRecord));

        return passenger;
    }

    public static PriceDTO constructPriceDTO() {
        return PriceDTO.builder()
                .busNumber(BUS_NUMBER)
                .priceAmount(PRICE_AMOUNT)
                .tripDate(TRIP_DATE)
                .build();
    }

    public static Inventory constructInventory() {
        return new Inventory()
                .setAvailable(INVENTORY_AVAILABLE)
                .setBusNumber(BUS_NUMBER)
                .setTripDate(TRIP_DATE);
    }
}
