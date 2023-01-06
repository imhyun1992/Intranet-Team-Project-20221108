package com.tjoeun.vo;

import java.util.ArrayList;

public class DateData {
	
	private String year;
	private String month;
	private String date;
	private String status;
	private String event;
	private ArrayList<TodoVO> todolist = new ArrayList<TodoVO>();

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public ArrayList<TodoVO> getTodolist() {
		return todolist;
	}

	public void setTodolist(ArrayList<TodoVO> todolist) {
		this.todolist = todolist;
	}

	public DateData() { }

	public DateData(int year, int month, int date, String status, String event) {
		this.year = String.valueOf(year);
		this.month = String.valueOf(month);
		this.date = String.valueOf(date);
		this.status = status;
		this.event = event;
	}

	public DateData(String year, String month, int date, String status, String event) {
		this.year = year;
		this.month = month;
		this.date = String.valueOf(date);
		this.status = status;
		this.event = event;
	}

	@Override
	public String toString() {
		return "DateData [year=" + year + ", month=" + month + ", date=" + date + ", status=" + status + ", event="
				+ event + ", todolist=" + todolist + "]";
	}

}
