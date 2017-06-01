package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.dao.TFanPaInfoDao;
import com.zhenhappy.ems.manager.entity.TFanPaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by angxd on 2017-04-19.
 */
@Service
public class TFanPaInfoServiceImp implements TFanPaInfoService {

    @Autowired
    private TFanPaInfoDao tFanPaInfoDao;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveTFanPainfo(TFanPaInfo tFanPaInfo) {
        if(tFanPaInfo != null){
            tFanPaInfoDao.create(tFanPaInfo);
        }
    }

    @Override
    public void updateTFanPainfo(TFanPaInfo tFanPaInfo) {
        if(tFanPaInfo != null){
            tFanPaInfoDao.update(tFanPaInfo);
        }
    }

    @Override
    public TFanPaInfo loadTFanPaInfoByCardNum(String cardNum) {
        List<TFanPaInfo> tFanPaInfoList = tFanPaInfoDao.queryByHql("from TFanPaInfo where cardno = ? ", new Object[]{ cardNum });
        if(tFanPaInfoList != null && tFanPaInfoList.size()>0){
            return tFanPaInfoList.get(0);
        }
        return null;
    }

    @Override
    public List<TFanPaInfo> loadAllTFanPaInfoList(){
        List<TFanPaInfo> tFanPaInfoList = hibernateTemplate.find("from TFanPaInfo", null);
        if(tFanPaInfoList.size()==0){
            return null;
        }else{
            return tFanPaInfoList;
        }
    }
}
