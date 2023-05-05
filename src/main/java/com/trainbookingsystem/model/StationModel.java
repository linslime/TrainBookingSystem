package com.trainbookingsystem.model;

import com.trainbookingsystem.entity.Railway;
import com.trainbookingsystem.entity.Station;
import lombok.Data;

import java.util.List;
@Data
public class StationModel {

    private long StationId;
    private String stationName;
    private List<Railway> nextStations;
    private List<Railway> priorStations;

}
