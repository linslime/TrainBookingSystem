package com.trainbookingsystem.service;

import com.trainbookingsystem.data.*;
import com.trainbookingsystem.entity.*;
import com.trainbookingsystem.mapper.DataMapper;
import com.trainbookingsystem.parser.RailNetworkParser;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Data
@Service
public class DataImpl {
    private int stationNumber = 10;
    private int RouteNumber = 12;
    private String beginTime = "2023-1-1";
    private String endTime = "2023-1-7";
    private int turns = 20;

    @Resource
    private DataMapper dataMapper ;

    public String createData(){
        dataMapper.deleteAllRailways();
        dataMapper.deleteAllRoutes();
        dataMapper.deleteAllTrains();
        dataMapper.deleteAllTickets();
        dataMapper.deleteAllStations();

        StationCreator stationCreator = new StationCreator();
        stationCreator.setStations(new ArrayList<>());
        stationCreator.setStationNumber(stationNumber);
        stationCreator.createStation();
        for(Station station:stationCreator.getStations()){
            dataMapper.addStation(station.getStationId(),station.getStationName());
        }

        RailwayCreator railwayCreator = new RailwayCreator();
        railwayCreator.setRailways(new ArrayList<>());
        railwayCreator.createRailway(stationCreator.getStationNumber(),stationCreator.getStations());
        for(Railway railway:railwayCreator.getRailways()){
            dataMapper.addRailway(railway.getRailwayId(),railway.getRailwayName(),railway.getFromStationId(),railway.getToStationId(),railway.getDistance());
        }

        RouteCreator routeCreator = new RouteCreator();
        routeCreator.setRouteNumber(RouteNumber);
        routeCreator.setRoutes(new ArrayList<>());
        routeCreator.setRoutesList(new ArrayList<>());
        routeCreator.setBaseStationModelsList(new ArrayList<>());
        routeCreator.createRoutes(stationCreator.getStationNumber(), RailNetworkParser.parser(stationCreator.getStations(),railwayCreator.getRailways()));
        for(Route route: routeCreator.getRoutes()){
            dataMapper.addRoute(route.getRouteId(),route.getRouteDefine());
        }

        TrainCreator trainCreator = new TrainCreator();
        trainCreator.setTrains(new ArrayList<>());
        trainCreator.createTrain(routeCreator.getBaseStationModelsList(),routeCreator.getRoutes(),beginTime,endTime,turns);
        for(Train train:trainCreator.getTrains()){
            dataMapper.addTrain(train.getTrainNo(),train.getDepartureTime(),train.getRouteId(),train.getTrainDefine());
        }

        TicketsCreator ticketsCreator = new TicketsCreator();
        ticketsCreator.setTickets(new ArrayList<>());
        ticketsCreator.createTickets(routeCreator.getRoutes());
        for (Ticket ticket:ticketsCreator.getTickets()){
            dataMapper.addTicket(ticket.getTicketId(),ticket.getRouteId(),ticket.getFromStationId(),ticket.getToStationId());
        }
        return "hello";
    }

}
