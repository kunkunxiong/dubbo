package com.dubbo.entity;

import java.io.Serializable;

public class UserPojo implements Serializable{// 业务操作类
	private static final long serialVersionUID = 1L;
	private User user;
	private String startBirthday;//起始生日
	private String endBirthday;//结束生日
	private Integer order;//id顺序为0,id倒序为1

	public UserPojo(){

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStartBirthday() {
		return startBirthday;
	}

	public void setStartBirthday(String startBirthday) {
		this.startBirthday = startBirthday;
	}

	public String getEndBirthday() {
		return endBirthday;
	}

	public void setEndBirthday(String endBirthday) {
		this.endBirthday = endBirthday;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "UserPojo [user=" + user + ", startBirthday=" + startBirthday + ", endBirthday=" + endBirthday
				+ ", order=" + order + "]";
	}

}
