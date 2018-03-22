package com.jdum.booking.book.model;

import com.jdum.booking.common.model.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Accessors(chain = true)
@Entity
@Table(name = "INVENTORY")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity {

    @Column(name = "BUS_NUMBER")
    private String busNumber;

    @Column(name = "TRIP_DATE")
    private String tripDate;

    @Column(name = "AVAILABLE")
    private int available;

    public boolean isAvailable(int count) {
        return ((available - count) > 5);
    }

    public int getBookableInventory() {
        return available - 5;
    }
}
