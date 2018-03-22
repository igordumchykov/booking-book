package com.jdum.booking.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jdum.booking.book.model.BookingRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingRecord, Long> {
	
}
