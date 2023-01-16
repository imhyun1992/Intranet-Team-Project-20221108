package com.tjoeun.vo;

public class Param {

	private int startNo;
	private int endNo;
	private String category;
	private String searchcategory;
	private String searchobj;
	private String email;
	
	// approval 
	private String approval_search;
	private String approval_status;
	private String userName;
	private int userNo;
	
	public Param() {}
	
	public int getStartNo() {
		return startNo;
	}
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchcategory() {
		return searchcategory;
	}
	public void setSearchcategory(String searchcategory) {
		this.searchcategory = searchcategory;
	}
	public String getSearchobj() {
		return searchobj;
	}
	public void setSearchobj(String searchobj) {
		this.searchobj = searchobj;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getApproval_search() {
		return approval_search;
	}

	public void setApproval_search(String approval_search) {
		this.approval_search = approval_search;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	
}
