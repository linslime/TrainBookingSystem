package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.Ticket;
import com.trainbookingsystem.entity.Train;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TicketMapper {
    @Select("select * from tickets where from_station_id = #{fromStationId}")
    List<Ticket> selectByFromStationId(String fromStationId);

    @Select("select * from tickets where to_station_id = #{toStationId}")
    List<Ticket> selectByToStationId(String toStationId);


}
