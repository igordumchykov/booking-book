package com.jdum.booking.book.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdum.booking.book.service.BookingService;
import com.jdum.booking.common.dto.BookingRecordDTO;
import com.jdum.booking.common.exceptions.BusinessServiceException;
import com.jdum.booking.common.exceptions.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.jdum.booking.book.constants.REST.BOOKING_CREATE_PATH;
import static com.jdum.booking.book.constants.REST.BOOKING_GET_PATH;
import static com.jdum.booking.book.utils.TestDataCreator.BOOK_ID;
import static com.jdum.booking.book.utils.TestDataCreator.constructBookingDTO;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author idumchykov
 * @since 2/16/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnIdWhenBook() throws Exception {

        when(bookingService.book(any(BookingRecordDTO.class))).thenReturn(BOOK_ID);

        mockMvc.perform(post(BOOKING_CREATE_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(constructBookingDTO())))
                .andExpect(status().isOk())
                .andExpect(content().string(BOOK_ID.toString()));
    }

    @Test
    public void shouldReturn400WhenBook() throws Exception {

        doThrow(BusinessServiceException.class).when(bookingService).book(any(BookingRecordDTO.class));

        mockMvc.perform(post(BOOKING_CREATE_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(constructBookingDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBooking() throws Exception {

        BookingRecordDTO bookingRecordDTO = constructBookingDTO();

        when(bookingService.getBooking(BOOK_ID)).thenReturn(bookingRecordDTO);

        mockMvc.perform(get(BOOKING_GET_PATH, BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingRecordDTO)));
    }

    @Test
    public void shouldReturn404WhenGetBooking() throws Exception {

        doThrow(NotFoundException.class).when(bookingService).getBooking(BOOK_ID);

        mockMvc.perform(get(BOOKING_GET_PATH, BOOK_ID))
                .andExpect(status().isNotFound());
    }
}