package com.trainbookingsystem.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainbookingsystem.entity.Route;
import com.trainbookingsystem.entity.Ticket;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class TicketsCreator {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final static String ROUTE_ID = "routeId";
    private final static String STATION_LIST = "stationList";
    private final static String STATION_ID = "stationId";
    private List<Ticket> tickets;

    private List<Long> getStations(Route route){
        List<Long> str = new ArrayList<>();
        String routeDefine = route.getRouteDefine();
        try {
            JsonNode root = objectMapper.readTree(routeDefine);
            JsonNode stationList = root.get(STATION_LIST);
            Iterator<JsonNode> stations = stationList.elements();
            while(stations.hasNext()){
                JsonNode station = stations.next();
                str.add(station.get(STATION_ID).asLong());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return str;
    }

    public void createTickets(List<Route> routes){
        int ticketNumber = 0;

        for(int i = 0; i < routes.size(); i++){
            Route route = routes.get(i);
            List<Long> stations = getStations(route);
            for (int from = 0; from < stations.size() - 1; from++ ){
                for(int to = from + 1; to < stations.size(); to++){
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(ticketNumber);
                    ticketNumber++;
                    ticket.setRouteId(route.getRouteId());
                    ticket.setFromStationId(stations.get(from));
                    ticket.setToStationId(stations.get(to));

                    tickets.add(ticket);
                }
            }
        }
    }

}
