package com.hotelavailability;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HotelValidator {

	public HotelValidator() {
		// TODO Auto-generated constructor stub
	}
	
	public static void checkInvalidDates(String checkIn, String checkOut) throws InvalidDateException, InvalidDateFormatException {
		DateFormat formatter;
		formatter = new SimpleDateFormat("yy-MM-dd");
		
		try {
			Date checkInDate = formatter.parse(checkIn);
			Date checkOutDate = formatter.parse(checkOut);
			if(checkInDate.after(checkOutDate)) {
				throw new InvalidDateException("CheckIn Date is after CheckOut date");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new InvalidDateFormatException("Date format should be yy-MM-dd");
		}
		
	}

}
