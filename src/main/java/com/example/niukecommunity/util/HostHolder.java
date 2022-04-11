package com.example.niukecommunity.util;

import com.example.niukecommunity.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，用于代替session对象。并且是线程隔离的，防止在并发下产生冲突
 */
@Component
public class HostHolder {
    //ThreadLocal之所以具有线程隔离的效果，因为它总是先获取当前线程，存取数据和获取数据均是以线程为单位，换句话说就是以线程为"key"，存取和获取数据
    private ThreadLocal<User> users=new ThreadLocal<>();
    public void setUser(User user){
        users.set(user);
    }
    public User getUser(){
        return users.get();
    }
    //清楚对象，防止累计数目过多
    public void clear(){
        users.remove();
    }
}
