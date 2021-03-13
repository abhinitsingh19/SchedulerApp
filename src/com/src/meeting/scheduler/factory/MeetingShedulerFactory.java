package com.src.meeting.scheduler.factory;

import com.src.meeting.scheduler.meeting.impl.MeetingScheduler;

/**
 * It is used to get the object for class MeetingSheduler 
 * @author  Singh,Abhinit
 */
public class MeetingShedulerFactory 
{
    /** Default Constructor */
    private MeetingShedulerFactory()
    {
        
    }
    
    /**
     * return the object for the class MeetingScheduler
     * @param startTime startTime
     * @param endTime endTime
     * @return object for class MeetingScheduler, 
     */
    public static MeetingScheduler createMeetingSheduler(long startTime , long endTime)
    {
    	return new MeetingScheduler(startTime,endTime);
    }
}
