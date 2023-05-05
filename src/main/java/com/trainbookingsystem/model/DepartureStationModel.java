package com.trainbookingsystem.model;

import lombok.Data;

import java.util.Date;

@Data
public class DepartureStationModel extends BaseStationModel{
    private Date departureTime;
}
