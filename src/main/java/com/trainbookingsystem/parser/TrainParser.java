package com.trainbookingsystem.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.trainbookingsystem.model.ArrivalStationModel;
import com.trainbookingsystem.model.DepartureStationModel;
import com.trainbookingsystem.model.IntermediateStationModel;
import com.trainbookingsystem.model.TrainModel;
import lombok.Data;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

@Data
public class TrainParser {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final static String ROUTE_ID = "routeId";
    private final static String STATION_LIST = "stationList";
    private final static String NODE_TYPE = "nodeType";
    private final static String TRAIN_NO = "trainNo";
    private final static String DEPARTURE = "departure";
    private final static String STOPS = "stops";
    private final static String ARRIVAL = "arrival";

    public TrainModel parser(String trainDefine) throws ParseException {
        JsonNode root = null;
        try {
            root = objectMapper.readTree(trainDefine);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        long trainNo = root.get(TRAIN_NO).asLong();
        long routeId = root.get(ROUTE_ID).asLong();

        TrainModel trainModel = new TrainModel();

        trainModel.setTrainNo(trainNo);
        trainModel.setRouteId(routeId);
        trainModel.setBaseStationModels(new ArrayList<>());

        JsonNode stationList = root.get(STATION_LIST);
        Iterator<JsonNode> nodes = stationList.elements();
        while (nodes.hasNext()){
            JsonNode node = nodes.next();
            if(node.has(NODE_TYPE)){
                String nodeType = node.get(NODE_TYPE).asText();
                if(DEPARTURE.equals(nodeType)) {
                    // 开始节点
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    DepartureStationModel model = new DepartureStationModel();
                    model.setNodeType(DEPARTURE);
                    model.setStationId(node.get("stationId").asLong());
                    model.setDepartureTime(sdf.parse( node.get("departureTime").asText() ));
                    model.setExt(node.get("ext").asText());
                    trainModel.getBaseStationModels().add(model);
                }else if(STOPS.equals(nodeType)){
                    IntermediateStationModel model = null;
                    try {
                        model = objectMapper.readValue(node.toString(), IntermediateStationModel.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    trainModel.getBaseStationModels().add(model);
                }else if(ARRIVAL.equals(nodeType)){
                    ArrivalStationModel model = null;
                    try {
                        model = objectMapper.readValue(node.toString(), ArrivalStationModel.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    trainModel.getBaseStationModels().add(model);
                }
            }
        }
        return trainModel;
    }
}
