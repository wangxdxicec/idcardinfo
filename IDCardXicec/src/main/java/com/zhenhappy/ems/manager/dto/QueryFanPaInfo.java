package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wangxd on 2017-03-23.
 */
public class QueryFanPaInfo {
	private Integer id;
	private String cardno;
	private String barcode;
	private Date creatime;

	public QueryFanPaInfo() {
		super();
	}

	public QueryFanPaInfo(Integer id) {
		super();
		this.id = id;
	}

	public QueryFanPaInfo(Integer id, String cardno, String barcode, Date creatime) {
		this.id = id;
		this.cardno = cardno;
		this.barcode = barcode;
		this.creatime = creatime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getCreatime() {
		return creatime;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}
}
