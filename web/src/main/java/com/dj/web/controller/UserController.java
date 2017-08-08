package com.dj.web.controller;


import com.dj.web.exception.ExpCodeConstant;
import com.dj.web.exception.RuntimeBusinessException;
import com.dj.web.model.User;
import com.dj.web.service.UserService;
import com.dj.web.utils.Wrapper;
import com.dj.web.utils.WrapperUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * Created by daijie on 2017/7/1.
 */
@Controller
@RequestMapping("/v1/users")
public class UserController {



    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Value("${project_id}")
    private Integer projectId;


    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Wrapper<User> addUser(User user){

        validateAddUserParams(user);

        userService.addUser(user);
        user.setUsername("abc12"+new Random().nextInt(10));

        userService.addUser(user);

        return new Wrapper<User>(user);
    }

    /**
     * 校验新增用户参数 -->只判断非空
     * @param user
     */
    public void validateAddUserParams(User user){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(user.getEmail())){
            LOGGER.error("邮箱(email)不能为空");
            builder.append("邮箱(email)不能为空;");
        }
        if(StringUtils.isEmpty(user.getUsername())){
            LOGGER.error("用户名(username)不能为空");
            builder.append("用户名(username)不能为空;");
        }
        if(StringUtils.isEmpty(user.getAddr())){
            LOGGER.error("地址(addr)不能为空");
            builder.append("地址(addr)不能为空;");
        }
        if(user.getBirth() == null){
            LOGGER.error("生日(birth)不能为空");
            builder.append("生日(birth)不能为空;");
        }
        if(StringUtils.isEmpty(user.getPasswd())){
            LOGGER.error("密码(passwd)不能为空");
            builder.append("密码(passwd)不能为空;");
        }

        String error = builder.toString();
        if(!"".equals(error)){
            String code = ExpCodeConstant.M_USER.PARAM_ILLEGAL.getCode();
            String msg = WrapperUtils.getMsg(code,new String[]{error});
            throw new RuntimeBusinessException(code,msg);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Wrapper<User> deleteUser(@PathVariable(value = "id") Integer id){

        User user = userService.deleteUserById(id);


        return new Wrapper<User>(user);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Wrapper<User> queryUserById(@PathVariable(value = "id") Integer id){

        User user = userService.queryUserById(id);

        return new Wrapper<User>(user);
    }


    @ResponseBody
    @RequestMapping(value = "/total",method = RequestMethod.GET)
    public Wrapper<Integer> getUserCount(){
        Integer count = userService.queryUserCount();
        return new Wrapper<Integer>(count);
    }


    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Wrapper<List<User>> getUserByPage(@RequestParam (value = "pageNum",required = true) Integer pageNum,
                                             @RequestParam (value = "pageSize",required = true) Integer pageSize,
                                             @RequestParam (value = "sort",required = false) String sort){

        List<User> list = userService.queryUserByPage(pageNum,pageSize,sort);

        return new Wrapper<List<User>>(list);
    }



}
