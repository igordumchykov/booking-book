package com.jdum.booking.book.repository;

import com.jdum.booking.book.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jdum.booking.book.utils.TestDataCreator.*;
import static org.junit.Assert.assertEquals;

/**
 * @author idumchykov
 * @since 2/22/18
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"dev"})
public class InventoryRepositoryDevIT {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Before
    public void setUp() throws Exception {
        inventoryRepository.save(new Inventory(BUS_NUMBER, TRIP_DATE, INVENTORY_AVAILABLE));
    }

    @Test
    public void findByBusNumberAndTripDate() throws Exception {
        Inventory inventory = inventoryRepository.findByBusNumberAndTripDate(BUS_NUMBER, TRIP_DATE);

        assertEquals(BUS_NUMBER, inventory.getBusNumber());
        assertEquals(TRIP_DATE, inventory.getTripDate());
        assertEquals(INVENTORY_AVAILABLE, inventory.getAvailable());
    }

}