package com.trainbookingsystem;

import com.trainbookingsystem.model.DepartureStationModel;
import com.trainbookingsystem.model.TicketModel;
import com.trainbookingsystem.model.TrainModel;
import com.trainbookingsystem.parser.TrainParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
@MapperScan("com.trainbookingsystem.mapper")
public class TrainBookingSystemApplication {

    public static void main(String[] args)  {
        SpringApplication.run(TrainBookingSystemApplication.class, args);
    }

}
