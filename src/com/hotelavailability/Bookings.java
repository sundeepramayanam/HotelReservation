package com.hotelavailability;

import java.util.Date;

public class Bookings {

	/*
	 * This class is a bean class for the the request for a reservation.
	 */
	
	private String hotel;
	private Date OccupiedDate;
	
	
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	

	public Date getOccupiedDate() {
		return OccupiedDate;
	}
	public void setOccupiedDate(Date occupiedDate) {
		OccupiedDate = occupiedDate;
	}
	public Bookings(String hotel, Date OccupiedDate) {
		this.hotel = hotel;
		this.OccupiedDate = OccupiedDate;
		
	}
}