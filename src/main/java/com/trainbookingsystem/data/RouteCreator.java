package com.trainbookingsystem.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trainbookingsystem.entity.Railway;
import com.trainbookingsystem.entity.Route;
import com.trainbookingsystem.model.*;
import lombok.Data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Data
public class RouteCreator {
    private int routeNumber;
    private List<List<Railway>> routesList;
    private List<Route> routes;
    private List<List<BaseStationModel>> baseStationModelsList;

    private final static String NODE_TYPE_DEPARTURE = "departure";
    private final static String NODE_TYPE_STOPS = "stops";
    private final static String NODE_TYPE_ARRIVAL = "arrival";

    private void createRoutesLists(int stationNumber, RailNetworkModel railNetworkModel){
        SecureRandom r = new SecureRandom();
        int size = railNetworkModel.getStationModels().size();
        List<StationModel> stationModels = railNetworkModel.getStationModels();

        for (int i = 0;i < routeNumber;i++){
            List<StationModel> route1 = new ArrayList<>();
            List<Railway> route2 = new ArrayList<>();
            StationModel stationModel = stationModels.get(r.nextInt(size));


            int routeLength = r.nextInt( (int)(stationNumber/2) + 1) + 1;
            for(int j = 0;j < routeLength;j++){
                route1.add(stationModel);

                int key = 10;//防止死循环
                while(key > 0){
                    key --;
                    List<Railway> nextStations = stationModel.getNextStations();
                    int tempSize = nextStations.size();
                    int index = r.nextInt(tempSize);
                    Railway railway = nextStations.get(index);
                    long nextStationId = railway.getToStationId();

                    if(getIndexof(nextStationId,route1) == -1){
                        stationModel = stationModels.get(getIndexof(nextStationId,stationModels));
                        route2.add(railway);
                        break;
                    }
                }
            }
            routesList.add(route2);
        }
    }

    private int getIndexof(long stationId,List<StationModel> route){
        int index;
        for(index = 0;index < route.size();index++){
            if(route.get(index).getStationId() == stationId){
                return index;
            }
        }
        return -1;
    }

    private List<BaseStationModel> createModel(List<Railway> route){
        List<BaseStationModel> baseStationModels = new ArrayList<>();

        DepartureStationModel departureStationModel = new DepartureStationModel();
        departureStationModel.setNodeType(NODE_TYPE_DEPARTURE);
        departureStationModel.setStationId(route.get(0).getFromStationId());
        departureStationModel.setExt("ext");

        baseStationModels.add(departureStationModel);

        for(int i = 0;i < route.size() - 1; i++){
            IntermediateStationModel intermediateStationModel = new IntermediateStationModel();
            intermediateStationModel.setNodeType(NODE_TYPE_STOPS);
            intermediateStationModel.setStationId(route.get(i).getToStationId());
            intermediateStationModel.setRailwayId(route.get(i).getRailwayId());
            intermediateStationModel.setExt("ext");

            baseStationModels.add(intermediateStationModel);
        }

        ArrivalStationModel arrivalStationModel = new ArrivalStationModel();
        arrivalStationModel.setNodeType(NODE_TYPE_ARRIVAL);
        arrivalStationModel.setStationId(route.get(route.size() - 1).getToStationId());
        arrivalStationModel.setRailwayId(route.get(route.size() - 1).getRailwayId());
        arrivalStationModel.setExt("ext");

        baseStationModels.add(arrivalStationModel);

        return baseStationModels;
    }

    private String toJson (List<BaseStationModel> baseStationModels,long routeId)  {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("routeId",routeId);

        ArrayNode arrayNode = mapper.createArrayNode();

        ObjectNode departureNode = mapper.createObjectNode();
        DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(0);
        departureNode.put("nodeType",departureStationModel.getNodeType());
        departureNode.put("stationId",departureStationModel.getStationId());
        departureNode.put("ext",departureStationModel.getExt());
        arrayNode.add(departureNode);

        for(int i = 1; i < baseStationModels.size() - 1; i++){
            ObjectNode intermediateNode = mapper.createObjectNode();
            IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
            intermediateNode.put("nodeType",intermediateStationModel.getNodeType());
            intermediateNode.put("stationId",intermediateStationModel.getStationId());
            intermediateNode.put("railwayId",intermediateStationModel.getRailwayId());
            intermediateNode.put("ext",intermediateStationModel.getExt());
            arrayNode.add(intermediateNode);
        }

        ObjectNode arrivalNode = mapper.createObjectNode();
        ArrivalStationModel arrivalStationModel = (ArrivalStationModel)baseStationModels.get(baseStationModels.size() - 1);
        arrivalNode.put("nodeType",arrivalStationModel.getNodeType());
        arrivalNode.put("stationId",arrivalStationModel.getStationId());
        arrivalNode.put("railwayId",arrivalStationModel.getRailwayId());
        arrivalNode.put("ext",arrivalStationModel.getExt());
        arrayNode.add(arrivalNode);

        rootNode.set("stationList",arrayNode);

        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public void createRoutes(int stationNumber, RailNetworkModel railNetworkModel){

        createRoutesLists(stationNumber,railNetworkModel);

        int routeNum = 0;

        for(int i = 0;i < routesList.size();i++){
            Route route = new Route();
            route.setRouteId(routeNum);
            routeNum++;
            List<BaseStationModel> baseStationModels = createModel(routesList.get(i));
            baseStationModelsList.add(baseStationModels);
            route.setRouteDefine(toJson(baseStationModels, route.getRouteId()));
            routes.add(route);
        }
    }
}
