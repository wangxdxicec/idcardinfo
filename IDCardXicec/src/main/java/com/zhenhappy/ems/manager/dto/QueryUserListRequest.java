package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.EasyuiRequest;

/**
 * Created by wangxd on 2017-03-29.
 */
public class QueryUserListRequest extends EasyuiRequest {
	private Integer id;
	private String userName;
	private Integer userSex;
	private String userNation;
	private String userBorn;
	private String userAddress;
	private String userCardNo;
	private String userIssueAddress;
	private String userEffecToExpiretDate;
	private String userBarCode;
	private Integer pageIndex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getUserNation() {
		return userNation;
	}

	public void setUserNation(String userNation) {
		this.userNation = userNation;
	}

	public String getUserBorn() {
		return userBorn;
	}

	public void setUserBorn(String userBorn) {
		this.userBorn = userBorn;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserIssueAddress() {
		return userIssueAddress;
	}

	public void setUserIssueAddress(String userIssueAddress) {
		this.userIssueAddress = userIssueAddress;
	}

	public String getUserEffecToExpiretDate() {
		return userEffecToExpiretDate;
	}

	public void setUserEffecToExpiretDate(String userEffecToExpiretDate) {
		this.userEffecToExpiretDate = userEffecToExpiretDate;
	}

	public String getUserBarCode() {
		return userBarCode;
	}

	public void setUserBarCode(String userBarCode) {
		this.userBarCode = userBarCode;
	}

	public String getUserCardNo() {
		return userCardNo;
	}

	public void setUserCardNo(String userCardNo) {
		this.userCardNo = userCardNo;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
}
