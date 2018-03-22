package com.jdum.booking.book.service;

import com.jdum.booking.book.model.BookingStatus;
import com.jdum.booking.common.dto.BookingRecordDTO;
import com.jdum.booking.common.exceptions.BusinessServiceException;
import com.jdum.booking.common.exceptions.NotFoundException;

/**
 * @author idumchykov
 * @since 10/4/17
 */
public interface BookingService {

    Long book(BookingRecordDTO bookingRecord) throws BusinessServiceException;

    BookingRecordDTO getBooking(Long id);

    void updateStatus(BookingStatus status, Long bookingId) throws NotFoundException;
}
