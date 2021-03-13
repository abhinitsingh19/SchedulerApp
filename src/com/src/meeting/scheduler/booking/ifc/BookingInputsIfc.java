/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED.
 *
 * SPPA-T3000  
 * 
 */

package com.src.meeting.scheduler.booking.ifc;

import java.util.List;

import com.src.meeting.scheduler.booking.BookingRequest;

public interface BookingInputsIfc
{
    public List<BookingRequest> readInputs();
    public long getStartOfficeTime();
    public long getEndOfficeTime();
}


/*
 * Copyright (c) Siemens AG 2021 ALL RIGHTS RESERVED
 *
 * SPPA-T3000
 */
