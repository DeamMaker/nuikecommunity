package com.example.niukecommunity.controller;

import com.example.niukecommunity.entity.Event;
import com.example.niukecommunity.entity.User;
import com.example.niukecommunity.event.EventProducer;
import com.example.niukecommunity.service.LikeService;
import com.example.niukecommunity.util.CommunityConstant;
import com.example.niukecommunity.util.CommunityUtil;
import com.example.niukecommunity.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {
    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;


    //因为是异步请求，所以加上ResponseBody。为啥是异步请求呢？因为点赞的时候，页面并没有刷新
    @PostMapping("/like")
    @ResponseBody
    public String like(int entityType,int entityId,int entityUserId,int postId){
        User user=hostHolder.getUser();
        //进行点赞
        likeService.like(user.getId(),entityType,entityId,entityUserId);
        //点赞数量
        long likeCount=likeService.findEntityLikeCount(entityType,entityId);
        //点赞的状态
        int likeStatus=likeService.findEntityLikeStatus(user.getId(),entityType,entityId);
        //将点赞状态和点赞数量封装在Map集合中
        Map<String,Object> map=new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);

        //触发点赞事件
        if(likeStatus==1){
            Event event=new Event()
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityId(entityId)
                    .setEntityType(entityType)
                    .setData("postId",postId)
                    .setEntityUserId(entityUserId)
                    .setTopic(TOPIC_LIKE);
            eventProducer.fireEvent(event);
        }



        //将结果返回给页面
        return CommunityUtil.getJSONString(0,null,map);
    }
}
