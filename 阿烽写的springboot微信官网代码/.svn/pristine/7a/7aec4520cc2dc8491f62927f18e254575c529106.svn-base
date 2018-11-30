package com.kingtechfin.wxthirdparty.Result;

import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mrchao on 2018/5/18.
 */
public class ServerResult {

    private int status;
    private String message;
    private Object result;
    private String timestamp;

    public static String getCurrentTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    }

    public ServerResult() {
        this.status = ServerStatusEnum.SERVICE_OK.getStatus();
        this.message = ServerStatusEnum.SERVICE_OK.getMsg();
        this.timestamp = getCurrentTimestamp();
    }

    public ServerResult(Object result) {
        this.status = ServerStatusEnum.SERVICE_OK.getStatus();
        this.message = ServerStatusEnum.SERVICE_OK.getMsg();
        this.result = result;
        this.timestamp = getCurrentTimestamp();
    }

    public ServerResult(int status, String message, String result) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.timestamp = getCurrentTimestamp();
    }

    public ServerResult(ServerStatusEnum serverStatusEnum, Object result) {
        this.status = serverStatusEnum.getStatus();
        this.message = serverStatusEnum.getMsg();
        this.result = result;
        this.timestamp = getCurrentTimestamp();
    }

    public ServerResult(ServerStatusEnum serverStatusEnum) {
        this.status = serverStatusEnum.getStatus();
        this.message = serverStatusEnum.getMsg();
        this.timestamp = this.getCurrentTimestamp();
    }

    public void updateResult(ServerStatusEnum serverStatusEnum) {
        this.status = serverStatusEnum.getStatus();
        this.message = serverStatusEnum.getMsg();
        this.timestamp = this.getCurrentTimestamp();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String toString() {
        return "{"
                + "\"status\"" + ":" + String.valueOf(status) + ","
                + "\"message\"" + ":"  + "\"" + message + "\"" + ","
                + "\"result\"" + ":"  + "\"" + result + "\"" + ","
                + "\"timestamp\"" + ":"  + "\"" + timestamp + "\""
                + "}";
    }
}
