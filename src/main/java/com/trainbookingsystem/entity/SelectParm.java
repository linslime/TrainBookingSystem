package com.trainbookingsystem.entity;

import lombok.Data;

@Data
public class SelectParm {
    private long fromStationId;
    private long toStationId;
    private String departureTime;
    private String sortWay;
}
