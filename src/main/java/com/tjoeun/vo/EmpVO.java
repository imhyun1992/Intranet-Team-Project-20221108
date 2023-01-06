package com.tjoeun.vo;

import java.util.Date;
public class EmpVO {
	
	private int empno;
	private String name;
	private String password;
	private String position;
	private int deptno;
	private String incnum;
	private String pernum;
	private String email;
	private Date hiredate;
	private String gender;
	private String permission;
	
	public EmpVO() {}
	
	public EmpVO(int empno, String name, String password, String pernum, String email, String gender) {
		super();
		this.empno = empno;
		this.name = name;
		this.password = password;
		this.pernum = pernum;
		this.email = email;
		this.gender = gender;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getIncnum() {
		return incnum;
	}

	public void setIncnum(String incnum) {
		this.incnum = incnum;
	}

	public String getPernum() {
		return pernum;
	}

	public void setPernum(String pernum) {
		this.pernum = pernum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "EmpVO [empno=" + empno + ", name=" + name + ", password=" + password + ", position=" + position
				+ ", deptno=" + deptno + ", incnum=" + incnum + ", pernum=" + pernum + ", email=" + email
				+ ", hiredate=" + hiredate + ", gender=" + gender + ", permission=" + permission + "]";
	}

}
