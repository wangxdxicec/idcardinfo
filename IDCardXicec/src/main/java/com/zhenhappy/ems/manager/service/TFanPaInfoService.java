package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.entity.TFanPaInfo;
import com.zhenhappy.ems.manager.entity.TIDCardInfo;

import java.util.List;

/**
 * Created by wangxd on 2017-04-19.
 */
public interface TFanPaInfoService {

    public void saveTFanPainfo(TFanPaInfo tFanPaInfo);
    public void updateTFanPainfo(TFanPaInfo tFanPaInfo);
    public TFanPaInfo loadTFanPaInfoByCardNum(String cardNum);
    public List<TFanPaInfo> loadAllTFanPaInfoList();

}
