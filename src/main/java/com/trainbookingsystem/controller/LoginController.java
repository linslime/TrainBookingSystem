package com.trainbookingsystem.controller;

import com.trainbookingsystem.entity.DirectTicket;
import com.trainbookingsystem.entity.Train;
import com.trainbookingsystem.entity.TransfersTicket;
import com.trainbookingsystem.exception.ResultBody;
import com.trainbookingsystem.mapper.TrainMapper;
import com.trainbookingsystem.mapper.UserMapper;
import com.trainbookingsystem.entity.User;
import com.trainbookingsystem.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class LoginController {

    @Resource
    UserMapper userMapper;

    @Resource
    TrainMapper trainMapper;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    public ResultBody login(User requestUser, HttpSession session) {
        String userName = requestUser.getUserName();
        userName = HtmlUtils.htmlEscape(userName);
        User user = userMapper.getByUserNameAndPassword(userName, requestUser.getPassword());
        if (null == user) {
            return ResultBody.error("400","登陆失败");
        } else {

            session.setAttribute("user", user);
            return ResultBody.success(user);
        }
    }

    @GetMapping("/api/getTrain")
    public List<Train> getTrain(){
        return trainMapper.getAll();
    }
}
