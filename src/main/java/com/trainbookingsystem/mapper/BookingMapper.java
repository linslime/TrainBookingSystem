package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.DirectTicket;
import com.trainbookingsystem.entity.TransfersTicket;
import com.trainbookingsystem.model.TicketModel;
import com.trainbookingsystem.model.TicketTransfersModel;
import org.apache.ibatis.annotations.Insert;

public interface BookingMapper {

    @Insert("insert into ticket_model(user_id,train_no,departure_station_id,arrival_station_id,departure_time,arrival_time,pass_time,price_type,price) values(#{userId},#{trainNo},#{departureStationId},#{arrivalStationId},#{departureTime},#{arrivalTime},#{passTime},#{priceType},#{price})")
    void addTicket(DirectTicket directTicket);

//    @Insert("insert into ticket_model(user_id,train_no1,departure_station_id1,arrival_station_id1,departure_time1,arrival_time1,pass_time1,soft_seat_price1,hard_seat_price1,first_class_seat_price1,second_class_seat_price1,business_class_seat_price1,train_no2,departure_station_id2,arrival_station_id2,departure_time2,arrival_time2,pass_time2,soft_seat_price2,hard_seat_price2,first_class_seat_price2,second_class_seat_price2,business_class_seat_price2,transfers_station_id,pass_time,stop_time) values(#{userId},#{trainNo1},#{departureStationId1},#{arrivalStationId1},#{departureTime1},#{arrivalTime1},#{passTime1},#{softSeatPrice1},#{hardSeatPrice1},#{firstClassSeatPrice1},#{secondClassSeatPrice1},#{businessClassSeatPrice1},,#{trainNo2},#{departureStationId2},#{arrivalStationId2},#{departureTime2},#{arrivalTime2},#{passTime2},#{softSeatPrice2},#{hardSeatPrice2},#{firstClassSeatPrice2},#{secondClassSeatPrice2},#{businessClassSeatPrice2},#{transfersStationId},#{passTime},#{stopTime})")
    @Insert("insert into ticket_transfers_model(user_id,train_no1,departure_station_id1,arrival_station_id1,departure_time1,arrival_time1,pass_time1,price_type1,price1,train_no2,departure_station_id2,arrival_station_id2,departure_time2,arrival_time2,pass_time2,price_type2,price2,transfers_station_id,pass_time,stop_time) values(#{userId},#{trainNo1},#{departureStationId1},#{arrivalStationId1},#{departureTime1},#{arrivalTime1},#{passTime1},#{priceType1},#{price1},#{trainNo2},#{departureStationId2},#{arrivalStationId2},#{departureTime2},#{arrivalTime2},#{passTime2},#{priceType2},#{price2},#{transfersStationId},#{passTime},#{stopTime})")
    void addTransfersTicket(TransfersTicket transfersTicket);
}
