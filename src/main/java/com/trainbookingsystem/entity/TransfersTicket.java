package com.trainbookingsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TransfersTicket {
    private long id;
    private long userId;
    private long trainNo1;
    private long departureStationId1;
    private long arrivalStationId1;
    private Date departureTime1;
    private Date arrivalTime1;
    private long passTime1;
    private float priceType1;
    private float price1;
    private long trainNo2;
    private long departureStationId2;
    private long arrivalStationId2;
    private Date departureTime2;
    private Date arrivalTime2;
    private long passTime2;
    private float priceType2;
    private float price2;
    private long transfersStationId;
    private long passTime;
    private long stopTime;
}
