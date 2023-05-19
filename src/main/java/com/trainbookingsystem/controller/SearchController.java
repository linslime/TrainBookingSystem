package com.trainbookingsystem.controller;

import com.trainbookingsystem.entity.SelectParm;
import com.trainbookingsystem.mapper.StationMapper;
import com.trainbookingsystem.model.TicketTransfersModel;
import com.trainbookingsystem.result.Result;
import com.trainbookingsystem.service.DataImpl;
import com.trainbookingsystem.model.TicketModel;
import com.trainbookingsystem.service.TicketBookingSearcher;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class SearchController {
    @Resource
    DataImpl dataImpl = new DataImpl();

    @Resource
    TicketBookingSearcher ticketBookingSearcher = new TicketBookingSearcher();

    @Resource
    StationMapper stationMapper;

    @ResponseBody
    @GetMapping("/api/createDate")
    public Result createDate() {
        dataImpl.createData();
        return new Result(200);
    }


    @GetMapping("/api/getDirectTickets")
    public List<TicketModel> getDirectTickets( SelectParm selectParm) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = sdf.parse( selectParm.getDepartureTime() );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date.setTime(date.getTime() + 86400000);
        ticketBookingSearcher.setFromStationId(selectParm.getFromStationId());
        ticketBookingSearcher.setToStationId(selectParm.getToStationId());
        ticketBookingSearcher.setDate(date);

        ticketBookingSearcher.setIsSortedBy(selectParm.getSortWay());
        List<TicketModel> ticketModels = ticketBookingSearcher.getDirectTickets();
        return ticketModels;

    }
    @GetMapping("/api/getTransitTickets")
    public List<TicketTransfersModel> getTransitTickets( SelectParm selectParm) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = sdf.parse( selectParm.getDepartureTime() );
//            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date.setTime(date.getTime() + 86400000);
        ticketBookingSearcher.setFromStationId(selectParm.getFromStationId());
        ticketBookingSearcher.setToStationId(selectParm.getToStationId());
        ticketBookingSearcher.setDate(date);
        ticketBookingSearcher.setIsSortedBy(selectParm.getSortWay());
        List<TicketTransfersModel> ticketModels = ticketBookingSearcher.getTransitTickets();
        return ticketModels;
    }

}
