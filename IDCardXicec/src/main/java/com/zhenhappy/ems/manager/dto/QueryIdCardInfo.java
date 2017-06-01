package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wangxd on 2017-03-23.
 */
public class QueryIdCardInfo {
	private Integer id;
	private String name;
	private String sex;
	private String nation;
	private String born;
	private String address;
	private String cardno;
	private String issuedaddress;
	private String effectdate;
	private String expiredate;
	private String samid;
	private String picimage;
	private String piccode;
	private String piclength;
	private String picimageaddress;
	private String barcode;
	private Integer scannum;
	private Date createtime;

	public QueryIdCardInfo() {
		super();
	}

	public QueryIdCardInfo(Integer id) {
		super();
		this.id = id;
	}

	public QueryIdCardInfo(Integer id, String name, String sex, String nation, String born, String address,
						   String cardno, String issuedaddress, String effectdate, String expiredate, String barcode,
						   Integer scannum, String picimageaddress) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.nation = nation;
		this.born = born;
		this.address = address;
		this.cardno = cardno;
		this.issuedaddress = issuedaddress;
		this.effectdate = effectdate;
		this.expiredate = expiredate;
		this.barcode = barcode;
		this.picimageaddress = picimageaddress;
		this.scannum = scannum;
	}

	public QueryIdCardInfo(Integer id, String name, String sex, String nation, String born, String address,
						   String cardno, String issuedaddress, String effectdate, String expiredate, String samid,
						   String picimage, String piccode, String piclength, String picimageaddress, String barcode,
						   Integer scannum, Date createtime) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.nation = nation;
		this.born = born;
		this.address = address;
		this.cardno = cardno;
		this.issuedaddress = issuedaddress;
		this.effectdate = effectdate;
		this.expiredate = expiredate;
		this.samid = samid;
		this.picimage = picimage;
		this.piccode = piccode;
		this.piclength = piclength;
		this.picimageaddress = picimageaddress;
		this.barcode = barcode;
		this.scannum = scannum;
		this.createtime = createtime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBorn() {
		return born;
	}

	public void setBorn(String born) {
		this.born = born;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getIssuedaddress() {
		return issuedaddress;
	}

	public void setIssuedaddress(String issuedaddress) {
		this.issuedaddress = issuedaddress;
	}

	public String getEffectdate() {
		return effectdate;
	}

	public void setEffectdate(String effectdate) {
		this.effectdate = effectdate;
	}

	public String getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}

	public String getSamid() {
		return samid;
	}

	public void setSamid(String samid) {
		this.samid = samid;
	}

	public String getPicimage() {
		return picimage;
	}

	public void setPicimage(String picimage) {
		this.picimage = picimage;
	}

	public String getPiccode() {
		return piccode;
	}

	public void setPiccode(String piccode) {
		this.piccode = piccode;
	}

	public String getPiclength() {
		return piclength;
	}

	public void setPiclength(String piclength) {
		this.piclength = piclength;
	}

	public String getPicimageaddress() {
		return picimageaddress;
	}

	public void setPicimageaddress(String picimageaddress) {
		this.picimageaddress = picimageaddress;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getScannum() {
		return scannum;
	}

	public void setScannum(Integer scannum) {
		this.scannum = scannum;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
