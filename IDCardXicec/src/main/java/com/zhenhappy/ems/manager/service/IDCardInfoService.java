package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.entity.TIDCardInfo;

import java.util.List;

/**
 * Created by lianghaijian on 2014-04-22.
 */
public interface IDCardInfoService {

    public void saveIDCardinfo(TIDCardInfo tidCardInfo);
    public void updateIDCardinfo(TIDCardInfo tidCardInfo);
    public TIDCardInfo loadIDCardinfoByCardNum(String cardNum);
    public List<TIDCardInfo> loadIDCardinfoByUnFilter();
}
