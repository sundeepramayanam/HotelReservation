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
import java.util.Map.Entry;
import java.util.Scanner;

public class ReservationRequest {

	/*
	 * This class is used to import the input for the application
	 */

	public static void main(String[] args) throws ParseException {

		
		String fileName = "metropolis_hotels.csv";
		String bookingsfile = "metropolis_bookings.csv";
		String checkIn = "18-02-04";
		String checkOut = "18-02-06";
		File file = new File(fileName);

		/*
		 * Reading the list of hotels file (metropolis_hotels.csv)
		 */
		
		ArrayList<HotelsInfo> HotelsInfoArray = new ArrayList<HotelsInfo>();
		
//		HotelsInfoArray = (ArrayList<HotelsInfo>) populateHotelsList(fileName);
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

		/*
		 * Reading the first CSV file (metropolis_bookings.csv)
		 */
		
		File file1 = new File(bookingsfile);
		ArrayList<Bookings> BookingsArray = new ArrayList<Bookings>();

		try {
			Scanner inputStream1 = new Scanner(file1);
			while (inputStream1.hasNext()) {
				String data = inputStream1.next();
				String[] values = data.split(",");
				DateFormat formatter;
				List<Date> dates = new ArrayList<Date>();
				List<Date> datesToBook = new ArrayList<Date>();
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
                    //String ds = formatter.format(lDate);
                    StringBuilder key = new StringBuilder();
                    for(char c : values[0].toCharArray()){
                    	if(Character.isAlphabetic(c)){
                    		key.append(c);
                    	}
                    }
                    BookingsArray.add(new Bookings(key.toString(),lDate));
                }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		HashMap<String, HashMap<Date, Integer>> resultHashMap = new HashMap<String, HashMap<Date, Integer>>();
		resultHashMap = hotelToNumberOfRooms(HotelsInfoArray, BookingsArray);
		
		List<String> displayHotels = new ArrayList<String>();
		displayHotels = hotelsAvailable(checkIn, checkOut, resultHashMap);
	}
		
		
	
	
	/*
	 * Creating a hash map to store hotels and increment the counter.
	 */
	public static HashMap hotelToNumberOfRooms(ArrayList<HotelsInfo> hotelsInfo , ArrayList<Bookings> bookings) {
		
		ArrayList<HotelsInfo> HotelsInfoArray = hotelsInfo;
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
	
	
//	public static List populateHotelsList(String hotelsFile) {
//		ArrayList<HotelsInfo> HotelsInfoArray = new ArrayList<HotelsInfo>();
//		
//		Scanner inputStream = new Scanner(hotelsFile);
//		while (inputStream.hasNext()) {
//			String data = inputStream.next();
//			String[] values = data.split(",");
//			HotelsInfoArray.add(new HotelsInfo(values[0], Integer.parseInt(values[1])));
//		}
//		inputStream.close();
//		
//		return HotelsInfoArray;
//		
//	}
	
	
	
	public static List hotelsAvailable(String checkIn, String checkOut, HashMap<String, HashMap<Date, Integer>> resultHashMap) {
		List<String> result= new ArrayList<String>();
		List<Date> bookingDates = new ArrayList<Date>();
		bookingDates = getDates(checkIn, checkOut);
		
		
		
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
}