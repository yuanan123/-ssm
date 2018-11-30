package com.kingtechfin.wxthirdparty.logaspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mrchao on 2018/5/15.
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.kingtechfin.wxthirdparty.controller..*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("########################### REQUEST BEGIN ###############################");
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());

        // 打印参数
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest)
            {
                logger.info("PARAM[" + i + "] : " + "HttpServletRequest");
                continue;
            } else if (args[i] instanceof HttpServletResponse)
            {
                logger.info("PARAM[" + i + "] : " + "HttpServletResponse");
                continue;
            }
            logger.info("PARAM[" + i + "] : " + JSON.toJSONString(args[i]));
        }

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        logger.info("RESPONSE : " + JSON.toJSONString(result));
        logger.info("########################### RESPONSE END ###############################");
        // 处理完请求，返回内容
        return result;
    }
}
