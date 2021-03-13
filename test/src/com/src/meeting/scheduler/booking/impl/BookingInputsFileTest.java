/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED.
 *
 * SPPA-T3000  
 * 
 */

package com.src.meeting.scheduler.booking.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.src.meeting.scheduler.booking.BookingRequest;
import com.src.meeting.scheduler.exception.InvalidProposedMeetingParameterException;

public class BookingInputsFileTest
{
    private BookingInputsFile bookingInputsFile;
    
    @BeforeEach
    public void setUp() throws Exception
    {
    	
    }
    
    @Test
    public void testReadInputsWhenValidMeetingRequestsArePassed() throws Exception
    {
    
    	String inputBookingRequestTxtpath = "./config/meetingRequests.txt";
    	
    	bookingInputsFile = new BookingInputsFile(inputBookingRequestTxtpath);
    
    	List<BookingRequest> bookingRequests = bookingInputsFile.readInputs();
    	
    	assertNotNull(bookingRequests);
    	assertEquals(5 , bookingRequests.size());
    	
    	
    }
    
    
    @Test
    public void testReadInputsWhenInValidMeetingRequestIsPassed() throws Exception
    {

    	String inputBookingRequestTxtpath = "./config/meetingRequests1.txt";

    	bookingInputsFile = new BookingInputsFile(inputBookingRequestTxtpath);

    	Exception exception = Assertions.assertThrows(InvalidProposedMeetingParameterException.class, () -> {
    		bookingInputsFile.readInputs();
    	});

    	assertEquals("Invalid Proposed Meeting Date , Please Select a Meeting Date In Valid Format", exception.getMessage());


    }
    
    
    @Test
    public void testReadInputsWhenBookingRequestInputTextFileNotPresent() throws Exception
    {
    
    	String inputBookingRequestTxtpath = "./config/meetingRequests2.txt";
    	
    	bookingInputsFile = new BookingInputsFile(inputBookingRequestTxtpath);
    	List<BookingRequest> bookingRequests = null;
    	
    	bookingRequests	 = bookingInputsFile.readInputs();
    	assertNotNull(bookingRequests);
    	assertTrue( bookingRequests.size() == 0);
    	
    }
    
    
    @Test
    public void testReadInputsWhenBookingRequestInputPassedIsInvald() throws Exception
    {
    
    	String inputBookingRequestTxtpath = "./config/meetingRequests2.txt";
    	
    	bookingInputsFile = new BookingInputsFile(inputBookingRequestTxtpath);
    	List<BookingRequest> bookingRequests = null;
    	
    	bookingRequests	 = bookingInputsFile.readInputs();
    	assertNotNull(bookingRequests);
    	assertTrue( bookingRequests.size() == 0);
    	
    }
    
    @AfterEach
    public void tearDown() throws Exception
    {
    	bookingInputsFile = null;
    }
    
}


/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED
 *
 * SPPA-T3000
 */
