package com.kingtechfin.wxthirdparty.Result;

import com.kingtechfin.wxthirdparty.globalenum.ServerStatusEnum;

public class ExceptionResult extends Exception {
    private ServerResult serverResult;

    public ExceptionResult() {
        super();
        this.serverResult = new ServerResult(ServerStatusEnum.SERVICE_FAILED);
    }

    public ExceptionResult(String msg) {
        super(msg);
        this.serverResult = new ServerResult(ServerStatusEnum.SERVICE_FAILED, msg);
    }

    public ExceptionResult(ServerResult serverResult) {
        this.serverResult = serverResult;
    }

    public ServerResult getServerResult() {
        return serverResult;
    }

    public void setServerResult(ServerResult serverResult) {
        this.serverResult = serverResult;
    }
}
