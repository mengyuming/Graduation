package com.graduation.tools;

import java.util.ArrayList;
import java.util.List;

public class ControMessage {
    //成功状态码：200   失败状态码：100
    private Integer status;
    //存储状态信息
    private String message;
    //存储需要返回的数据
    private List<Object> all;

    public ControMessage setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ControMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Object> setAll() {
        this.all = new ArrayList<Object>();
        return all;
    }

    public ControMessage contrlSuccess() {
        this.status=200;
        return this;
    }
    public ControMessage contrlError() {
        this.status=100;
        return this;
    }

    public List<Object> getAll() {
        return all;
    }
}
