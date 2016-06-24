package com.example.bmob;

import java.util.Date;

import cn.bmob.v3.BmobObject;


public class Notes extends BmobObject{
	
	private String type;
	private Integer buy;
	private Integer sail;
	private String username;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBuy() {
		return buy;
	}

	public void setBuy(Integer buy) {
		this.buy = buy;
	}

	public Integer getSail() {
		return sail;
	}

	public void setSail(Integer sail) {
		this.sail = sail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
