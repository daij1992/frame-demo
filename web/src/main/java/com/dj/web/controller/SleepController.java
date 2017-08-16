package com.dj.web.controller;


import com.dj.web.utils.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by daijie on 2017-8-14.
 */
@RequestMapping("/sleep")
@Controller
public class SleepController {


    private static final Logger LOGGER = LoggerFactory.getLogger(SleepController.class);


    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Wrapper<String> postSleep(@RequestParam(value = "time",required = true) int time) throws InterruptedException {

        LOGGER.info("postSleep thread:{}  sleep  ...",Thread.currentThread().getName());
        Thread.sleep(time);

        LOGGER.info("postSleep thread:{} wake up  ...",Thread.currentThread().getName());

        String jsonStr = "{\"id\":1,\"username\":\"lirongtong023132\",\"passwd\":\"1321dagda1gds1gsa1g132a1056g4ada\",\"email\":\"799099401@qq.com\",\"addr\":\"广东省深圳市\",\"birth\":\"2017-01-05\"}";

        return new Wrapper<String>(jsonStr);
    }



    @ResponseBody
    @RequestMapping(value = "/{time}",method = RequestMethod.GET)
    public Wrapper<String> getSleep(@PathVariable(value = "time") int time) throws InterruptedException {

        LOGGER.info("getSleep thread:{}  sleep  {} ...",Thread.currentThread().getName(),time);
        Thread.sleep(time);

        LOGGER.info("getSleep thread:{} wake up  ...",Thread.currentThread().getName());

        String jsonStr = "{\"id\":1,\"username\":\"lirongtong023132\",\"passwd\":\"1321dagda1gds1gsa1g132a1056g4ada\",\"email\":\"799099401@qq.com\",\"addr\":\"广东省深圳市\",\"birth\":\"2017-01-05\"}";

        return new Wrapper<String>(jsonStr);
    }

}
