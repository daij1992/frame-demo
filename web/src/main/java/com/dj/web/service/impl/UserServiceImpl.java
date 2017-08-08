package com.dj.web.service.impl;


import com.dj.web.dao.UserDao;
import com.dj.web.model.User;
import com.dj.web.service.UserService;
import com.dj.web.utils.WrapperUtils;
import com.dj.web.exception.ExpCodeConstant;
import com.dj.web.exception.RuntimeBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daijie on 2017-8-4.
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        //用户名不能重复
        User validateUser = userDao.queryUserByUsername(user.getUsername());
        if(validateUser != null){
            String code = ExpCodeConstant.M_USER.PARAM_ILLEGAL.getCode();
            String msg = WrapperUtils.getMsg(code,new String[]{"username: "+user.getUsername()+" 重复"});
            throw  new RuntimeBusinessException(code,msg);
        }
        userDao.addUser(user);
    }

    public User deleteUserById(Integer id) {
        //用户是否存在
        User user = userDao.queryUserById(id);
        if(user == null){
            String code = ExpCodeConstant.M_USER.USER_DELETE_FORBIDDEN.getCode();
            String msg = WrapperUtils.getMsg(code,new String[]{"id为"+id+"用户不存在"});
            throw  new RuntimeBusinessException(code,msg);
        }

        userDao.deleteUser(id);

        return user;
    }

    public User queryUserById(Integer id) {

        User user = userDao.queryUserById(id);

        return user;
    }

    public Integer queryUserCount() {
        return userDao.queryUserCount();
    }

    public List<User> queryUserByPage(Integer pageNum, Integer pageSize, String sort) {

        Integer offset = (pageNum -1 )*pageSize;

        return userDao.queryUserByPage(offset,pageSize,sort);
    }
}
