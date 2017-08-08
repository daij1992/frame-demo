package com.dj.web.dao;


import com.dj.web.model.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by daijie on 2017-8-4.
 */
public interface UserDao {

    public User queryUserById(@Param(value = "id") Integer id);

    public void addUser(User user);

    public void deleteUser(@Param(value = "id") Integer id);

    public User queryUserByUsername(@Param(value = "username") String username);

    public Integer queryUserCount();

    public List<User> queryUserByPage(@Param(value = "offset") Integer offset, @Param(value = "limit") Integer limit, @Param(value = "sort") String sort);
}
