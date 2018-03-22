package com.jdum.booking.book.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.jdum.booking.book.constants.Constants.CHECK_IN_QUEUE;
import static com.jdum.booking.book.constants.Constants.SEARCH_QUEUE;

/**
 * @author idumchykov
 * @since 2/8/18
 */
@Configuration
public class AppConfig {

    @Bean
    public Queue searchQueue() {
        return new Queue(SEARCH_QUEUE, false);
    }

    @Bean
    public Queue checkinQueue() {
        return new Queue(CHECK_IN_QUEUE, false);
    }
}
