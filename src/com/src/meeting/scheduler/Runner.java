/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED.
 *
 * SPPA-T3000  
 * 
 */

package com.src.meeting.scheduler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.PropertyConfigurator;

import com.src.meeting.scheduler.booking.BookingRequest;
import com.src.meeting.scheduler.booking.BookingResponse;
import com.src.meeting.scheduler.booking.ifc.BookingInputsIfc;
import com.src.meeting.scheduler.booking.impl.BookingInputsFile;
import com.src.meeting.scheduler.factory.MeetingShedulerFactory;
import com.src.meeting.scheduler.meeting.ifc.MeetingsSchedulerIfc;



/**
 * This Class is the Entry Point for Meeting Scheduler Application.
 * It does the following jobs.
 * It reads Input text file and creates a list of Booking Request
 * and Then Pass it on to Meeting Scheduler to process this lists to complete Bookings.
 * @author Singh,Abhinit
 *
 */
public class Runner
{
    public static void main(String[] args)
    {
    	configurelog4j();
    	String inputBookingRequestTxtpath = "./config/meetingRequests.txt";
        
        BookingInputsIfc bookings = new BookingInputsFile(inputBookingRequestTxtpath);
        
        //read the Proposed Meeting Requests from input text File
     
        List<BookingRequest> reqeusts = bookings.readInputs();
        
        MeetingsSchedulerIfc meetingScheduler = MeetingShedulerFactory.createMeetingSheduler(bookings.getStartOfficeTime(),bookings.getEndOfficeTime());
        
        Map<String, List<BookingResponse>> responses = meetingScheduler.book(reqeusts);

        displayBookedMeetings(responses);

    }
    
    private static void configurelog4j()
	{

		String log4jPath = "./config/log4j.properties";
		PropertyConfigurator.configure(log4jPath);

	}

    public static void displayBookedMeetings(Map<String, List<BookingResponse>> bookedMeetingsMap)
    {
        Iterator<Entry<String, List<BookingResponse>>> iterator = bookedMeetingsMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Entry<String, List<BookingResponse>> bookedMeetingSlotEntry = iterator.next();
            System.out.println(bookedMeetingSlotEntry.getKey());
            bookedMeetingSlotEntry.getValue().forEach(System.out::println);
        }
        //bookedMeetingsMap.entrySet().forEach(System.out::println);
        
    }
    
    
}


/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED
 *
 * SPPA-T3000
 */
