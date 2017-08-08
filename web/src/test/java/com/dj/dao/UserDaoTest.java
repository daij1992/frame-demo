package com.dj.dao;


import com.dj.web.dao.UserDao;
import com.dj.web.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by daijie on 2017/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;


    @Test
    public void queryUserById(){
       User user = userDao.queryUserById(1);
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setBirth(new Date());
        user.setUsername("daijie");
        user.setEmail("daijie1@konka.com");
        user.setPasswd("12345");
        user.setAddr("四川眉山");

        userDao.addUser(user);
    }


    @Test
    public void  deleteUser(){
        userDao.deleteUser(1);
    }


    @Test
    public void queryUserByPage(){
        Integer offset = 0;
        Integer limit = 10;
        String sort = "create_time DESC";
        List<User> list = userDao.queryUserByPage(offset,limit,sort);
    }

    @Test
    public void queryUserCount(){
        Integer count = userDao.queryUserCount();
    }


    @Test
    public void test(){
        String username = "daijie";
        User user = userDao.queryUserByUsername(username);
    }
}
