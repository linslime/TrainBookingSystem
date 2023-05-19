package com.trainbookingsystem.parser;

import com.trainbookingsystem.mapper.StationMapper;
import com.trainbookingsystem.model.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TicketParser {
    private int i;
    @Resource
    private StationMapper stationMapper;

    public TicketModel getTicketModel (long departureStationId, long arrivalStationId,TrainModel trainModel){
        TicketModel ticketModel = new TicketModel();

        ticketModel.setTrainNo(trainModel.getTrainNo());
        ticketModel.setDepartureStationId(departureStationId);
        ticketModel.setDepartureStationName(stationMapper.getStationNameById(departureStationId));
        ticketModel.setArrivalStationId(arrivalStationId);
        ticketModel.setArrivalStationName(stationMapper.getStationNameById(arrivalStationId));
        Date a = getArrivalTime(arrivalStationId, trainModel.getBaseStationModels());
        Date b = getDepartureTime(departureStationId,trainModel.getBaseStationModels());
        ticketModel.setArrivalTime(a);
        ticketModel.setDepartureTime(b);

        ticketModel.setPassTime(getPassTime(departureStationId,arrivalStationId,trainModel.getBaseStationModels()));
        ticketModel.setSoftSeatPrice(getSoftSeatPrice(departureStationId,arrivalStationId,trainModel.getBaseStationModels()));
        ticketModel.setHardSeatPrice(getHardSeatPrice(departureStationId,arrivalStationId,trainModel.getBaseStationModels()));
        ticketModel.setFirstClassSeatPrice(getFirstClassSeatPrice(departureStationId,arrivalStationId,trainModel.getBaseStationModels()));
        ticketModel.setSecondClassSeatPrice(getSecondClassSeatPrice(departureStationId,arrivalStationId,trainModel.getBaseStationModels()));
        ticketModel.setBusinessClassSeatPrice(getBusinessClassSeatPrice(departureStationId,arrivalStationId,trainModel.getBaseStationModels()));
        return ticketModel;
    }

    private Date getDepartureTime(long departureStationId, List<BaseStationModel> baseStationModels){
        Date departureTime = new Date();
        long passTime = 0l;
        for(i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                departureTime = (Date) departureStationModel.getDepartureTime().clone();
                if (departureStationId == departureStationModel.getStationId()){
                    break;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                passTime += arrivalStationModel.getPassTime();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                passTime += intermediateStationModel.getPassTime();
                passTime += intermediateStationModel.getResidenceTime();
                if ( departureStationId == intermediateStationModel.getStationId()){
                    break;
                }
            }
        }
        departureTime.setTime(departureTime.getTime() + passTime);
        return departureTime;
    }

    private Date getArrivalTime(long arrivalStationId, List<BaseStationModel> baseStationModels){
        Date departureTime = new Date();
        long passTime = 0l;
        for(i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                departureTime = (Date) departureStationModel.getDepartureTime().clone();
                if (arrivalStationId == departureStationModel.getStationId()){
                    break;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                passTime += arrivalStationModel.getPassTime();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                passTime += intermediateStationModel.getPassTime();
                if ( arrivalStationId==intermediateStationModel.getStationId()){
                    break;
                }
                passTime += intermediateStationModel.getResidenceTime();
            }
        }
        departureTime.setTime(departureTime.getTime() + passTime);
        return departureTime;
    }

    private long getPassTime(long departureStationId, long arrivalStationId, List<BaseStationModel> baseStationModels){
       return  getArrivalTime(arrivalStationId, baseStationModels).getTime() - getDepartureTime(departureStationId, baseStationModels).getTime();
    }

    private float getSoftSeatPrice(long departureStationId, long arrivalStationId, List<BaseStationModel> baseStationModels){
        float price = 0;
        int key = 0;
        for(int i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                if (departureStationId == departureStationModel.getStationId()){
                    key = 1;
                    continue;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                price += arrivalStationModel.getSoftSeatPrice();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                if (key == 1){
                    price += intermediateStationModel.getSoftSeatPrice();
                }
                if (departureStationId == intermediateStationModel.getStationId()){
                    key = 1;
                    continue;
                }
                if ( arrivalStationId == intermediateStationModel.getStationId()){
                    break;
                }
            }
        }
        return price;
    }

    private float getHardSeatPrice(long departureStationId, long arrivalStationId, List<BaseStationModel> baseStationModels){
        float price = 0;
        int key = 0;
        for(int i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                if (departureStationId == departureStationModel.getStationId()){
                    key = 1;
                    continue;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                price += arrivalStationModel.getHardSeatPrice();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                if (key == 1){
                    price += intermediateStationModel.getHardSeatPrice();
                }
                if (departureStationId == intermediateStationModel.getStationId()){
                    key = 1;
                    continue;
                }
                if ( arrivalStationId == intermediateStationModel.getStationId()){
                    break;
                }
            }
        }
        return price;
    }

    private float getFirstClassSeatPrice(long departureStationId, long arrivalStationId, List<BaseStationModel> baseStationModels){
        float price = 0;
        int key = 0;
        for(int i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                if (departureStationId == departureStationModel.getStationId()){
                    key = 1;
                    continue;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                price += arrivalStationModel.getFirstClassSeatPrice();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                if (key == 1){
                    price += intermediateStationModel.getFirstClassSeatPrice();
                }
                if (departureStationId == intermediateStationModel.getStationId()){
                    key = 1;
                    continue;
                }
                if ( arrivalStationId == intermediateStationModel.getStationId()){
                    break;
                }
            }
        }
        return price;
    }

    private float getSecondClassSeatPrice(long departureStationId, long arrivalStationId, List<BaseStationModel> baseStationModels){
        float price = 0;
        int key = 0;
        for(int i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                if (departureStationId == departureStationModel.getStationId()){
                    key = 1;
                    continue;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                price += arrivalStationModel.getSecondClassSeatPrice();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                if (key == 1){
                    price += intermediateStationModel.getSecondClassSeatPrice();
                }
                if (departureStationId == intermediateStationModel.getStationId()){
                    key = 1;
                    continue;
                }
                if ( arrivalStationId == intermediateStationModel.getStationId()){
                    break;
                }
            }
        }
        return price;
    }

    private float getBusinessClassSeatPrice(long departureStationId, long arrivalStationId, List<BaseStationModel> baseStationModels){
        float price = 0;
        int key = 0;
        for(int i = 0; i < baseStationModels.size(); i++){
            if (i == 0){
                DepartureStationModel departureStationModel = (DepartureStationModel) baseStationModels.get(i);
                if (departureStationId == departureStationModel.getStationId()){
                    key = 1;
                    continue;
                }
            }else if(i == baseStationModels.size() - 1){
                ArrivalStationModel arrivalStationModel = (ArrivalStationModel) baseStationModels.get(i);
                price += arrivalStationModel.getBusinessClassSeatPrice();
            }else{
                IntermediateStationModel intermediateStationModel = (IntermediateStationModel) baseStationModels.get(i);
                if (key == 1){
                    price += intermediateStationModel.getBusinessClassSeatPrice();
                }
                if (departureStationId == intermediateStationModel.getStationId()){
                    key = 1;
                    continue;
                }
                if ( arrivalStationId == intermediateStationModel.getStationId()){
                    break;
                }
            }
        }
        return price;
    }

}
