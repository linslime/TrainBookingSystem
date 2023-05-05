package com.trainbookingsystem.entity;

import lombok.Data;

@Data
public class Ticket {
    private long ticketId;
    private long routeId;
    private long fromStationId;
    private long toStationId;
}
