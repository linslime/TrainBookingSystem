package com.trainbookingsystem.service;

import com.trainbookingsystem.entity.Train;
import com.trainbookingsystem.mapper.TicketBookingSearcherMapper;
import com.trainbookingsystem.mapper.TrainMapper;
import com.trainbookingsystem.mapper.TrainTransfersMapper;
import com.trainbookingsystem.model.TicketModel;
import com.trainbookingsystem.model.TicketTransfersModel;
import com.trainbookingsystem.model.TrainModel;
import com.trainbookingsystem.model.TrainTransfersModel;
import com.trainbookingsystem.parser.TicketParser;
import com.trainbookingsystem.parser.TrainParser;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Service
public class TicketBookingSearcher {
    //排序关键字
    private final static String DEPARTURE_TIME = "departureTime";
    private final static String ARRIVAL_TIME = "arrivalTime";
    private final static String TRAVEL_TIME = "travelTime";
    private final static String PRICE = "price";
    //搜索关键字
    private Long fromStationId;
    private Long toStationId;
    private Date date;
    //排序
    private String isSortedBy;


    @Resource
    TicketBookingSearcherMapper ticketBookingSearcherMapper;
    @Resource
    TrainTransfersMapper trainTransfersMapper;
    @Resource
    TrainMapper trainMapper;

    public List<TicketModel> getDirectTickets(){
        TrainParser trainParser = new TrainParser();
        TicketParser ticketParser = new TicketParser();
        List<Train> trains = ticketBookingSearcherMapper.selectTrainByStation(fromStationId, toStationId);
        List<TrainModel> trainModels = new ArrayList<>();
        for (Train train : trains) {
            TrainModel trainModel = null;
            try {
                trainModel = trainParser.parser(train.getTrainDefine());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            trainModels.add(trainModel);
        }
        List<TicketModel> ticketModels = new ArrayList<>();
        for (TrainModel trainModel : trainModels) {
            ticketModels.add(ticketParser.getTicketModel(fromStationId, toStationId, trainModel));
        }
        return ticketModels;
    }
    public List<TicketTransfersModel> getTransitTickets(){
        TrainParser trainParser = new TrainParser();
        TicketParser ticketParser = new TicketParser();
        List<TrainTransfersModel> trainTransfersModels = trainTransfersMapper.select(fromStationId,toStationId);
        List<TicketTransfersModel> ticketTransfersModels = new ArrayList<>();
        Train train = null;
        for(TrainTransfersModel trainTransfersModel:trainTransfersModels){
            trainTransfersModel.setFromStationId(fromStationId);
            trainTransfersModel.setToStationId(toStationId);

            TicketTransfersModel ticketTransfersModel = new TicketTransfersModel();
            train = trainMapper.selectByTrainNo(trainTransfersModel.getFirstTrainNo());
            try {
                TrainModel trainModel = trainParser.parser(train.getTrainDefine());

                ticketTransfersModel.setFirstTicketModel(ticketParser.getTicketModel(trainTransfersModel.getFromStationId(),trainTransfersModel.getTransfersStationId(),trainModel));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            train = trainMapper.selectByTrainNo(trainTransfersModel.getSecondTrainNo());
            try {
                TrainModel trainModel = trainParser.parser(train.getTrainDefine());
                ticketTransfersModel.setSecondTicketModel(ticketParser.getTicketModel(trainTransfersModel.getTransfersStationId(),trainTransfersModel.getToStationId(),trainModel));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ticketTransfersModel.setTransfersStationId(trainTransfersModel.getTransfersStationId());
            ticketTransfersModels.add(ticketTransfersModel);
        }
        return ticketTransfersModels;
    }
}
