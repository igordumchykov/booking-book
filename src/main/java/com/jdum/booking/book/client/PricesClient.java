package com.jdum.booking.book.client;

import com.jdum.booking.common.dto.PriceDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.jdum.booking.book.constants.REST.BUS_NUMBER_PARAM;
import static com.jdum.booking.book.constants.REST.TRIP_DATE_PARAM;

/**
 * @author idumchykov
 * @since 1/26/18
 */
@FeignClient(name = "${client.prices.service}")
public interface PricesClient {

    @RequestMapping(method = RequestMethod.GET, value = "${client.prices.requests.get}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PriceDTO getPrice(@RequestParam(BUS_NUMBER_PARAM) String busNumber, @RequestParam(TRIP_DATE_PARAM) String tripDate);
}
