/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED.
 *
 * SPPA-T3000  
 * 
 */

package com.src.meeting.scheduler.meeting.ifc;

import java.util.List;
import java.util.Map;

import com.src.meeting.scheduler.booking.BookingRequest;
import com.src.meeting.scheduler.booking.BookingResponse;

public interface MeetingsSchedulerIfc
{
    public Map<String, List<BookingResponse>> book(List<BookingRequest> reqeusts);
}


/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED
 *
 * SPPA-T3000
 */
