package com.trainbookingsystem.controller;

import com.trainbookingsystem.mapper.BookingMapper;
import com.trainbookingsystem.model.TicketModel;
import com.trainbookingsystem.model.TicketTransfersModel;
import com.trainbookingsystem.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BookingController {
    @Resource
    BookingMapper bookingMapper;

    @GetMapping("/api/saveTicket")
    public Result saveTicket(TicketModel ticketModel){
        System.out.println("hahah");
        bookingMapper.addTicket(ticketModel);
        return new Result(200);
    }

    @GetMapping("/api/saveTransfersTicket")
    public Result saveTransfersTicket(TicketTransfersModel ticketTransfersModel){
        System.out.println(ticketTransfersModel);
        bookingMapper.addTransfersTicket(ticketTransfersModel);
        return new Result(200);
    }
}
