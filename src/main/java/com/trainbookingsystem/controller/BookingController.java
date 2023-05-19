package com.trainbookingsystem.controller;

import com.trainbookingsystem.entity.DirectTicket;
import com.trainbookingsystem.entity.TransfersTicket;
import com.trainbookingsystem.mapper.BookingMapper;
import com.trainbookingsystem.mapper.UserMapper;
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

    @Resource
    UserMapper userMapper;

    @GetMapping("/api/saveTicket")
    public Result saveTicket(DirectTicket directTicket){
        bookingMapper.addTicket(directTicket);
        return new Result(200);
    }

    @GetMapping("/api/saveTransfersTicket")
    public Result saveTransfersTicket(TransfersTicket transfersTicket){
        bookingMapper.addTransfersTicket(transfersTicket);
        return new Result(200);
    }

    @GetMapping("/api/getDirectTicketByUserId")
    public List<DirectTicket> getDirectTicketByUserId(long id){
        return userMapper.getDirectTicketByUserId(id);
    }

    @GetMapping("/api/getTransfersTicketByUserId")
    public List<TransfersTicket> getTransfersTicketByUserId(long id){
        return userMapper.getTransferTicketByUserId(id);
    }

    @GetMapping("/api/deleteDirectTicketByUserId")
    public Result deleteDirectTicketByUserId(long id){
        bookingMapper.deleteDirectTicketByUserId(id);
        return new Result(200);
    }

    @GetMapping("/api/deleteTransfersTicketByUserId")
    public Result deleteTransfersTicketByUserId(long id){
        bookingMapper.deleteTransfersTicketByUserId(id);
        return new Result(200);
    }
}
