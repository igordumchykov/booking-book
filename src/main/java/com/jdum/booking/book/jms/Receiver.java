package com.jdum.booking.book.jms;

import com.jdum.booking.book.model.BookingStatus;
import com.jdum.booking.book.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jdum.booking.book.constants.Constants.CHECK_IN_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class Receiver {

    @Autowired
    private BookingService bookingService;

    @RabbitListener(queues = CHECK_IN_QUEUE)
    public void processMessage(Long bookingID) {
        log.debug("Booking id received: {}", bookingID);
        bookingService.updateStatus(BookingStatus.CHECKED_IN, bookingID);
    }

}