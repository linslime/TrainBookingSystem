package com.trainbookingsystem.mapper;

import com.trainbookingsystem.model.TrainTransfersModel;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface TrainTransfersMapper {
    @Select("select tr1.train_no as first_train_no, tr1.route_id as first_route_id, tr2.train_no as second_train_no, tr2.route_id as second_route_id, ti1.to_station_id as transfers_station_id from tickets ti1, tickets ti2, trains tr1, trains tr2 where ti1.from_station_id = #{fromStationId} and ti2.to_station_id = #{toStationId} and ti1.to_station_id = ti2.from_station_id and tr1.route_id = ti1.route_id and tr2.route_id = ti2.route_id and ti1.route_id<>ti2.route_id and tr1.departure_time<#{date} and tr2.departure_time < DATE_ADD(tr1.departure_time, INTERVAL 1.5 hour) and tr2.departure_time > DATE_ADD(tr1.departure_time, INTERVAL 1 hour) limit 50")
    List<TrainTransfersModel> select(long fromStationId, long toStationId, Date date);
}
