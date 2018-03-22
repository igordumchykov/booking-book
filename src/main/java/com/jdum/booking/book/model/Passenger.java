package com.jdum.booking.book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jdum.booking.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Entity
@Table
@Data
@ToString(exclude = "bookingRecord", callSuper = true)
@EqualsAndHashCode(exclude = "bookingRecord", callSuper = true)
public class Passenger extends BaseEntity {

    private String firstName;
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private BookingRecord bookingRecord;
}
