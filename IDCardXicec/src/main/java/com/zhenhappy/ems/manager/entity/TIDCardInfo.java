package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;

import java.awt.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lianghaijian on 2014-04-22.
 */
@Entity
@Table(name = "idcard_info", schema = "dbo")
public class TIDCardInfo {
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
    private String remark;
    private Integer filterflag;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "nation")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Column(name = "born")
    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "cardno")
    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @Column(name = "issuedaddress")
    public String getIssuedaddress() {
        return issuedaddress;
    }

    public void setIssuedaddress(String issuedaddress) {
        this.issuedaddress = issuedaddress;
    }

    @Column(name = "effectdate")
    public String getEffectdate() {
        return effectdate;
    }

    public void setEffectdate(String effectdate) {
        this.effectdate = effectdate;
    }

    @Column(name = "expiredate")
    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    @Column(name = "samid")
    public String getSamid() {
        return samid;
    }

    public void setSamid(String samid) {
        this.samid = samid;
    }

    @Column(name = "picimage")
    public String getPicimage() {
        return picimage;
    }

    public void setPicimage(String picimage) {
        this.picimage = picimage;
    }

    @Column(name = "picimageaddress")
    public String getPicimageaddress() {
        return picimageaddress;
    }

    public void setPicimageaddress(String picimageaddress) {
        this.picimageaddress = picimageaddress;
    }

    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "scannum")
    public Integer getScannum() {
        return scannum;
    }

    public void setScannum(Integer scannum) {
        this.scannum = scannum;
    }

    @Column(name = "piccode")
    public String getPiccode() {
        return piccode;
    }

    public void setPiccode(String piccode) {
        this.piccode = piccode;
    }

    @Column(name = "piclength")
    public String getPiclength() {
        return piclength;
    }

    public void setPiclength(String piclength) {
        this.piclength = piclength;
    }

    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "filterflag")
    public Integer getFilterflag() {
        return filterflag;
    }

    public void setFilterflag(Integer filterflag) {
        this.filterflag = filterflag;
    }
}
