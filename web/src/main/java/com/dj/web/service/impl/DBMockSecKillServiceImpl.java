package com.dj.web.service.impl;

import com.dj.web.service.MockSecKill;
import org.springframework.stereotype.Service;

/**
 * Created by daijie on 2017-8-25.
 */
@Service
public class DBMockSecKillServiceImpl implements MockSecKill{


    public boolean buy(int goodsid, int num) {
        return false;
    }
}
