package com.tjoeun.vo;

import java.util.Date;

public class TodoVO {
	private int idx;
	private int empno;
	private Date writedate;
	private String content;
	private String status;
	private String shareset;
	private String colorcode;
	private String setdate;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getSetdate() {
		return setdate;
	}
	public void setSetdate(String setdate) {
		this.setdate = setdate;
	}
	public String getShareset() {
		return shareset;
	}
	public void setShareset(String shareset) {
		this.shareset = shareset;
	}
	public String getColorcode() {
		return colorcode;
	}
	public void setColorcode(String colorcode) {
		this.colorcode = colorcode;
	}
	
	@Override
	public String toString() {
		return "TodoVO [idx=" + idx + ", empno=" + empno + ", writedate=" + writedate + ", content=" + content
				+ ", status=" + status + ", shareset=" + shareset + ", colorcode=" + colorcode + ", setdate=" + setdate
				+ "]";
	}
		
}
