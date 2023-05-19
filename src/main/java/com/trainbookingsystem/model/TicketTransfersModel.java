package com.trainbookingsystem.model;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TicketTransfersModel {
    private long userId;
    private long trainNo1;
    private long departureStationId1;
    private String departureStationName1;
    private long arrivalStationId1;
    private String arrivalStationName1;
    private Date departureTime1;
    private Date arrivalTime1;
    private long passTime1;
    private float softSeatPrice1;
    private float hardSeatPrice1;
    private float firstClassSeatPrice1;
    private float secondClassSeatPrice1;
    private float businessClassSeatPrice1;
    private long trainNo2;
    private long departureStationId2;
    private String departureStationName2;
    private long arrivalStationId2;
    private String arrivalStationName2;
    private Date departureTime2;
    private Date arrivalTime2;
    private long passTime2;
    private float softSeatPrice2;
    private float hardSeatPrice2;
    private float firstClassSeatPrice2;
    private float secondClassSeatPrice2;
    private float businessClassSeatPrice2;
    private long transfersStationId;
    private long passTime;
    private long stopTime;

    public Date getDepartureTime(){
        return this.departureTime1;
    }
    public void setFirstTicketModel(TicketModel ticketModel){
        trainNo1 = ticketModel.getTrainNo();
        departureStationId1 = ticketModel.getDepartureStationId();
        departureStationName1 = ticketModel.getDepartureStationName();
        arrivalStationId1 = ticketModel.getArrivalStationId();
        arrivalStationName1 = ticketModel.getArrivalStationName();
        setDepartureTime1(ticketModel.getDepartureTime());
        setArrivalTime1(ticketModel.getArrivalTime());
        passTime1 = ticketModel.getPassTime();
        softSeatPrice1 = ticketModel.getSoftSeatPrice();
        hardSeatPrice1 = ticketModel.getHardSeatPrice();
        firstClassSeatPrice1 = ticketModel.getFirstClassSeatPrice();
        secondClassSeatPrice1 = ticketModel.getSecondClassSeatPrice();
        businessClassSeatPrice1 = ticketModel.getBusinessClassSeatPrice();
    }
    public void setSecondTicketModel(TicketModel ticketModel){
        trainNo1 = ticketModel.getTrainNo();
        departureStationId2 = ticketModel.getDepartureStationId();
        departureStationName2 = ticketModel.getDepartureStationName();
        arrivalStationId2 = ticketModel.getArrivalStationId();
        arrivalStationName2 = ticketModel.getArrivalStationName();
        setDepartureTime2(ticketModel.getDepartureTime());
        setArrivalTime2(ticketModel.getArrivalTime());
        passTime2 = ticketModel.getPassTime();
        softSeatPrice2 = ticketModel.getSoftSeatPrice();
        hardSeatPrice2 = ticketModel.getHardSeatPrice();
        firstClassSeatPrice2 = ticketModel.getFirstClassSeatPrice();
        secondClassSeatPrice2 = ticketModel.getSecondClassSeatPrice();
        businessClassSeatPrice2 = ticketModel.getBusinessClassSeatPrice();

        stopTime = (departureTime2.getTime()-arrivalTime1.getTime())/1000/60;
        passTime = passTime1 + passTime2 + stopTime;
    }
    public String getDepartureTime1(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(departureTime1);
        return time;
    }
    public String getArrivalTime1(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(arrivalTime1);
        return time;
    }
    public void setArrivalTime1(String time)  {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        Date date = null;
        try {
            date = sdf.parse( time );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.arrivalTime1 = date;
    }
    public void setDepartureTime1(String time) {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        Date date = null;
        try {
            date = sdf.parse( time );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.departureTime1 = date;
    }
    public String getDepartureTime2(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(departureTime2);
        return time;
    }
    public String getArrivalTime2(){
        String time = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(arrivalTime2);
        return time;
    }
    public void setArrivalTime2(String time)  {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        Date date = null;
        try {
            date = sdf.parse( time );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.arrivalTime2 = date;

    }
    public void setDepartureTime2(String time) {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        Date date = null;
        try {
            date = sdf.parse( time );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.departureTime2 = date;
    }
}
