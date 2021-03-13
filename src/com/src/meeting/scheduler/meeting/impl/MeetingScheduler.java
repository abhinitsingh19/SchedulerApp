package com.src.meeting.scheduler.meeting.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.src.meeting.scheduler.booking.BookingRequest;
import com.src.meeting.scheduler.booking.BookingResponse;
import com.src.meeting.scheduler.exception.InvalidProposedMeetingParameterException;
import com.src.meeting.scheduler.meeting.ifc.MeetingsSchedulerIfc;
import com.src.meeting.scheduler.util.MeetingShedulerUtil;

/**
 * This class is responsible for processing a batch of Meeting room Booking Requests
 * 
 * @author Singh,Abhinit
 *
 */
public class MeetingScheduler implements MeetingsSchedulerIfc
{
    // /** Log4J */
    private static Logger logger = Logger.getLogger(MeetingScheduler.class);
    
    private HashMap<String, List<BookingResponse>> bookedMeetingsMap;
    
    /** An Integer indicating office starting hour */
    private long ofcStartWorkingHour;
    /** An Integer indicating office closing hour */
    private long ofcEndWorkingHour;
    
    public MeetingScheduler(long ofcStartWorkingHour, long ofcEndWorkingHour)
    {
        this.ofcStartWorkingHour = ofcStartWorkingHour;
        this.ofcEndWorkingHour = ofcEndWorkingHour;
        bookedMeetingsMap = new HashMap<>();
    }
    
