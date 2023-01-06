package com.tjoeun.vo;

import java.sql.Date;

public class ReportVO {
	private int idx;
	private String type; 
	private Date writedate;
	private String title;
	private String name;
	private String content;
	private String attachedfile;
	private String realfilename;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttachedfile() {
		return attachedfile;
	}
	public void setAttachedfile(String attachedfile) {
		this.attachedfile = attachedfile;
	}
	public String getRealfilename() {
		return realfilename;
	}
	public void setRealfilename(String realfilename) {
		this.realfilename = realfilename;
	}
	
	@Override
	public String toString() {
		return "ReportVO [idx=" + idx + ", type=" + type + ", writedate=" + writedate + ", title=" + title + ", name="
				+ name + ", content=" + content + ", attachedfile=" + attachedfile + ", realfilename=" + realfilename
				+ "]";
	}
	

}
