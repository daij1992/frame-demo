package com.dj.web.service.impl;

import com.dj.web.service.MockSecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * Created by daijie on 2017-8-25.
 */
@Service(value = "redisMockSecKillServiceImpl")
public class RedisMockSecKillServiceImpl implements MockSecKillService {


   // @Autowired
    private JedisCluster jedisCluster;

    public boolean buy(int goodsid, int num) {


        return false;
    }
}
