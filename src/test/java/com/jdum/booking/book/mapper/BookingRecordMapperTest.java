package com.jdum.booking.book.mapper;

import com.jdum.booking.book.model.BookingRecord;
import com.jdum.booking.common.dto.BookingRecordDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jdum.booking.book.utils.TestDataCreator.constructBooking;
import static com.jdum.booking.book.utils.TestDataCreator.constructBookingDTO;
import static org.junit.Assert.assertEquals;

/**
 * @author idumchykov
 * @since 2/22/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BookingRecordMapperTest {

    @InjectMocks
    private BookingRecordMapper mapper;

    @Test
    public void shouldMapBetweenDomainAndDTO() throws Exception {

        BookingRecord domain = constructBooking();
        BookingRecordDTO dto = constructBookingDTO();

        BookingRecordDTO actualDTO = mapper.map(domain, BookingRecordDTO.class);
        BookingRecord actualDomain = mapper.map(dto, BookingRecord.class);

        assertEquals(domain.getStatus(), actualDomain.getStatus());
        assertEquals(domain.getBusNumber(), actualDomain.getBusNumber());
        assertEquals(domain.getOrigin(), actualDomain.getOrigin());
        assertEquals(domain.getDestination(), actualDomain.getDestination());
        assertEquals(domain.getTripDate(), actualDomain.getTripDate());
        assertEquals(domain.getPrice(), actualDomain.getPrice());
        assertEquals(domain.getPassengers(), actualDomain.getPassengers());

        assertEquals(dto.getStatus(), actualDTO.getStatus());
        assertEquals(dto.getBusNumber(), actualDTO.getBusNumber());
        assertEquals(dto.getOrigin(), actualDTO.getOrigin());
        assertEquals(dto.getDestination(), actualDTO.getDestination());
        assertEquals(dto.getTripDate(), actualDTO.getTripDate());
        assertEquals(dto.getPrice(), actualDTO.getPrice());
        assertEquals(dto.getPassengers(), actualDTO.getPassengers());
    }

}