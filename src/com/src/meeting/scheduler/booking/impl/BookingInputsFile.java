/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED.
 *
 * SPPA-T3000  
 * 
 */

package com.src.meeting.scheduler.booking.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.src.meeting.scheduler.booking.BookingRequest;
import com.src.meeting.scheduler.booking.ifc.BookingInputsIfc;
import com.src.meeting.scheduler.util.MeetingShedulerUtil;

/**
 * This Class is responsible for reading a batch of meeting requests and store them locally for further processing.
 * Future Scope - This Path from which Booking Information is read is kept flexible to allow for reading from various sources
 * @author Singh,Abhinit
 *
 */
public class BookingInputsFile implements BookingInputsIfc
{
    
	  // /** Log4J */
    private static Logger logger = Logger.getLogger(BookingInputsFile.class);
    
    long  startOfficeTime;
    long  endOfficeTime;
    String inputBookingRequestTxtpath;
    
  
	public BookingInputsFile()
    {
    }
    
    public BookingInputsFile(String customFilePath)
    {
    	inputBookingRequestTxtpath = customFilePath;
    }
    
    @Override
    public List<BookingRequest> readInputs()
    {
    	print("readInputs Enter");
        List<BookingRequest> meetings =  new ArrayList<>();
        try (Scanner input = new Scanner(new File(inputBookingRequestTxtpath)))
        {
            while (input.hasNextLine())
            {
                
                BookingRequest bookingRequest = new BookingRequest();
                
                String bookingRequestsStrLine = input.nextLine();
                
                String[] bookingRequestsStrLineSplittedArr = bookingRequestsStrLine.split(" ");
                
                if (bookingRequestsStrLineSplittedArr.length == 2)
                {
                	try
                	{
                		startOfficeTime = Integer.parseInt(bookingRequestsStrLineSplittedArr[0]);

                	}
                	catch(NumberFormatException ex)
                	{
                		startOfficeTime = 900;
                		logger.warn("Configured Office Working Hours are Invalid ,Falling back to default value for startTime");
                	}
                	
                	try
                	{
                		endOfficeTime = Integer.parseInt(bookingRequestsStrLineSplittedArr[1]);

                	}
                	catch(NumberFormatException ex)
                	{
                		endOfficeTime = 1730;
                		logger.warn("Configured Office Working Hours are Invalid ,Falling back to default value for endTime");
                	}
                   
                	continue;
                }
                if (bookingRequestsStrLineSplittedArr.length > 2)
                {
                    
                    String bookingcreationDateStr = bookingRequestsStrLineSplittedArr[0] + " " + bookingRequestsStrLineSplittedArr[1];
                    
                    Date bookingCreationDate = MeetingShedulerUtil.convertStringIntoStandardDateTimeFormat(bookingcreationDateStr);
                    
                    bookingRequest.setBookingCreationTime(bookingCreationDate);
                    bookingRequest.setEmpId(bookingRequestsStrLineSplittedArr[2]);
                    // move cursor to next line to set other booking details.
                    String nextBoookingStrLine = input.nextLine();
                    String[] split2 = nextBoookingStrLine.split(" ");
                    
                    String meetingRequestDateStr = split2[0] + " " + split2[1];
                    Date meetingRequestDate = MeetingShedulerUtil.convertStringIntoStandardDateTimeFormat(meetingRequestDateStr);
                    
                    bookingRequest.setMeetingRequestedDate(meetingRequestDate);
                    bookingRequest.setDuration(Integer.parseInt(split2[2]));
                    
                    meetings.add(bookingRequest);
                    
                }
                
            }
        }
        
        
//        catch (InvalidProposedMeetingParameterException ex)
//        {
//        	logger.warn("Invalid Meeting Request parameters Passed "+ ex);
//        }
        
        catch (FileNotFoundException  ex)
        {
           logger.warn("Booking Requests Text File  Not Fond At Given Path "+ ex);
        }
        
        print("readInputs Leave");
        return meetings;
    }
    
    @Override
    public long getStartOfficeTime()
    {
        // TODO Auto-generated method stub
        return startOfficeTime;
    }
    
    @Override
    public long getEndOfficeTime()
    {
        // TODO Auto-generated method stub
        return endOfficeTime;
    }
    
    public String getInputBookingRequestTxtpath() 
    {
  		return inputBookingRequestTxtpath;
  	}

  	public void setInputBookingRequestTxtpath(String inputBookingRequestTxtpath)
  	{
  		this.inputBookingRequestTxtpath = inputBookingRequestTxtpath;
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

/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED
 *
 * SPPA-T3000
 */
