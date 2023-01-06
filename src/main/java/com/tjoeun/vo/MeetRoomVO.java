package com.tjoeun.vo;

import java.util.Date;

public class MeetRoomVO {
	
	private int roomnum;
	private int deptno;
	private Date setdate;
	private String starttime;
	private String endtime;
	
	public int getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(int roomnum) {
		this.roomnum = roomnum;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public Date getSetdate() {
		return setdate;
	}
	public void setSetdate(Date setdate) {
		this.setdate = setdate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	@Override
	public String toString() {
		return "MeetRoomVO [roomnum=" + roomnum + ", deptno=" + deptno + ", setdate=" + setdate + ", starttime="
				+ starttime + ", endtime=" + endtime + "]";
	}


}
