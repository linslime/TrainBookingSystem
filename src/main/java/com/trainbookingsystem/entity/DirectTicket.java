package com.trainbookingsystem.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class DirectTicket {
    private long userId;
    private long trainNo;
    private long departureStationId;
    private long arrivalStationId;
    private Date departureTime;
    private Date arrivalTime;
    private long passTime;
    private String priceType;
    private float price;

    public String getDepartureTime(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(departureTime);
        return time;
    }

    public String getArrivalTime(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(arrivalTime);
        return time;
    }

//    public long getPassTime(){
//        return passTime/1000/60 * -1;
//    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
