package com.trainbookingsystem.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Railway {
    //id
    private long railwayId;
    //name
    private String railwayName;
    //出发站
    private long fromStationId;
    //目的站
    private long toStationId;
    //距离
    private float distance;

}
