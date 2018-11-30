package com.kingtechfin.wxthirdparty.config;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.kingtechfin.wxthirdparty.Result.ExceptionResult;
import com.kingtechfin.wxthirdparty.Result.ServerResult;
import com.kingtechfin.wxthirdparty.globalenum.MysqlStatusMap;
import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;
import com.kingtechfin.wxthirdparty.logaspect.LogAspect;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by mrchao on 2018/6/23.
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ServerResult allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        ServerResult serverResult = null;

        if (exception instanceof DataAccessException) {
            SQLException exSql = (SQLException)exception.getCause();
            serverResult = new ServerResult(
                    exSql.getErrorCode(),
                    MysqlStatusMap.getSingle().getSqlMsg(exSql.getErrorCode()),
                    exception.getMessage());
        } else if(exception instanceof NullPointerException) {
            serverResult = new ServerResult(
                    ServerStatusEnum.SERVICE_FAILED,
                    "空数据，参数不正确");
        } else if(exception instanceof MybatisPlusException) {
            serverResult = new ServerResult(ServerStatusEnum.SERVICE_FAILED);
            String retMsg = null;
            Object obj = exception.getCause();
            if (obj instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = (InvocationTargetException)obj;
                obj = invocationTargetException.getTargetException();
                if (obj instanceof PersistenceException) {
                    retMsg = ((PersistenceException) obj).getMessage();
                } else {
                    retMsg = ((Throwable) obj).getMessage();
                }
            } else {
                retMsg = ((Throwable) obj).getMessage();
            }
            serverResult.setResult(retMsg);

        } else if (exception instanceof ExceptionResult) {
            ExceptionResult exceptionResult = (ExceptionResult)exception;
            serverResult = exceptionResult.getServerResult();
        }
        else {
            serverResult = new ServerResult(
                    ServerStatusEnum.SERVICE_FAILED,
                    exception.getMessage());
            System.out.println(exception.getClass());
        }
        logger.error(serverResult.toString());
        return serverResult;
    }
}
