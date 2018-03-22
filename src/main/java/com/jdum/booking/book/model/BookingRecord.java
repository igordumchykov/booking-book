package com.jdum.booking.book.model;

import com.jdum.booking.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Accessors(chain = true)
@Entity
@Table
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookingRecord extends BaseEntity {

    private String busNumber;
    private String origin;
    private String destination;
    private String tripDate;
    private Date bookingDate;
    private String price;

    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bookingRecord")
    private Set<Passenger> passengers;
}
