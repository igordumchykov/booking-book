package com.jdum.booking.book.jms;

import com.jdum.booking.book.model.BookingStatus;
import com.jdum.booking.book.service.BookingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jdum.booking.book.utils.TestDataCreator.BOOK_ID;
import static org.mockito.Mockito.verify;

/**
 * @author idumchykov
 * @since 2/22/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ReceiverTest {

    @InjectMocks
    private Receiver receiver;

    @Mock
    private BookingService bookingService;

    @Test
    public void shouldProcessMessage() throws Exception {

        receiver.processMessage(BOOK_ID);

        verify(bookingService).updateStatus(BookingStatus.CHECKED_IN, BOOK_ID);
    }

}