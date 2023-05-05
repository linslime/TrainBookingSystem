package com.trainbookingsystem.data;

import com.trainbookingsystem.entity.Station;
import lombok.Data;

import java.util.List;

@Data
public class StationCreator {
    private int stationNumber;
    private List<Station> stations;

    public void createStation(){
        for(int i = 0; i < stationNumber;i++){
            Station station = new Station();
            station.setStationId(i);
            station.setStationName("SN" + i);
            stations.add(station);
        }
    }
}
