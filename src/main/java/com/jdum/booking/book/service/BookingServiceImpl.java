package com.jdum.booking.book.service;

import com.jdum.booking.book.client.PricesClient;
import com.jdum.booking.book.jms.Sender;
import com.jdum.booking.book.model.BookingRecord;
import com.jdum.booking.book.model.BookingStatus;
import com.jdum.booking.book.model.Inventory;
import com.jdum.booking.book.model.Passenger;
import com.jdum.booking.book.repository.BookingRepository;
import com.jdum.booking.book.repository.InventoryRepository;
import com.jdum.booking.common.dto.BookingRecordDTO;
import com.jdum.booking.common.dto.PriceDTO;
import com.jdum.booking.common.exceptions.BusinessServiceException;
import com.jdum.booking.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.jdum.booking.book.constants.Constants.*;
import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Autowired
    private MapperFacade bookingRecordMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PricesClient pricesClient;

    @Autowired
    private Sender sender;

    @Override
    public Long book(BookingRecordDTO bookingRecord) throws BusinessServiceException {

        checkPrice(bookingRecord);
        Inventory inventory = getInventory(bookingRecord);

        inventoryRepository.saveAndFlush(inventory);
        log.debug("Inventory was updated");

        Long id = saveBooking(bookingRecord);

        sendBookingEvent(bookingRecord, inventory);

        return id;
    }

    private void sendBookingEvent(BookingRecordDTO bookingRecord, Inventory inventory) {

        Map<String, Object> bookingDetails = prepareMessage(bookingRecord, inventory);
        sender.send(bookingDetails);
        log.debug("booking event successfully delivered: {}", bookingDetails);
    }

    private Map<String, Object> prepareMessage(BookingRecordDTO bookingRecord, Inventory inventory) {

        Map<String, Object> bookingDetails = new HashMap<>();

        bookingDetails.put(BUS_NUMBER_MSG, bookingRecord.getBusNumber());
        bookingDetails.put(TRIP_DATE_MSG, bookingRecord.getTripDate());
        bookingDetails.put(NEW_INVENTORY_MSG, inventory.getBookableInventory());

        return bookingDetails;
    }

    private Long saveBooking(BookingRecordDTO bookingRecordDTO) {

        BookingRecord bookingRecord = bookingRecordMapper.map(bookingRecordDTO, BookingRecord.class);
        bookingRecord.setStatus(BookingStatus.BOOKING_CONFIRMED);
        Set<Passenger> passengers = bookingRecord.getPassengers();
        passengers.forEach(passenger -> passenger.setBookingRecord(bookingRecord));
        bookingRecord.setBookingDate(new Date());
        Long id = bookingRepository.save(bookingRecord).getId();
        log.debug("Booking was saved");

        return id;
    }

    private Inventory getInventory(BookingRecordDTO bookingRecordDTO) throws BusinessServiceException {

        Inventory inventory = inventoryRepository.findByBusNumberAndTripDate(bookingRecordDTO.getBusNumber(), bookingRecordDTO.getTripDate());
        if (inventory == null || !inventory.isAvailable(bookingRecordDTO.getPassengers().size())) {
            throw new BusinessServiceException("No more seats available");
        }
        log.debug("Successfully checked inventory: {}", inventory);

        inventory.setAvailable(inventory.getAvailable() - bookingRecordDTO.getPassengers().size());
        return inventory;
    }

    private void checkPrice(BookingRecordDTO bookingRecord) {

        PriceDTO price = pricesClient.getPrice(bookingRecord.getBusNumber(), bookingRecord.getTripDate());
        log.debug("PriceDTO: {} ", price);

        if (!bookingRecord.getPrice().equals(price.getPriceAmount())) {
            throw new BusinessServiceException("PriceDTO is tampered");
        }
    }

    @Override
    public BookingRecordDTO getBooking(Long id) {

        BookingRecord foundRecord = ofNullable(bookingRepository.findOne(id))
                .orElseThrow(() -> new NotFoundException(String.format("BookingRecord for id %d not found", id)));

        return bookingRecordMapper.map(foundRecord, BookingRecordDTO.class);
    }

    @Transactional
    @Override
    public void updateStatus(BookingStatus status, Long bookingId) throws NotFoundException {

        BookingRecord record = ofNullable(bookingRepository.findOne(bookingId))
                .orElseThrow(() -> new NotFoundException(String.format("BookingRecord for id %d not found", bookingId)));

        record.setStatus(status);
    }
}
