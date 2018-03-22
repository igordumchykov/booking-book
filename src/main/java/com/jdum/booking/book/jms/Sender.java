package com.jdum.booking.book.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jdum.booking.book.constants.Constants.SEARCH_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class Sender {

    @Autowired
    private RabbitMessagingTemplate template;

    public void send(Object message) {
        log.debug("Sending a booking event: {}", message);
        template.convertAndSend(SEARCH_QUEUE, message);
    }
}