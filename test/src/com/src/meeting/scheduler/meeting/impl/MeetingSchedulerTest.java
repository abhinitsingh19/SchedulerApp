/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED.
 *
 * SPPA-T3000  
 * 
 */

package com.src.meeting.scheduler.meeting.impl;



import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.src.meeting.scheduler.booking.BookingRequest;
import com.src.meeting.scheduler.booking.BookingResponse;
import com.src.meeting.scheduler.booking.impl.BookingInputsFile;
import com.src.meeting.scheduler.factory.MeetingShedulerFactory;

public class MeetingSchedulerTest
{
    /**Class Under Test*/
	private MeetingScheduler meetingScheduler ;
	/**Mock For BookingInputsFile*/
	@Mock
	private BookingInputsFile bookingInputsFile;
	
    @BeforeEach
    public void setUp() throws Exception
    {
    	
    	bookingInputsFile =Mockito.mock(BookingInputsFile.class);
    	
    	Mockito.when(bookingInputsFile.getStartOfficeTime()).thenReturn(900L);
    	Mockito.when(bookingInputsFile.getEndOfficeTime()).thenReturn(1730L);
    	
    	meetingScheduler = MeetingShedulerFactory.createMeetingSheduler(bookingInputsFile.getStartOfficeTime(), bookingInputsFile.getEndOfficeTime());
    	 
    }
    
    private List<BookingRequest> createValidBookingRequestsONSameDayForSameSlots() 
    {

    	//Booking creation time  of Two Different Users
    	Date bookingCreatingDate =  new Date(1300337226000L);
    	Date bookingCreatingDate1 =  new Date(1300259096000L);
    	
    	//Attempt to make a booking on same Date at Same Time
    	Date proposedMeetingDate =  new Date(1300678200000L);
    	
    	
    	List<BookingRequest> requests = Arrays.asList(new BookingRequest("Emp1", bookingCreatingDate, 2l,proposedMeetingDate)
    												,new BookingRequest("Emp2", bookingCreatingDate1, 2l,proposedMeetingDate));
    	return requests;
    }
    
    private List<BookingRequest> createValidBookingRequestsONSameDayForDifferentSlots() 
    {

    	//Booking creation time  of Two Different Users
    	Date bookingCreatingDate =  new Date(1300337226000L);
    	Date bookingCreatingDate1 =  new Date(1300259096000L);
    	
    	//Attempt to make a booking on same Date at Same Time
    	Date proposedMeetingDate =  new Date(1300678200000L);
    	Date proposedMeetingDate1 =  new Date(1300685400000L);
    	
    	
    	List<BookingRequest> requests = Arrays.asList(new BookingRequest("Emp1", bookingCreatingDate, 2l,proposedMeetingDate)
    												,new BookingRequest("Emp2", bookingCreatingDate1, 2l,proposedMeetingDate1));
    	return requests;
    }
    
    private List<BookingRequest> createInvalidBookingRequests() 
    {
    
    	//Booking creation time  of Two Different Users
    	Date bookingCreatingDate =  new Date(1300337226000L);
    	Date bookingCreatingDate1 =  new Date(1300259096000L);
    	
    	//Attempt to make a booking outside office Working Hours
    	Date proposedMeetingDate =  new Date(1300674600000L);
    	Date proposedMeetingDate1 =  new Date(1300798800000L);
    	
    	
    	List<BookingRequest> requests = Arrays.asList(new BookingRequest("Emp1", bookingCreatingDate, 2l,proposedMeetingDate)
    												,new BookingRequest("Emp2", bookingCreatingDate1, 2l,proposedMeetingDate1));
    	return requests;
    }

	@Test
    public void testBookingRequestsIfSameTimeSlotIsRequestedOnSameDay() throws Exception
    {
		List<BookingRequest> validBoookingRequests = createValidBookingRequestsONSameDayForSameSlots();
    
    	meetingScheduler.book(validBoookingRequests);
    	
    	Map<String, List<BookingResponse>> allowedBookings = meetingScheduler.getAllowedBookings();
		Assertions.assertNotNull(allowedBookings, () -> "Map of bookings Grouped by dates should not be null");
		Assertions.assertTrue(allowedBookings.size() == 1);
    	allowedBookings.forEach((k,v) -> Assertions.assertTrue(!v.isEmpty() && v.size() == 1));
    	
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getEndOfficeTime();
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getStartOfficeTime();
    	
    }
	
	
	@Test
    public void testBookingRequestsIfDifferentTimeSlotIsRequestedOnSameDay() throws Exception
    {

		List<BookingRequest> validBoookingRequests = createValidBookingRequestsONSameDayForDifferentSlots();
    
    	meetingScheduler.book(validBoookingRequests);
    	
    	Map<String, List<BookingResponse>> allowedBookings = meetingScheduler.getAllowedBookings();
		Assertions.assertNotNull(allowedBookings, () -> "Map of bookings Grouped by dates should not be null");
		Assertions.assertTrue(allowedBookings.size() == 1);
    	allowedBookings.forEach((k,v) -> Assertions.assertTrue(!v.isEmpty() && v.size() == 2));
    	
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getEndOfficeTime();
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getStartOfficeTime();
    	
    }
	
	@Test
    public void testBookingRequestsIfProposedMeetingStartAndEndTimesAreOutsideBusinessWorkingHours() throws Exception
    {
		List<BookingRequest> invalidMockBookingRequests = createInvalidBookingRequests();
    
    	meetingScheduler.book(invalidMockBookingRequests);
    	
    	Map<String, List<BookingResponse>> allowedBookings = meetingScheduler.getAllowedBookings();
    	
    	Assertions.assertNotNull(allowedBookings);
    	allowedBookings.forEach((k,v) -> Assertions.assertTrue(v.isEmpty()));
		
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getEndOfficeTime();
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getStartOfficeTime();
    	
    }
	
	private List<BookingRequest> createValidBookingRequestsONSameDayForSameSlots1() 
	{

		//Booking creation time  of Two Different Users
		Date bookingCreatingDate =  new Date(1300337226000L);
		Date bookingCreatingDate1 =  new Date(1300259096000L);

		//Attempt to make a booking on same Date at Same Time
		Date proposedMeetingDate =  new Date(4342534364647477575L);


		List<BookingRequest> requests = Arrays.asList(new BookingRequest("Emp1", bookingCreatingDate, 2l,proposedMeetingDate)
				,new BookingRequest("Emp2", bookingCreatingDate1, 2l,proposedMeetingDate));
		return requests;
	}
	
	@Test
    public void testBookingRequestsIfMeetingRequetsAreNotInIntendedFormat() throws Exception
    {
		List<BookingRequest> validBoookingRequests = createValidBookingRequestsONSameDayForSameSlots1();
    
    	meetingScheduler.book(validBoookingRequests);
    	
    	Map<String, List<BookingResponse>> allowedBookings = meetingScheduler.getAllowedBookings();
		Assertions.assertNotNull(allowedBookings, () -> "Map of bookings Grouped by dates should not be null");
		Assertions.assertTrue(allowedBookings.size() == 1);
    	allowedBookings.forEach((k,v) -> Assertions.assertTrue(v.isEmpty() && v.size() == 0));
    	
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getEndOfficeTime();
    	Mockito.verify(bookingInputsFile, Mockito.times(1)).getStartOfficeTime();
    	
    }
    
    @AfterEach
    public void tearDown() throws Exception 
    {
    	bookingInputsFile = null;
    	meetingScheduler = null;
    }
    
}


/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED
 *
 * SPPA-T3000
 */
