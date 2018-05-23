package com.hotelavailability;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ReservationRequest {

	/**
	 * 
	 * @param args
	 * @throws ParseException
	 */

	public static void main(String[] args) throws ParseException {

		String fileName = "metropolis_hotels.csv";
		String bookingsfile = "metropolis_bookings.csv";
		String checkIn = "18-02-04";
		String checkOut = "18-02-06";
		File file = new File(fileName);

		ArrayList<HotelsInfo> HotelsInfoArray = new ArrayList<HotelsInfo>();

		try {
			Scanner inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				HotelsInfoArray.add(new HotelsInfo(values[0], Integer.parseInt(values[1])));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		File file1 = new File(bookingsfile);
		ArrayList<Bookings> BookingsArray = new ArrayList<Bookings>();

		try {
			Scanner inputStream1 = new Scanner(file1);
			while (inputStream1.hasNext()) {
				String data = inputStream1.next();
				String[] values = data.split(",");
				DateFormat formatter;
				List<Date> dates = new ArrayList<Date>();
				formatter = new SimpleDateFormat("yy-MM-dd");
				Date startDate = null;
				Date endDate = null;

				try {
					startDate = (Date) formatter.parse(values[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				endDate = (Date) formatter.parse(values[2]);

				long interval = 24 * 1000 * 60 * 60;
				long startTime = startDate.getTime();
				long endTime = endDate.getTime();

				while (startTime <= endTime) {
					dates.add(new Date(startTime));
					startTime += interval;
				}
				// end date range function
				for (int i = 0; i < dates.size(); i++) {
					Date lDate = (Date) dates.get(i);
					// String ds = formatter.format(lDate);
					StringBuilder key = new StringBuilder();
					for (char c : values[0].toCharArray()) {
						if (Character.isAlphabetic(c)) {
							key.append(c);
						}
					}
					BookingsArray.add(new Bookings(key.toString(), lDate));
				}
			}
			inputStream1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		HashMap<String, HashMap<Date, Integer>> hotelToNumberOfRooms = new HashMap<String, HashMap<Date, Integer>>();
		hotelToNumberOfRooms = hotelToNumberOfRooms(HotelsInfoArray, BookingsArray);
		System.out.println(hotelToNumberOfRooms);
		

		List<String> displayHotels = hotelsAvailable(checkIn, checkOut, hotelToNumberOfRooms, HotelsInfoArray);

	}

	
	public static HashMap<String, HashMap<Date, Integer>> hotelToNumberOfRooms(ArrayList<HotelsInfo> hotelsInfo, ArrayList<Bookings> bookings) {

		ArrayList<Bookings> BookingsArray = bookings;

		HashMap<String, HashMap<Date, Integer>> hashResult = new HashMap<String, HashMap<Date, Integer>>();
		HashMap<Date, Integer> innerMap = new HashMap<Date, Integer>();

		for (Bookings value : BookingsArray) {
			if (hashResult.containsKey(value.getHotel())) {
				innerMap = hashResult.get(value.getHotel().trim());
				if (innerMap.containsKey(value.getOccupiedDate())) {
					innerMap.put(value.getOccupiedDate(), innerMap.get(value.getOccupiedDate()) + 1);
					hashResult.put(value.getHotel().trim(), innerMap);
				} else {
					innerMap.put(value.getOccupiedDate(), 1);
					hashResult.put(value.getHotel().trim(), innerMap);
				}
			} else {
				innerMap = new HashMap<Date, Integer>();
				innerMap.put(value.getOccupiedDate(), 1);
				hashResult.put(value.getHotel().trim(), innerMap);
			}
		}
		return hashResult;
	}

	public static List<String> hotelsAvailable(String checkIn, String checkOut,
			HashMap<String, HashMap<Date, Integer>> resultHashMap, ArrayList<HotelsInfo> HotelsInfoArray ) {
		
		List<String> result = new ArrayList<String>();
		List<Date> requestedDates = new ArrayList<Date>();
		requestedDates = getDates(checkIn, checkOut);
		
		
		for (HotelsInfo hotelsInfo : HotelsInfoArray) {
			String hotelName = hotelsInfo.getHotelName();
			int noOfRooms = hotelsInfo.getNumberOfRooms();
			if(resultHashMap.containsKey(hotelName)) {
				boolean hotelFlag = false;
				
				HashMap<Date, Integer> valueMap = resultHashMap.get(hotelName);
				
				for (Date date : requestedDates) {
					if (valueMap.containsKey(date)) {
						int numOfRoomsBooked = valueMap.get(date);
						if (numOfRoomsBooked < getTotalRoomsForHotel(hotelName,HotelsInfoArray)) {
							hotelFlag = true;
						} else {
							hotelFlag = false;
						}
					} else {
						hotelFlag = true;
					}
				}
				
				if (hotelFlag) {
					System.out.println(hotelName);
				}
			} else {
				System.out.println(hotelName);
			}

				
		}
		
		
		for (Map.Entry<String, HashMap<java.util.Date, java.lang.Integer>> entry : resultHashMap.entrySet()) {
			
			boolean hotelFlag = false;
			String key = entry.getKey();
			HashMap<java.util.Date, java.lang.Integer> valueMap = entry.getValue();
			
			for (Date date : requestedDates) {
				if (valueMap.containsKey(date)) {
					int numOfRoomsBooked = valueMap.get(date);
					if (numOfRoomsBooked < getTotalRoomsForHotel(key,HotelsInfoArray)) {
						hotelFlag = true;
					} else {
						hotelFlag = false;
					}
				} else {
					hotelFlag = true;
				}
			}
			
			if (hotelFlag) {
				System.out.println(key);
			}
			
			
		}

		
		

		return result;

	}

	public static List<Date> getDates(String checkIn, String checkOut) {
		DateFormat formatter;
		List<Date> dates = new ArrayList<Date>();

		formatter = new SimpleDateFormat("yy-MM-dd");
		Date startDate = null;
		Date endDate = null;

		try {
			startDate = (Date) formatter.parse(checkIn);

			endDate = (Date) formatter.parse(checkOut);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long interval = 24 * 1000 * 60 * 60;
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		while (startTime <= endTime) {
			dates.add(new Date(startTime));
			startTime += interval;
			// return dates;
		}
		return dates;
	}

	public static void printHelpAndExit(boolean doExit) {
		System.out.println("The correct usage is : " + "HotelAvailability --hotels <hotels_file> "
				+ "--bookings <bookings_file> " + "--checkin <check_in_date> " + "--checkout <checkoutdate>");
		System.out.println(
				"<hotels_file> : The CSV file which shows the list of hotels with number of rooms it contains.");
		System.out.println("<bookings_file> : The CSV file which shows the bookings made in the hotels.");
		System.out.println("<check_in_date> : The date of check-in in the format yyyy-MM-dd (Ex: 2015-08-01)");
		System.out.println("<check_out_date> : The date of check-out in the format yyyy-MM-dd (Ex: 2015-08-05)");
		System.out.println("Run command example:");
		System.out.println("HotelAvailability " + "--hotels ./metropolis_hotels.csv "
				+ "--bookings ./metropolis_bookings.csv " + "--checkin 2015-08-01 " + "--checkout 2015-08-05");
		System.out.println();

		if (doExit) {
			System.exit(1);
		}
	}
	
	public static int getTotalRoomsForHotel(String hotelName, ArrayList<HotelsInfo> HotelsInfoArray) {
		int value = 0;
		
		for (HotelsInfo hotelsInfo : HotelsInfoArray) {
			if(hotelsInfo.getHotelName().equals(hotelName)) {
				return hotelsInfo.getNumberOfRooms();
			}
		}
		
		return value;
	}
}