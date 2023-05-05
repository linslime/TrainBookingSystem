package com.trainbookingsystem.result;

import lombok.Data;

@Data
public class Result {
    //响应码
    private int code;

    public Result(int code) {
        this.code = code;
    }

}

