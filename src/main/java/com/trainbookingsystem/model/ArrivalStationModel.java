package com.trainbookingsystem.model;

import lombok.Data;

@Data
public class ArrivalStationModel extends BaseStationModel{
    private long railwayId;
    private long passTime;
    private float softSeatPrice;
    private float hardSeatPrice;
    private float firstClassSeatPrice;
    private float secondClassSeatPrice;
    private float businessClassSeatPrice;
}
