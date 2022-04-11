package com.example.niukecommunity.dao;

import com.example.niukecommunity.entity.LoginTicket;
import org.apache.ibatis.annotations.*;
@Mapper
@Deprecated    //设置为不推荐使用
public interface LoginTicketMapper {
    @Insert({"insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"})
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select("select * from login_ticket where ticket=#{ticket}")
    LoginTicket selectByTicket(String ticket);


    @Update("update login_ticket set status=#{status} where ticket=#{ticket}")
    int updateStatus(String ticket,int status);
}
