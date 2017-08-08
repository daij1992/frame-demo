package com.dj.web.exception;



import com.alibaba.fastjson.JSON;
import com.dj.web.utils.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


/**
 * Created by daijie on 2017-8-4.
 */
public class BusinessSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	private final static Logger LOG = LoggerFactory.getLogger(BusinessSimpleMappingExceptionResolver.class);
	
	
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {


		HandlerMethod handlerMethod = (HandlerMethod) handler;
		ResponseBody body = handlerMethod.getMethodAnnotation(ResponseBody.class);

		//没有错误页面 全部是JSON
		if(body == null){
			LOG.info("--------------没有加@ResponseBody注解  说明是跳转错误页面--------");
			return super.doResolveException(request, response, handlerMethod, ex);
		}
		LOG.info("--------------加了@ResponseBody注解  说明是JSON返回--------");

		ModelAndView mv = new ModelAndView();

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.OK.value());
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache,must-revalidate");

		//封装异常
		Wrapper<String> wrapper = formatExceptionWrapper(ex);

		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(JSON.toJSONString(wrapper));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mv;
	}

	private Wrapper<String> formatExceptionWrapper(Exception ex){
		String code;
		String msg;
		if(ex instanceof  BusinessException){
			//业务异常----非运行时 必须try catch
			LOG.error("非运行 业务异常---ret_code-->异常码:{}",((BusinessException) ex).getCode());
			LOG.error("非运行 业务异常---ret_msg--->异常描述:{}",((BusinessException)ex).getMsg());
			LOG.error("栈信息------>", ex);
			code = ((BusinessException) ex).getCode();
			msg = ((BusinessException)ex).getMsg();
		}else if(ex instanceof  RuntimeBusinessException){
			//业务异常----运行时 不用try catch
			LOG.error("运行时 业务异常---ret_code-->异常码:{}",((RuntimeBusinessException) ex).getCode());
			LOG.error("运行时 业务异常---ret_msg--->异常描述:{}",((RuntimeBusinessException)ex).getMsg());
			LOG.error("栈信息------>", ex);
			code = ((RuntimeBusinessException) ex).getCode();
			msg = ((RuntimeBusinessException)ex).getMsg();
		}else{
			if(ex instanceof NullPointerException){
				LOG.error("业务异常---ret_code-->异常码：E1S001");
				LOG.error("业务异常---ret_msg--->异常描述: 空指针");
				LOG.error("栈信息------>", ex);
				code = "E01S001";
				msg = "NullPointerException";
			}else if(ex instanceof SQLException){
				LOG.error("业务异常---ret_code-->异常码：E1S002");
				LOG.error("业务异常---ret_msg--->异常描述: SQLException");
				LOG.error("栈信息------>", ex);
				code = "E01S002";
				msg = "SQLException";
			}else{
				LOG.error("业务异常---ret_code-->异常码：E1S000");
				LOG.error("业务异常---ret_msg--->异常描述: Exception");
				LOG.error("栈信息------>", ex);
				code = "E01S000";
				msg = "系统出错";
			}
		}
		return new Wrapper<String>(code,msg,"");
	}
}
