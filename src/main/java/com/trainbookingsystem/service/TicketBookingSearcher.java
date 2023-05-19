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
    private int isSortedBy;


    @Resource
    TicketBookingSearcherMapper ticketBookingSearcherMapper;
    @Resource
    TrainTransfersMapper trainTransfersMapper;
    @Resource
    TrainMapper trainMapper;
    @Resource
    TicketParser ticketParser = new TicketParser();
    public List<TicketModel> getDirectTickets(){
        TrainParser trainParser = new TrainParser();
        List<Train> trains = ticketBookingSearcherMapper.selectTrainByStation(fromStationId, toStationId,date);
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
            TicketModel t = ticketParser.getTicketModel(fromStationId, toStationId, trainModel);
            if(t.departureTime().getTime() < date.getTime()){
                ticketModels.add(t);
            }
        }
        sortTicketModel(ticketModels);
        return ticketModels;
    }
    public List<TicketTransfersModel> getTransitTickets(){
        TrainParser trainParser = new TrainParser();
        System.out.println(date);
        List<TrainTransfersModel> trainTransfersModels = trainTransfersMapper.select(fromStationId,toStationId,date);
        System.out.println(trainTransfersModels.size());
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
            if (ticketTransfersModel.getStopTime() > 0 && ticketTransfersModel.getDepartureTime().getTime() < date.getTime()){
                ticketTransfersModels.add(ticketTransfersModel);
            }
        }
        sortTicketTransfersModel(ticketTransfersModels);
        return ticketTransfersModels;
    }

    private void quickSortTicketModelAscendingByPassTime(List<TicketModel> ticketModels,int low,int high){
        int i,j;
        TicketModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getPassTime()<=ticketModels.get(j).getPassTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getPassTime()>=ticketModels.get(i).getPassTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketModels.get(j);
                ticketModels.set(j,ticketModels.get(i));
                ticketModels.set(i,t);
            }

        }
        ticketModels.set(low,ticketModels.get(i));
        ticketModels.set(i,temp);
        quickSortTicketModelAscendingByPassTime(ticketModels,low,j-1);
        quickSortTicketModelAscendingByPassTime(ticketModels,j+1,high);
        return;
    }
    private void quickSortTicketModelDescendingByPassTime(List<TicketModel> ticketModels,int low,int high){
        int i,j;
        TicketModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getPassTime()>=ticketModels.get(j).getPassTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getPassTime()<=ticketModels.get(i).getPassTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketModels.get(j);
                ticketModels.set(j,ticketModels.get(i));
                ticketModels.set(i,t);
            }

        }
        ticketModels.set(low,ticketModels.get(i));
        ticketModels.set(i,temp);
        quickSortTicketModelDescendingByPassTime(ticketModels,low,j-1);
        quickSortTicketModelDescendingByPassTime(ticketModels,j+1,high);
        return;
    }
    private void quickSortTicketModelAscendingByDepartureTime(List<TicketModel> ticketModels,int low,int high){
        int i,j;
        TicketModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.departureTime().getTime()<=ticketModels.get(j).departureTime().getTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.departureTime().getTime()>=ticketModels.get(i).departureTime().getTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketModels.get(j);
                ticketModels.set(j,ticketModels.get(i));
                ticketModels.set(i,t);
            }

        }
        ticketModels.set(low,ticketModels.get(i));
        ticketModels.set(i,temp);
        quickSortTicketModelAscendingByDepartureTime(ticketModels,low,j-1);
        quickSortTicketModelAscendingByDepartureTime(ticketModels,j+1,high);
        return;
    }
    private void quickSortTicketModelDescendingByDepartureTime(List<TicketModel> ticketModels,int low,int high){
        int i,j;
        TicketModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.departureTime().getTime()>=ticketModels.get(j).departureTime().getTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.departureTime().getTime()<=ticketModels.get(i).departureTime().getTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketModels.get(j);
                ticketModels.set(j,ticketModels.get(i));
                ticketModels.set(i,t);
            }

        }
        ticketModels.set(low,ticketModels.get(i));
        ticketModels.set(i,temp);
        quickSortTicketModelDescendingByDepartureTime(ticketModels,low,j-1);
        quickSortTicketModelDescendingByDepartureTime(ticketModels,j+1,high);
        return;
    }
    private void quickSortTicketModelAscendingByPrice(List<TicketModel> ticketModels,int low,int high){
        int i,j;
        TicketModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getSoftSeatPrice()<=ticketModels.get(j).getSoftSeatPrice()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getSoftSeatPrice()>=ticketModels.get(i).getSoftSeatPrice()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketModels.get(j);
                ticketModels.set(j,ticketModels.get(i));
                ticketModels.set(i,t);
            }

        }
        ticketModels.set(low,ticketModels.get(i));
        ticketModels.set(i,temp);
        quickSortTicketModelAscendingByPrice(ticketModels,low,j-1);
        quickSortTicketModelAscendingByPrice(ticketModels,j+1,high);
        return;
    }
    private void quickSortTicketModelDescendingByPrice(List<TicketModel> ticketModels,int low,int high){
        int i,j;
        TicketModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getSoftSeatPrice()>=ticketModels.get(j).getSoftSeatPrice()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getSoftSeatPrice()<=ticketModels.get(i).getSoftSeatPrice()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketModels.get(j);
                ticketModels.set(j,ticketModels.get(i));
                ticketModels.set(i,t);
            }

        }
        ticketModels.set(low,ticketModels.get(i));
        ticketModels.set(i,temp);
        quickSortTicketModelDescendingByPrice(ticketModels,low,j-1);
        quickSortTicketModelDescendingByPrice(ticketModels,j+1,high);
        return;
    }
    private void sortTicketModel(List<TicketModel> ticketModels){
        if(isSortedBy == 1){
            quickSortTicketModelDescendingByPassTime(ticketModels,0,ticketModels.size()-1);
        }else if (isSortedBy == 2){
            quickSortTicketModelAscendingByDepartureTime(ticketModels,0,ticketModels.size()-1);
        }else if (isSortedBy == 3){
            quickSortTicketModelDescendingByDepartureTime(ticketModels,0,ticketModels.size()-1);
        }else if (isSortedBy == 4){
            quickSortTicketModelAscendingByPrice(ticketModels,0,ticketModels.size()-1);
        }else if (isSortedBy == 5){
            quickSortTicketModelDescendingByPrice(ticketModels,0,ticketModels.size()-1);
        }else{
            quickSortTicketModelAscendingByPassTime(ticketModels,0,ticketModels.size()-1);
        }

    }

    private void quickSortTicketTransfersModelAscendingByPassTime(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getPassTime()<=ticketTransfersModels.get(j).getPassTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getPassTime()>=ticketTransfersModels.get(i).getPassTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelAscendingByPassTime(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelAscendingByPassTime(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelDescendingByPassTime(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getPassTime()>=ticketTransfersModels.get(j).getPassTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getPassTime()<=ticketTransfersModels.get(i).getPassTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelDescendingByPassTime(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelDescendingByPassTime(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelAscendingByDepartureTime(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getDepartureTime().getTime()<=ticketTransfersModels.get(j).getDepartureTime().getTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getDepartureTime().getTime()>=ticketTransfersModels.get(i).getDepartureTime().getTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelAscendingByDepartureTime(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelAscendingByDepartureTime(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelDescendingByDepartureTime(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getDepartureTime().getTime()>=ticketTransfersModels.get(j).getDepartureTime().getTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getDepartureTime().getTime()<=ticketTransfersModels.get(i).getDepartureTime().getTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelDescendingByDepartureTime(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelDescendingByDepartureTime(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelAscendingByStopTime(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getStopTime()<=ticketTransfersModels.get(j).getStopTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getStopTime()>=ticketTransfersModels.get(i).getStopTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelAscendingByStopTime(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelAscendingByStopTime(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelDescendingByStopTime(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getStopTime()>=ticketTransfersModels.get(j).getStopTime()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getStopTime()<=ticketTransfersModels.get(i).getStopTime()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelDescendingByStopTime(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelDescendingByStopTime(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelAscendingByPrice(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getSoftSeatPrice1()+temp.getSoftSeatPrice2()<=ticketTransfersModels.get(j).getSoftSeatPrice1()+ticketTransfersModels.get(j).getSoftSeatPrice2()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getSoftSeatPrice1()+temp.getSoftSeatPrice2()>=ticketTransfersModels.get(i).getSoftSeatPrice1()+ticketTransfersModels.get(i).getSoftSeatPrice2()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelAscendingByPrice(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelAscendingByPrice(ticketTransfersModels,j+1,high);
        return;
    }
    private void quickSortTicketTransfersModelDescendingByPrice(List<TicketTransfersModel> ticketTransfersModels,int low,int high){
        int i,j;
        TicketTransfersModel temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        temp = ticketTransfersModels.get(low);
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getSoftSeatPrice1()+temp.getSoftSeatPrice2()>=ticketTransfersModels.get(j).getSoftSeatPrice1()+ticketTransfersModels.get(j).getSoftSeatPrice2()&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getSoftSeatPrice1()+temp.getSoftSeatPrice2()<=ticketTransfersModels.get(i).getSoftSeatPrice1()+ticketTransfersModels.get(i).getSoftSeatPrice2()&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = ticketTransfersModels.get(j);
                ticketTransfersModels.set(j,ticketTransfersModels.get(i));
                ticketTransfersModels.set(i,t);
            }
        }
        ticketTransfersModels.set(low,ticketTransfersModels.get(i));
        ticketTransfersModels.set(i,temp);
        quickSortTicketTransfersModelDescendingByPrice(ticketTransfersModels,low,j-1);
        quickSortTicketTransfersModelDescendingByPrice(ticketTransfersModels,j+1,high);
        return;
    }
    private void sortTicketTransfersModel(List<TicketTransfersModel> ticketTransfersModels){
        if(isSortedBy == 1){
            quickSortTicketTransfersModelDescendingByPassTime(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else if(isSortedBy == 2){
            quickSortTicketTransfersModelAscendingByDepartureTime(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else if(isSortedBy == 3){
            quickSortTicketTransfersModelDescendingByDepartureTime(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else if(isSortedBy == 4){
            quickSortTicketTransfersModelAscendingByPrice(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else if(isSortedBy == 5){
            quickSortTicketTransfersModelDescendingByPrice(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else if(isSortedBy == 6){
            quickSortTicketTransfersModelAscendingByStopTime(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else if(isSortedBy == 7){
            quickSortTicketTransfersModelDescendingByStopTime(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }else{
            quickSortTicketTransfersModelAscendingByPassTime(ticketTransfersModels,0,ticketTransfersModels.size()-1);
        }
    }
}
