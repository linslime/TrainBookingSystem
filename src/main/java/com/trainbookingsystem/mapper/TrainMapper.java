package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.Train;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TrainMapper {
    @Select("select * from trians where route_id = #{routeId}")
    List<Train> selectByRouteId(long routeId);

    @Select("select * from trains where train_no = #{trainNo}")
    Train selectByTrainNo(long trainNo);

    @Select("select * from trains ")
    List<Train> getAll();
}
