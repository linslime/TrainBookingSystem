package com.trainbookingsystem.mapper;

import com.trainbookingsystem.entity.DirectTicket;
import com.trainbookingsystem.entity.TransfersTicket;
import com.trainbookingsystem.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select * from user where username = #{userName}")
    User findByUserName(String userName);

    @Select("select * from user where userName = #{userName} and password = #{password}")
    User getByUserNameAndPassword(String userName,String password);

    @Select ("select * from ticket_model where user_id = #{id}")
    List<DirectTicket> getDirectTicketByUserId(long id);

    @Select("select * from ticket_transfers_model where user_id = #{id}")
    List<TransfersTicket> getTransferTicketByUserId(long id);
}
