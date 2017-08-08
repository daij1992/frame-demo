package com.dj.web.service;


import com.dj.web.model.User;
import java.util.List;

/**
 * Created by daijie on 2017-8-4.
 */
public interface UserService {

    public void addUser(User user);

    public User deleteUserById(Integer id);

    public User queryUserById(Integer id);

    public Integer queryUserCount();

    public List<User> queryUserByPage(Integer pageNum, Integer pageSize, String sort);

}
