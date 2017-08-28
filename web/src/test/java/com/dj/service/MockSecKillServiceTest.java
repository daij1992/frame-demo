package com.dj.service;

import com.dj.web.dao.GoodsDao;
import com.dj.web.service.MockSecKillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by daijie on 2017-8-28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class MockSecKillServiceTest {

    private static  final Logger LOGGER = LoggerFactory.getLogger(MockSecKillServiceTest.class);

    //根据实际情况注入 redis 和 db
   // @Resource(name = "redisMockSecKillServiceImpl")
    @Resource(name = "dBMockSecKillServiceImpl")
    private  MockSecKillService mockSecKillService;

    @Autowired
    private   GoodsDao goodsDao;

    //商品id
    private  static  final  int goodsid = 1;

    //库存
    private static final  int amount = 100;

    //单用户购买数量
    private static final int singleBuyNum = 3;

    //并发用户
    private static  final  int buyer = 300;


    public static CountDownLatch latch=new CountDownLatch(buyer);


    @Before
    public  void setUp(){
        //设置库存大小
        goodsDao.setAmount(goodsid,amount);
        LOGGER.info("设置库存success! goodsid:{}    amount:{}",goodsid,amount);
    }

    @Test
    public void SecKill() throws IOException {

        for(int i = 0 ; i < buyer ;i++){
            Thread request=  new Thread(new MockSecKillRequest(mockSecKillService));
            request.start();
            latch.countDown();
        }

        System.in.read();


    }


    //模拟秒杀请求
    class MockSecKillRequest implements  Runnable{

        private  MockSecKillService mockSecKillService;

        public MockSecKillRequest(MockSecKillService mockSecKillService) {
            this.mockSecKillService = mockSecKillService;

        }

        public void run() {
            System.out.println("init buyer "+Thread.currentThread().getName());
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             if(true == mockSecKillService.buy(goodsid,singleBuyNum)){
                 System.out.println("购买成功  "+Thread.currentThread().getName()+" goodsid:"+goodsid + " amount:"+singleBuyNum);
             }else{
                 System.out.println("购买失败  "+Thread.currentThread().getName()+" goodsid:"+goodsid + " amount:"+singleBuyNum);
             };
        }
    }


}
