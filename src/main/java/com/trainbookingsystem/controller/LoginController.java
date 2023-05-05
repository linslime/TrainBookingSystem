package com.trainbookingsystem.controller;

import com.trainbookingsystem.mapper.UserMapper;
import com.trainbookingsystem.entity.User;
import com.trainbookingsystem.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    UserMapper userMapper;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser, HttpSession session) {
        String userName = requestUser.getUserName();
        userName = HtmlUtils.htmlEscape(userName);
        User user = userMapper.getByUserNameAndPassword(userName, requestUser.getPassword());
        if (null == user) {
            return new Result(400);
        } else {

            session.setAttribute("user", user);
            return new Result(200);
        }
    }
}
