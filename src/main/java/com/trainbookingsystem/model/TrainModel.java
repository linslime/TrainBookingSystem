package com.trainbookingsystem.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrainModel {
    //车次
    private long trainNo;
    //经过的站点
    private long routeId;
    //经过的站点
    private List<BaseStationModel> baseStationModels;


}
