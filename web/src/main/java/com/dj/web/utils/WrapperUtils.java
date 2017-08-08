package com.dj.web.utils;

import com.dj.common.utils.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daijie on 2017-8-4.
 */
public class WrapperUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(WrapperUtils.class);


    /**
     *
     * @Title: getMsg
     * @Description: 无args时获取ret_msg,无需替换翻译
     * @param retCode
     * @return String
     * @throws
     */
    public static String getMsg(String retCode) {

        String originMsg = PropertiesUtil.getExceptionProperty(retCode);

        return originMsg;
    }


    /**
     *
     * @Title: getMsg
     * @Description: 有参数args时获取ret_msg,需替换翻译
     * @param retCode
     * @param agrs
     * @return String
     * @throws
     */
    public static String getMsg(String retCode, String... agrs) {
        //用于返回
        String retMsg = "";

        // 1. 通过异常码从配置文件exception.properties获取原始异常String originMsg,进行翻译替换{}
        String originMsg = PropertiesUtil.getExceptionProperty(retCode);

        // 2. 根据 originMsg 和 args 构建返回异常信息 retMsg
        if (agrs == null) {

            LOGGER.info("动态替换{}内容 agrs 为空");

            retMsg = java.text.MessageFormat.format(originMsg, agrs);

        } else {
            if (agrs.length < (originMsg.split("\\{").length - 1)) {

                // agrs个数 小于 originMsg{}个数
                LOGGER.info("动态替换{}内容 agrs个数 小于 originMsg中{*}个数");

                retMsg = java.text.MessageFormat.format(originMsg, agrs);

            } else if (agrs.length > (originMsg.split("\\{").length - 1)) {

                // agrs个数 大于 originMsg{}个数
                LOGGER.info("动态替换{}内容 agrs个数 大于 originMsg中{*}个数");

                retMsg = java.text.MessageFormat.format(originMsg, agrs);

            }else{

                retMsg = java.text.MessageFormat.format(originMsg, agrs);

            }
        }
        return retMsg;
    }


}
