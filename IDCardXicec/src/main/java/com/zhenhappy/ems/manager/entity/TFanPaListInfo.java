package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2017-04-19.
 */
@Entity
@Table(name = "fanpa_info_data", schema = "dbo")
public class TFanPaListInfo {
    private Integer id;
    private String cardno;
    private String barcode;
    private Date creatime;

    public TFanPaListInfo() {
    }

    public TFanPaListInfo(Integer id, String cardno, String barcode, Date creatime) {
        this.id = id;
        this.cardno = cardno;
        this.barcode = barcode;
        this.creatime = creatime;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "cardno")
    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "creatime")
    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }
}
