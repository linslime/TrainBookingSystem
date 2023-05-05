package com.trainbookingsystem.mapper;

import com.trainbookingsystem.model.TicketModel;
import org.apache.ibatis.annotations.Insert;

public interface BookingMapper {
    @Insert("insert into ticket_model(user_id,train_no,departure_station_id,arrival_station_id,departure_time,arrival_time,pass_time,soft_seat_price,hard_seat_price,first_class_seat_price,second_class_seat_price,business_class_seat_price) values()")
    void addTicket(TicketModel ticketModel);
}
