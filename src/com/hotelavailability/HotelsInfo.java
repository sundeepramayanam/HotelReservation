package com.hotelavailability;

public class HotelsInfo {

	/*
	 * This Class is a bean that stores the input from CSV file.
	 */
	
	private String hotelName;
	private int numberOfRooms;
	
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	public HotelsInfo(String hotelName, int numberOfRooms) {
		this.hotelName = hotelName;
		this.numberOfRooms = numberOfRooms;
	}
}
