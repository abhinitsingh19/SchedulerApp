package com.src.meeting.scheduler.booking;
public class BookingResponse 
{
	
	private String startTime;
	
	private String endTime;
	
	private String empId;

	public BookingResponse(String startTime, String endTime, String empId) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.empId = empId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return startTime + " " +endTime+" " + empId ;
	}
	

	
	
}
