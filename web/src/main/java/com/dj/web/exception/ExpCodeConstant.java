package com.dj.web.exception;

/**
 * Created by Leo on 2017-3-20.
 */
public class ExpCodeConstant {

    //1. 成功和系统级别错误的异常码和返回信息 已经硬编码到 BusinessSimpleMappingExceptionResolver
    //1.1 成功：
        //code: 默认为 0
        //msg:  默认为 成功
    //1.2 系统级别错误:
        //code: 默认采用 E + 项目id(2位字符) + S + 具体码(3位字符) --->比如: E01S001(数据库异常)

    //2. 自定义业务码
        //code: 默认采用 E + 项目id(2位字符) + 模块id(1位字符) + 具体码(3位字符)
        //msg: 通过exception_zh.properties实现国际化和动态填充(填充模式参考slf4j)


    //用户模块
    public  enum  M_USER{
        PARAM_ILLEGAL("E11001"),   // 参数不合法:{}
        USER_NOT_EXSIT ("E11002"),     // id为{}的用户不存在
        USER_DELETE_FORBIDDEN("E11003");// 删除失败:{}


        public String code;

        M_USER(String code){
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }



}
