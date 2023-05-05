package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.Train;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TicketBookingSearcherMapper {

    @Select("select * from trains where route_id = any(select route_id from tickets where from_station_id = #{fromStationId} and to_station_id = #{toStationId})")
    List<Train> selectTrainByStation(long fromStationId, long toStationId);
}
