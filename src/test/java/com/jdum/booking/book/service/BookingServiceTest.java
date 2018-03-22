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
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static com.jdum.booking.book.utils.TestDataCreator.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author idumchykov
 * @since 2/22/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private MapperFacade bookingRecordMapper;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private PricesClient pricesClient;

    @Mock
    private Sender sender;

    @Captor
    private ArgumentCaptor<Inventory> inventoryCaptor;

    @Captor
    private ArgumentCaptor<Map<String, Object>> bookingDetailsCaptor;

    @Captor
    private ArgumentCaptor<BookingRecord> bookingRecordCaptor;

    @Test
    public void shouldGetBookingById() throws Exception {

        BookingRecord bookingRecord = constructBooking();
        BookingRecordDTO expected = constructBookingDTO();

        when(bookingRepository.findOne(BOOK_ID)).thenReturn(bookingRecord);
        when(bookingRecordMapper.map(bookingRecord, BookingRecordDTO.class)).thenReturn(expected);

        BookingRecordDTO actual = bookingService.getBooking(BOOK_ID);

        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundWhenGetById() throws Exception {

        when(bookingRepository.findOne(BOOK_ID)).thenReturn(null);

        bookingService.getBooking(BOOK_ID);
    }

    @Test
    public void shouldUpdateStatus() throws Exception {

        BookingRecord bookingRecord = constructBooking();
        assertNull(bookingRecord.getStatus());

        when(bookingRepository.findOne(BOOK_ID)).thenReturn(bookingRecord);

        bookingService.updateStatus(BOOKING_STATUS, BOOK_ID);

        assertEquals(bookingRecord.getStatus(), BOOKING_STATUS);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundWhenUpdateStatus() throws Exception {

        when(bookingRepository.findOne(BOOK_ID)).thenReturn(null);

        bookingService.updateStatus(BOOKING_STATUS, BOOK_ID);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldRejectTamperedPrice() throws Exception {

        BookingRecordDTO input = constructBookingDTO();
        input.setPrice(TAMPERED_PRICE_AMOUNT);

        PriceDTO priceDTO = constructPriceDTO();

        when(pricesClient.getPrice(BUS_NUMBER, TRIP_DATE)).thenReturn(priceDTO);

        bookingService.book(input);
    }

    @Test(expected = BusinessServiceException.class)
    public void shouldRejectIfInventoryAlreadyEmpty() throws Exception {

        BookingRecordDTO input = constructBookingDTO();
        PriceDTO priceDTO = constructPriceDTO();

        when(pricesClient.getPrice(BUS_NUMBER, TRIP_DATE)).thenReturn(priceDTO);
        when(inventoryRepository.findByBusNumberAndTripDate(BUS_NUMBER, TRIP_DATE)).thenReturn(null);

        bookingService.book(input);
    }

    @Test
    public void shouldBook() throws Exception {

        BookingRecordDTO input = constructBookingDTO();
        BookingRecord bookingRecord = constructBooking();
        PriceDTO priceDTO = constructPriceDTO();
        Inventory inventory = constructInventory();

        assertNull(input.getStatus());
        assertNull(bookingRecord.getStatus());

        when(pricesClient.getPrice(BUS_NUMBER, TRIP_DATE)).thenReturn(priceDTO);
        when(inventoryRepository.findByBusNumberAndTripDate(BUS_NUMBER, TRIP_DATE)).thenReturn(inventory);
        when(inventoryRepository.saveAndFlush(inventoryCaptor.capture())).then(answer -> null);
        when(bookingRecordMapper.map(input, BookingRecord.class)).thenReturn(bookingRecord);

        bookingRecord.setId(BOOK_ID);
        when(bookingRepository.save(bookingRecordCaptor.capture())).thenReturn(bookingRecord);

        doNothing().when(sender).send(bookingDetailsCaptor.capture());

        Long actualBookingId = bookingService.book(input);

        //check return value
        assertEquals(BOOK_ID, actualBookingId);

        //check updated inventory
        assertEquals(inventory.getAvailable(), inventoryCaptor.getValue().getAvailable());

        //check saved booking record
        BookingRecord savedBookingRecord = bookingRecordCaptor.getValue();
        assertEquals(BookingStatus.BOOKING_CONFIRMED, savedBookingRecord.getStatus());
        for (Passenger passenger : savedBookingRecord.getPassengers()) {
            assertEquals(savedBookingRecord, passenger.getBookingRecord());
        }

        //check sent message
        Map<String, Object> expectedMessage = constructMessage(inventory);
        assertEquals(expectedMessage, bookingDetailsCaptor.getValue());
    }

}