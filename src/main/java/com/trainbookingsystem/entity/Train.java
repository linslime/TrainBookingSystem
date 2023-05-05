package com.trainbookingsystem.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Train {
    //车次
    private long trainNo;
    //出发时间
    private Date departureTime;
    //经过的站点
    private long routeId;

    private String trainDefine;

    public String getDepartureTime(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(departureTime);
        return time;
    }

}
