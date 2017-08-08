package com.dj.web.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * MDCFitler（Mapped Diagnostic Context，映射调试上下文）
 *
 * 每个HTTP请求生成一个traceid,log4j打印日志时,自动打印traceid,用于日志跟踪调用链
 * Created by daijie on 2017-8-8.
 */
public class MDCFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //放入 traceid
        //注意put的名称必须和log4j.properties里面的配置一致
        //比如该项目中要打印traceid,log4j.properties中ConversionPattern 必须配置traceid:[%X{traceid}]
        MDC.put("traceid",System.currentTimeMillis()+"-"+Thread.currentThread().getId());
        chain.doFilter(request,response);
        //清理 traceid tomcat线程池 内存溢出 不能用MDC.clear()
        MDC.remove("traceid'");
    }

    public void destroy() {

    }
}
