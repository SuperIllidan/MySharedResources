package com.hidy.hdoa6.weeklyDiet.beans;

public class WeeklyDiet {
	private Breakfast breakfast;
	private Lunch lunch;
	private Dinner dinner;
	private Additional additional;
	private String dateStart;
	private String dateEnd;
	private String datePoint;
	public WeeklyDiet() {
		super();
	}
	public Breakfast getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(Breakfast breakfast) {
		this.breakfast = breakfast;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDatePoint() {
		return datePoint;
	}
	public void setDatePoint(String datePoint) {
		this.datePoint = datePoint;
	}
	public Lunch getLunch() {
		return lunch;
	}
	public void setLunch(Lunch lunch) {
		this.lunch = lunch;
	}
	public Dinner getDinner() {
		return dinner;
	}
	public void setDinner(Dinner dinner) {
		this.dinner = dinner;
	}
	public Additional getAdditional() {
		return additional;
	}
	public void setAdditional(Additional additional) {
		this.additional = additional;
	}
}
