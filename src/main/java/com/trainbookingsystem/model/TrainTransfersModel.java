package com.trainbookingsystem.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrainTransfersModel {

    //第一辆车次
    private long firstTrainNo;

    private long firstRouteId;
    //第二辆车次
    private long secondTrainNo;

    private long secondRouteId;
    //出发站
    private long fromStationId;
    //目的站
    private long toStationId;
    //中转站点
    private long transfersStationId;



}
