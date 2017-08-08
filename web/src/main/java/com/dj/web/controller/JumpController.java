package com.dj.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by daijie on 2017/7/1.
 */
@RequestMapping("/jump")
@Controller
public class JumpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/forward",method = RequestMethod.GET)
    public String toForward(@RequestParam(value = "relativePath",required = true) String relativePath){
        LOGGER.info("转发路径--->"+relativePath);

        return relativePath;
    }

    @RequestMapping(value = "/redirect",method = RequestMethod.GET)
    public String toRedirect(@RequestParam(value = "absolutePath",required = true) String absolutePath){

        LOGGER.info("重定向路径--->"+absolutePath);
        return "redirect:" +absolutePath;

    }

}
