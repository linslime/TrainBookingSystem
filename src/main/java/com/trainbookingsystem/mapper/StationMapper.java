package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StationMapper {
    @Select("select * from stations")
    List<Station> findAllStation();

    @Select("select station_name from stations where station_id = #{id}")
    String getStationNameById(long id);
}
