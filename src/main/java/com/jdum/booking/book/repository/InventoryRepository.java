package com.jdum.booking.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jdum.booking.book.model.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Inventory findByBusNumberAndTripDate(String busNumber, String tripDate);
	
}
