package com.hotelavailability;

import java.util.Date;

public class Bookings {

	/*
	 * This class is a bean class for the the request for a reservation.
	 */
	
	private String hotel;
	private Date checkIn;
	private Date checkOut;
	
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Bookings(String hotel, Date checkin) {
		this.hotel = hotel;
		this.checkIn = checkin;
		
	}
}
