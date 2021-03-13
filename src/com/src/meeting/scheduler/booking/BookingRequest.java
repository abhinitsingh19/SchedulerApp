package com.src.meeting.scheduler.booking;
import java.util.Date;

public class BookingRequest 
{

	private String empId;
	private Date bookingCreationTime;
	private long duration;
	private Date meetingRequestedDate;

	public BookingRequest(String empId, Date bookingCreationTime, long duration,
			Date meetingRequestedDate)
	{
		super();
		this.empId = empId;
		this.bookingCreationTime = bookingCreationTime;
		this.duration = duration;
		this.meetingRequestedDate = meetingRequestedDate;
	}
	public BookingRequest() 
	{
		// TODO Auto-generated constructor stub
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Date getBookingCreationTime() {
		return bookingCreationTime;
	}
	public void setBookingCreationTime(Date bookingCreationTime) {
		this.bookingCreationTime = bookingCreationTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public Date getMeetingRequestedDate() {
		return meetingRequestedDate;
	}
	public void setMeetingRequestedDate(Date meetingRequestedDate) {
		this.meetingRequestedDate = meetingRequestedDate;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingCreationTime == null) ? 0 : bookingCreationTime.hashCode());
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((meetingRequestedDate == null) ? 0 : meetingRequestedDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingRequest other = (BookingRequest) obj;
		if (bookingCreationTime == null) {
			if (other.bookingCreationTime != null)
				return false;
		} else if (!bookingCreationTime.equals(other.bookingCreationTime))
			return false;
		if (duration != other.duration)
			return false;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		if (meetingRequestedDate == null) {
			if (other.meetingRequestedDate != null)
				return false;
		} else if (!meetingRequestedDate.equals(other.meetingRequestedDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BookingRequest [empId=" + empId + ", bookingCreationTime=" + bookingCreationTime + ", duration="
				+ duration + ", meetingRequestedDate=" + meetingRequestedDate + "]";
	}


}
