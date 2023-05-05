package com.trainbookingsystem.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trainbookingsystem.entity.Route;
import com.trainbookingsystem.entity.Train;
import com.trainbookingsystem.model.ArrivalStationModel;
import com.trainbookingsystem.model.BaseStationModel;
import com.trainbookingsystem.model.DepartureStationModel;
import com.trainbookingsystem.model.IntermediateStationModel;
import lombok.Data;

import java.io.IOException;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class TrainCreator {
    private List<Train> trains;

    public void createTrain(List<List<BaseStationModel>> baseStationModelsList,List<Route> routes, String beginDate, String endDate, int turns) {

        int trainNumber = 0;
        SecureRandom r = new SecureRandom();
        int day = 0;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            day = (int) (end.getTime() - start.getTime()) / 86400000;

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            long routeId = route.getRouteId();
            int tt = day * turns;
            int number = r.nextInt( (int)(tt/2) + 1) + (int)(tt/2);

            for (int j = 0; j < number; j++) {
                Train train = new Train();
                Date departrueTime = randomDate(beginDate,endDate);
                long TN =  trainNumber;

                train.setTrainNo(TN);
                train.setDepartureTime(departrueTime);
                train.setRouteId(routeId);
                train.setTrainDefine(toJson(baseStationModelsList.get(i),routeId,TN,departrueTime));

                trainNumber++;
                trains.add(train);
            }

        }

    }

    private Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private long random(long begin, long end) {
        SecureRandom r = new SecureRandom();
        long tt = (long) (r.nextInt((int)(end - begin) + 1));
        long rtn = begin + tt;
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    private String toJson(List<BaseStationModel> baseStationModels, long routeId, long trainNo, Date departureTime){
        SecureRandom r = new SecureRandom();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("trainNo",trainNo);
        rootNode.put("routeId",routeId);

        ArrayNode arrayNode = mapper.createArrayNode();

        ObjectNode departureNode = mapper.createObjectNode();
        DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(0);
        departureNode.put("nodeType",departureStationModel.getNodeType());
        departureNode.put("stationId",departureStationModel.getStationId());
        departureNode.put("departureTime",departureTime.toString());
        departureNode.put("ext",departureStationModel.getExt());
        arrayNode.add(departureNode);

        for(int i = 1; i < baseStationModels.size() - 1; i++){
            ObjectNode intermediateNode = mapper.createObjectNode();
            int passTime = r.nextInt(3000000 + 1)+600000;
            int residenceTime = r.nextInt(600 + 1) + 300;
            float price = r.nextFloat() * 15 + 10;
            IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
            intermediateNode.put("nodeType",intermediateStationModel.getNodeType());
            intermediateNode.put("stationId",intermediateStationModel.getStationId());
            intermediateNode.put("railwayId",intermediateStationModel.getRailwayId());
            intermediateNode.put("passTime",passTime);
            intermediateNode.put("residenceTime",residenceTime);
            intermediateNode.put("secondClassSeatPrice",price);
            intermediateNode.put("firstClassSeatPrice",price * 1.45);
            intermediateNode.put("businessClassSeatPrice",price * 2.3);
            intermediateNode.put("hardSeatPrice",price * 1.9);
            intermediateNode.put("softSeatPrice", price * 2.8);
            intermediateNode.put("ext",intermediateStationModel.getExt());
            arrayNode.add(intermediateNode);
        }

        int passTime = r.nextInt(3000000 + 1)+600000;
        float price = r.nextFloat() * 15 + 10;
        ObjectNode arrivalNode = mapper.createObjectNode();
        ArrivalStationModel arrivalStationModel = (ArrivalStationModel)baseStationModels.get(baseStationModels.size() - 1);
        arrivalNode.put("nodeType",arrivalStationModel.getNodeType());
        arrivalNode.put("stationId",arrivalStationModel.getStationId());
        arrivalNode.put("railwayId",arrivalStationModel.getRailwayId());
        arrivalNode.put("passTime",passTime);
        arrivalNode.put("secondClassSeatPrice",price);
        arrivalNode.put("firstClassSeatPrice",price * 1.45);
        arrivalNode.put("businessClassSeatPrice",price * 2.3);
        arrivalNode.put("hardSeatPrice",price * 1.9);
        arrivalNode.put("softSeatPrice", price * 2.8);
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

}
