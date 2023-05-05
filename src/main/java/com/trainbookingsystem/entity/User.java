package com.trainbookingsystem.entity;

import lombok.Data;

@Data
public class User {
    private long id;
    private String userName;
    private String password;
    private String role;
}
