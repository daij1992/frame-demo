package com.dj.web.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by daijie on 2017-8-28.
 */
public interface GoodsDao {

    public  int  reduceAmount(@Param(value = "goodsid") int goodsid,@Param(value = "num") int num);

    public void setAmount(@Param(value = "goodsid") int goodsid,@Param(value = "num") int num);


}