    /**
     * @param reqeusts
     * 
     */
    private HashMap<String, List<BookingResponse>>  processMeetingRequest(List<BookingRequest> reqeusts)
    {

    	print("processMeetingRequest Enter reqeusts " + reqeusts);
    	
    	reqeusts.forEach(bookingRequest -> {
    		String empId = bookingRequest.getEmpId();

    		//Extract Proposed Meeting date from Boooking Request
    		Date meetingRequestedDate = bookingRequest.getMeetingRequestedDate();

    		//Format Proposed Meeting date to yyyy-mm-dd format to store it in Bookings map
    		String meetingRequestDayStr = MeetingShedulerUtil.convertDateToStringInStandardDateFormat(meetingRequestedDate);

    		Date meetingStartTimeByDate = null;
    		Date meetingEndTimeByDate = null;
    		//Format Proposed Meeting date to hh-mm format to compare it against office hours(Start/end) and also to compare against any existing meeting hours on very same day.
    		String meetingStartTimeStr = MeetingShedulerUtil.convertDateToStringHhMmFormat(bookingRequest.getMeetingRequestedDate());
    		try 
    		{
    			//Parse the Proposed Meeting date in string  in hh-mm format to add hours of duration to calculate proposed meeting end time.
    			meetingStartTimeByDate = MeetingShedulerUtil.convertStringToDateInHhMmFormat(meetingStartTimeStr);
    			meetingEndTimeByDate = new Date(meetingStartTimeByDate.getTime() + bookingRequest.getDuration() * MeetingShedulerUtil.HOUR);

    			//Format Proposed  Meeting End date to hh-mm format 
    			String meetingEndTimeStr = MeetingShedulerUtil.convertDateToStringHhMmFormat(meetingEndTimeByDate);

    			String[] meetingStartTimeStrArr = meetingStartTimeStr.split(":");
    			meetingStartTimeStr = meetingStartTimeStrArr[0] + meetingStartTimeStrArr[1];

    			String[] meetingEndTimeStrArr = meetingEndTimeStr.split(":");
    			meetingEndTimeStr = meetingEndTimeStrArr[0] + meetingEndTimeStrArr[1];

    			if (bookedMeetingsMap.containsKey(meetingRequestDayStr))
    			{
    				List<BookingResponse> bookings = bookedMeetingsMap.get(meetingRequestDayStr);

    				if (bookings != null)
    				{
    					// compare the start time and endtime of new Booking request against old
    					// should not exceed 17:30 and should not be lower than 09:00
    					// also if booking is already made in between requested start and end time
    					// it should not be allowed
    					int	meetingStartTimeInt = Integer.parseInt(meetingStartTimeStr);
    					int	meetingEndTimeInt = Integer.parseInt(meetingEndTimeStr);
    					boolean isAlreadyBooked = isRequestedTimeSlotAlreadyBooked(meetingStartTimeInt, meetingEndTimeInt, bookings);

    					if ((meetingStartTimeInt >= ofcStartWorkingHour) && (meetingEndTimeInt <= ofcEndWorkingHour) && !isAlreadyBooked)
    					{
    						BookingResponse bookingResponse = new BookingResponse(MeetingShedulerUtil.convertDateToStringHhMmFormat(meetingStartTimeByDate),
    								MeetingShedulerUtil.convertDateToStringHhMmFormat(meetingEndTimeByDate), empId);
    						bookings.add(bookingResponse);
    					}

    				}
    				bookedMeetingsMap.put(meetingRequestDayStr, bookings);

    			}
    			else
    			{
    				List<BookingResponse> bookings = new ArrayList<>();

    				if ((Integer.parseInt(meetingStartTimeStr) >= ofcStartWorkingHour) && (Integer.parseInt(meetingEndTimeStr) <= ofcEndWorkingHour))
    				{
    					BookingResponse bookingResponse = new BookingResponse(MeetingShedulerUtil.convertDateToStringHhMmFormat(meetingStartTimeByDate),
    							MeetingShedulerUtil.convertDateToStringHhMmFormat(meetingEndTimeByDate), empId);
    					bookings.add(bookingResponse);
    				}

    				bookedMeetingsMap.put(meetingRequestDayStr, bookings);
    			}
    		}
    		catch (InvalidProposedMeetingParameterException | NumberFormatException e) 
    		{
    			logger.warn("Invalid format of Requested Booking Date Passed" + e.getMessage());
    		}

    	});

    	print("processMeetingRequest Leave bookedMeetingsMap " + bookedMeetingsMap);
    	return bookedMeetingsMap;
    }
    
    
    /**
     * @param meetingStartTimeInt
     * @param meetingEndTimeInt
     * @param bookings
     * @return
     */
    private boolean isRequestedTimeSlotAlreadyBooked(int meetingStartTimeInt, int meetingEndTimeInt ,
        List<BookingResponse> bookings)
    {
        print("isRequestedTimeSlotAlreadyBooked Enter meetingStartTimeInt "+meetingStartTimeInt + ","
        		+ " " + meetingEndTimeInt + " , " +" bookings "+ bookings);
        boolean isAlreadyBooked = false;
        for (BookingResponse bookedSlot : bookings)
        {
            String[] meetingStartTimeStrArr = bookedSlot.getStartTime().split(":");
            String startBookedTimeStr = meetingStartTimeStrArr[0] + meetingStartTimeStrArr[1];
            
            String[] meetingEndTimeStrArr = bookedSlot.getEndTime().split(":");
            String endBookedTimeStr = meetingEndTimeStrArr[0] + meetingEndTimeStrArr[1];
          
            int startBookedTime = 0;
			int endBookedTime = 0;
            try
            {
            	 startBookedTime = Integer.parseInt(startBookedTimeStr);
            	 endBookedTime = Integer.parseInt(endBookedTimeStr);
            }
            catch(NumberFormatException ex)
            {
            	throw new InvalidProposedMeetingParameterException("Invalid Proposed Meeting Date", ex);
            }
            
           
			if ((meetingStartTimeInt >= startBookedTime && meetingStartTimeInt < endBookedTime)
                || (meetingEndTimeInt > startBookedTime && meetingEndTimeInt <= endBookedTime))
            {
                isAlreadyBooked = true;
                break;
            }
        }
        
        print("isRequestedTimeSlotAlreadyBooked Leave isAlreadyBooked "+isAlreadyBooked);
        
        return isAlreadyBooked;
        
    }
    
    @Override
    public Map<String, List<BookingResponse>> book(List<BookingRequest> requests)
    {
    	print("book Enter requests "+requests);
        requests.sort((BookingRequest request1, BookingRequest request2) -> request1
            .getBookingCreationTime().compareTo(request2.getBookingCreationTime()));
        processMeetingRequest(requests);
        
        print("book Leave  bookedMeetingsMap "+bookedMeetingsMap);
        return bookedMeetingsMap;
    }
    
    public Map<String, List<BookingResponse>> getAllowedBookings()
    {
    	return bookedMeetingsMap;
    }
    
     /**
     * Print debug logs.
     * @param message - message
     */
     private void print(String message)
     {
    	 if (logger.isDebugEnabled())
    	 {
    		 logger.debug(message);
    	 }
     }
    
}
