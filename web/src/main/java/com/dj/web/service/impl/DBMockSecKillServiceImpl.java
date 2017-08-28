package com.dj.web.service.impl;

import com.dj.web.dao.GoodsDao;
import com.dj.web.service.MockSecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daijie on 2017-8-25.
 */
@Service(value = "dBMockSecKillServiceImpl")
public class DBMockSecKillServiceImpl implements MockSecKillService {


    @Autowired
    private GoodsDao goodsDao;

    public boolean buy(int goodsid, int num) {
        if(goodsDao.reduceAmount(goodsid,num) > 0){
            return true;
        }else{
            return false;
        }
    }
}
