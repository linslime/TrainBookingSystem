package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.Railway;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NetMapper {
    @Select("select distinct from_station_id,to_station_id from railways ")
    List<Railway> getNet();

    @Delete("delete from railways where railway_id = #{id}")
    void deleteRailway(long id);

    @Delete("delete from railways where from_station_id = #{id} or to_station_id = #{id}")
    void deleteRailwayByStation(long id);
    @Delete("delete from stations where station_id = #{id}")
    void deleteStation(long id);
    @Update("update stations set station_name = #{name} where station_id = #{id}")
    void updateStationName(long id , String name);
}
