package com.yoyling.domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

//	private Integer id;
//	private String username;
//	private String address;
//	private String sex;
//	private Date birthday;

	private Integer userId;
	private String userName;
	private String userAddress;
	private String userSex;
	private String userBirthday;

	//一对多关系映射：一个用户对应多个账户
	private List<Account> accounts;

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", userAddress='" + userAddress + '\'' +
				", userSex='" + userSex + '\'' +
				", userBirthday='" + userBirthday + '\'' +
				'}';
	}
}
