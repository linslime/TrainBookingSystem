package com.trainbookingsystem.parser;

import com.trainbookingsystem.entity.Railway;
import com.trainbookingsystem.entity.Station;
import com.trainbookingsystem.model.RailNetworkModel;
import com.trainbookingsystem.model.StationModel;

import java.util.ArrayList;
import java.util.List;

public class RailNetworkParser {

    public static RailNetworkModel parser(List<Station> stations, List<Railway> railways){
        RailNetworkModel railNetworkModel = new RailNetworkModel();
        railNetworkModel.setStationModels(new ArrayList<>());
        for (Station station:stations){
            StationModel stationModel = new StationModel();

            stationModel.setStationId(station.getStationId());
            stationModel.setStationName(station.getStationName());
            stationModel.setNextStations(new ArrayList<>());
            stationModel.setPriorStations(new ArrayList<>());

            railNetworkModel.getStationModels().add(stationModel);
        }
        for(Railway railway:railways){
            int index;

            long fromStationId = railway.getFromStationId();
            index = railNetworkModel.getIndexof(fromStationId);
            railNetworkModel.getStationModels().get(index).getNextStations().add(railway);

            long toStationId = railway.getToStationId();
            index = railNetworkModel.getIndexof(toStationId);
            railNetworkModel.getStationModels().get(index).getPriorStations().add(railway);
        }
        return railNetworkModel;
    }


}
