package com.src.meeting.scheduler.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.src.meeting.scheduler.exception.InvalidProposedMeetingParameterException;

public class MeetingShedulerUtil
{
	public static SimpleDateFormat standardFormatterInDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat standardFormatterInDateTimeExclSecs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat standardFormatterInDate = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat standardFormatterInTime = new SimpleDateFormat("HH:mm");
	public  static final long HOUR = 3600 * 1000;
	
	public static String convertDateToStringHhMmFormat(Date meetingRequestedDate) 
	{
		return standardFormatterInTime.format(meetingRequestedDate);
		
	}

	public static Date convertStringToDateInHhMmFormat(String meetingStartTimeStr) throws InvalidProposedMeetingParameterException 
	{
		Date date = null;
		try 
		{
			date =  standardFormatterInTime.parse(meetingStartTimeStr);
		} 
		catch (ParseException e) 
		{
			throw new InvalidProposedMeetingParameterException("Invalid Proposed Meeting Date , Please Select a Meeting Date In Valid Formate",e);
		}
		return date;
	}

	public static String convertDateToStringInStandardDateFormat(Date meetingRequestedDate) 
	{
		return standardFormatterInDate.format(meetingRequestedDate);
	}
	
	public static Date convertStringToDateInStandardDateFormat(String meetingStartTimeStr) throws InvalidProposedMeetingParameterException 
	{
		Date date = null;
		try 
		{
			date =  standardFormatterInDate.parse(meetingStartTimeStr);
		} 
		catch (ParseException e) 
		{
			throw new InvalidProposedMeetingParameterException("Invalid Proposed Meeting Date , Please Select a Meeting Date In Valid Format",e);
		}
		return date;
	}

	public static Date convertStringIntoStandardDateTimeFormat(String bookingcreationDateStr) throws InvalidProposedMeetingParameterException
	{
		Date date = null;
		try 
		{
			date =  standardFormatterInDateTimeExclSecs.parse(bookingcreationDateStr);
		} 
		catch (ParseException e) 
		{
			throw new InvalidProposedMeetingParameterException("Invalid Proposed Meeting Date , Please Select a Meeting Date In Valid Format",e);
		}
		return date;
	}

	
	
	
	
	

	
	
}
