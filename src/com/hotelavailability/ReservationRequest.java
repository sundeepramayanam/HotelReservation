package com.hotelavailability;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ReservationRequest {

	/*
	 * This class is used to import the input for the application
	 */
	
	public static void main(String[] args) throws ParseException {
		
		String fileName = "metropolis_hotels.csv";
		String bookingsfile = "metropolis_bookings.csv";
		
		File file = new File(fileName);
		
		ArrayList<HotelsInfo> HotelsInfoArray= new ArrayList<HotelsInfo>();
		try {
			Scanner inputStream = new Scanner(file);
			while(inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				HotelsInfoArray.add(new HotelsInfo(values[0],Integer.parseInt(values[1])));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		File file1 = new File(bookingsfile);
		ArrayList<Bookings> BookingsArray = new ArrayList<Bookings>();
		
		try {
			Scanner inputStream1 = new Scanner(file1);
			while(inputStream1.hasNext()) {
				String data = inputStream1.next();
				String [] values = data.split(",");
				DateFormat formatter;
                List<Date> dates = new ArrayList<Date>();

                formatter = new SimpleDateFormat("yy-MM-dd");
                Date startDate = null;
                Date endDate = null;

                try {
					startDate = (Date) formatter.parse(values[1]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
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
                    BookingsArray.add(new Bookings(values[0],lDate));
                }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Bookings value : BookingsArray) {
		System.out.println(value.getHotel() + " : " + value.getCheckIn());
		}	
	}

}
		
