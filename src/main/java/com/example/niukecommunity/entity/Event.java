package com.example.niukecommunity.entity;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class Event {
    //事件主题
    private String topic;
    //事件发起者（比如说张三给李四点赞,这里就是张三)
    private int userId;
    private int entityId;
    //事件接受者（比如上面的李四）
    private int entityType;

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    private int entityUserId;
    private Map<String,Object> data=new HashMap<>();
}
