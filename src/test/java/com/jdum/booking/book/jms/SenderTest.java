package com.jdum.booking.book.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static com.jdum.booking.book.constants.Constants.SEARCH_QUEUE;
import static com.jdum.booking.book.utils.TestDataCreator.constructInventory;
import static com.jdum.booking.book.utils.TestDataCreator.constructMessage;
import static org.mockito.Mockito.verify;

/**
 * @author idumchykov
 * @since 2/22/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SenderTest {

    @InjectMocks
    private Sender sender;

    @Mock
    private RabbitMessagingTemplate template;

    @Test
    public void send() throws Exception {

        Map<String, Object> message = constructMessage(constructInventory());
        sender.send(message);

        verify(template).convertAndSend(SEARCH_QUEUE, message);
    }

}