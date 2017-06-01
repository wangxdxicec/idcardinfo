package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.dao.IDCardInfoDao;
import com.zhenhappy.ems.manager.entity.TIDCardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianghaijian on 2014-04-22.
 */
@Service
public class IDCardInfoServiceImp implements IDCardInfoService {

    @Autowired
    private IDCardInfoDao idCardInfoDao;


    @Override
    public void saveIDCardinfo(TIDCardInfo tidCardInfo) {
        if(tidCardInfo != null){
            idCardInfoDao.create(tidCardInfo);
        }
    }

    @Override
    public void updateIDCardinfo(TIDCardInfo tidCardInfo) {
        if(tidCardInfo != null){
            idCardInfoDao.update(tidCardInfo);
        }
    }

    @Override
    public TIDCardInfo loadIDCardinfoByCardNum(String cardNum) {
        List<TIDCardInfo> tidCardInfoList = idCardInfoDao.queryByHql("from TIDCardInfo where cardno = ? ", new Object[]{ cardNum });
        if(tidCardInfoList != null && tidCardInfoList.size()>0){
            return tidCardInfoList.get(0);
        }
        return null;
    }

    @Override
    public List<TIDCardInfo> loadIDCardinfoByUnFilter() {
        List<TIDCardInfo> tidCardInfoList = idCardInfoDao.queryByHql("from TIDCardInfo where filterflag !=1 or filterflag is null ", new Object[]{ });
        return tidCardInfoList;
    }
}
