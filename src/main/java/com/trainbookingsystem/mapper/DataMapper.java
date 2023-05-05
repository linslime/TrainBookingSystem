package com.trainbookingsystem.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface DataMapper {
    @Update("INSERT INTO stations (station_id,station_name) VALUES(#{stationId},#{stationName})")
    @Transactional//增加
    void addStation (long stationId,String stationName);

    @Update("INSERT INTO railways (railway_id, railway_name,from_station_id,to_station_id,distance) VALUES(#{railwayId}, #{railwayName}, #{fromStationId}, #{toStationId},#{distance})")
    @Transactional
    void addRailway(long railwayId, String railwayName, long fromStationId, long toStationId, float distance);

    @Update("INSERT INTO routes (route_id, route_define) VALUES(#{routeId}, #{routeDefine})")
    @Transactional
    void addRoute(long routeId, String routeDefine);

    @Update("INSERT INTO trains (train_no, departure_time, route_id,train_define) VALUES(#{trainNo}, #{departureTime}, #{routeId}, #{trainDefine})")
    @Transactional
    void addTrain(long trainNo, String departureTime, long routeId,String trainDefine);

    @Update("INSERT INTO tickets (ticket_id, route_id, from_Station_id, to_station_id) VALUES(#{ticketId}, #{routeId},#{fromStationId},#{toStationId})")
    @Transactional
    void addTicket(long ticketId, long routeId, long fromStationId, long toStationId);

    @Delete("DELETE FROM stations")
    void deleteAllStations();

    @Delete("DELETE FROM railways")
    void deleteAllRailways();

    @Delete("DELETE FROM routes")
    void deleteAllRoutes();

    @Delete("DELETE FROM trains")
    void deleteAllTrains();

    @Delete("DELETE FROM tickets")
    void deleteAllTickets();
}
