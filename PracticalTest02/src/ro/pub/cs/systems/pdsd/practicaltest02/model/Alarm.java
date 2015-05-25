package ro.pub.cs.systems.pdsd.practicaltest02.model;

import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;

public class Alarm {

	private String hour;
	private String minute;

	public Alarm() {
		this.hour = null;
		this.minute   = null;
	}

	public Alarm(
			String hour,
			String minute) {
		this.hour = hour;
		this.minute   = minute;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getHour() {
		return hour;
	}
	
	public void setMinute(String minute) {
		this.minute = minute;
	}
	
	public String getMinute() {
		return minute;
	}
	
	@Override
	public String toString() {
		return "Hour" + ": " + hour + "\n\r" + 
	          "Minute" + ": " + minute + "\n\r";
	}

}
