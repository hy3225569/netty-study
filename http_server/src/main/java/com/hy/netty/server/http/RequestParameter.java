package com.hy.netty.server.http;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName RequestParameter
 * @Description Todo 请求参数
 * @Author holy
 * @Date 2018/12/11 15:16
 **/
public class RequestParameter {
    /**
     * 远程地址
     */
    private String remoteAddress;
    /**
     * 自定义参数1
     */
    private Object param1;
    /**
     * json格式的参数体
     */
    private JSONObject jsonParams;

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Object getParam1() {
        return param1;
    }

    public void setParam1(Object param1) {
        this.param1 = param1;
    }

    public JSONObject getJsonParams() {
        return jsonParams;
    }

    public void setJsonParams(JSONObject jsonParams) {
        this.jsonParams = jsonParams;
    }
}
