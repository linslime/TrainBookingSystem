package com.trainbookingsystem.data;

import com.trainbookingsystem.entity.Railway;
import com.trainbookingsystem.entity.Station;
import lombok.Data;

import java.security.SecureRandom;
import java.util.List;


@Data
public class RailwayCreator {

    private List<Railway> railways;

    public void createRailway(int stationNumber, List<Station> stations){
        long railwayNumber = 0;

        SecureRandom r = new SecureRandom();
        for(int i = 0;i<stationNumber;i++){
            Station station = stations.get(i);

            //随机邻接站点数目
            int adjacencyStationNumber = r.nextInt(3) + 1;
            while(adjacencyStationNumber > 0){
                int toStationNo = r.nextInt(stationNumber);
                if (toStationNo != i){
                    adjacencyStationNumber--;

                    //随机出度数目
                    int outDegree = r.nextInt(2) + 1;
                    while(outDegree > 0){
                        outDegree --;

                        long fromStationId = station.getStationId();
                        long toStationId = stations.get(toStationNo).getStationId();
                        float distance = r.nextFloat() * 30  + 20;
                        //float price = r.nextFloat() * 15 + 10;

                        Railway railway = new Railway();
                        railway.setRailwayId(railwayNumber);
                        railway.setRailwayName("RN" + railwayNumber);
                        railway.setFromStationId(fromStationId);
                        railway.setToStationId(toStationId);
                        railway.setDistance(distance);
                        //railway.setPrice(price);

                        railwayNumber++;

                        railways.add(railway);
                    }

                    //随机入度数目
                    int inDegree = r.nextInt(2) + 1;
                    while(inDegree > 0){
                        inDegree --;

                        long toStationId = station.getStationId();
                        long fromStationId = stations.get(toStationNo).getStationId();
                        float distance = r.nextFloat() * 30  + 20;
                        float price = r.nextFloat() * 15 + 10;

                        Railway railway = new Railway();
                        railway.setRailwayId(railwayNumber);
                        railway.setRailwayName("RN" + railwayNumber);
                        railway.setFromStationId(fromStationId);
                        railway.setToStationId(toStationId);
                        railway.setDistance(distance);

                        railwayNumber++;

                        railways.add(railway);
                    }
                }

            }
        }

    }

}
