package com.jdum.booking.book.mapper;

import com.jdum.booking.book.model.BookingRecord;
import com.jdum.booking.common.dto.BookingRecordDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * @author idumchykov
 * @since 2/13/18
 */
@Component
public class BookingRecordMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {

        factory.classMap(BookingRecord.class, BookingRecordDTO.class)
                .byDefault()
                .register();
    }
}
