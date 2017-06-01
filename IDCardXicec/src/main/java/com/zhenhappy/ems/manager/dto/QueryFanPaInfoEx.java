package com.zhenhappy.ems.manager.dto;

/**
 * Created by wangxd on 2017-03-23.
 */
public class QueryFanPaInfoEx {
	private Integer id;
	private String cardno;
	private String barcode;
	private String creatime;

	public QueryFanPaInfoEx() {
		super();
	}

	public QueryFanPaInfoEx(Integer id) {
		super();
		this.id = id;
	}

	public QueryFanPaInfoEx(Integer id, String cardno, String barcode, String creatime) {
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

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}
}
