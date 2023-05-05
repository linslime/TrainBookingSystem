package com.trainbookingsystem.controller;

import com.trainbookingsystem.entity.Railway;
import com.trainbookingsystem.entity.Station;
import com.trainbookingsystem.mapper.NetMapper;
import com.trainbookingsystem.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NetController {
    @Resource
    NetMapper netMapper ;
    @GetMapping("/api/getNet")
    public List<Railway> getNet(){
        System.out.println("hahah");
        return netMapper.getNet();
    }

    @GetMapping("/api/deleteStation")
    public Result deleteStation(long stationId){
        netMapper.deleteStation(stationId);
        netMapper.deleteRailwayByStation(stationId);
        return new Result(200);
    }

    @GetMapping("/api/deleteRailway")
    public Result deleteRailway(long railwayId){
        netMapper.deleteRailway(railwayId);
        return new Result(200);
    }

    @GetMapping("/api/updateStationName")
    public Result updateStationName(Station station){
        netMapper.updateStationName(station.getStationId(),station.getStationName());
        return new Result(200);
    }
}
