package com.trainbookingsystem.model;

import lombok.Data;

import java.util.List;

@Data
public class RailNetworkModel {

    private List<StationModel> stationModels;

    public int getIndexof(long stationId){
        int index;
        for(index = 0;index < stationModels.size();index++){
            if(stationModels.get(index).getStationId() == stationId){
                return index;
            }
        }
        return -1;
    }
}
