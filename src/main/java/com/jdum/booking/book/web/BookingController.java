package com.jdum.booking.book.web;

import com.jdum.booking.book.service.BookingService;
import com.jdum.booking.common.dto.BookingRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.jdum.booking.book.constants.REST.BOOKING_CREATE_PATH;
import static com.jdum.booking.book.constants.REST.BOOKING_GET_PATH;

@RestController
@CrossOrigin
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(BOOKING_CREATE_PATH)
    public Long book(@RequestBody BookingRecordDTO bookingRecord) {

        log.debug("Create booking: {} ", bookingRecord);

        return bookingService.book(bookingRecord);
    }

    @GetMapping(BOOKING_GET_PATH)
    public BookingRecordDTO getBooking(@PathVariable Long id) {


        log.debug("Get booking for id: {} ", id);
        return bookingService.getBooking(id);
    }
}
