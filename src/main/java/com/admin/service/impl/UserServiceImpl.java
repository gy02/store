package com.admin.service.impl;

import com.admin.entity.User;
import com.admin.mapper.UserMapper;
import com.admin.service.IUserService;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gy
 * @since 2023-03-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, Object> login(User user) {
//        先根据用户名做查询
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
//        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);
//        不为空则生成token，并将用户信息存入redis
        if(loginUser!=null&&passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){
//            暂时用UUID,终极方案是jwt
            String key="user:"+ UUID.randomUUID();
//            存入redis
            loginUser.setPassword(null);
            //30分钟后失效
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
//            返回数据
            Map<String,Object> data=new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    /**
     * 根据token，从Redis中获取用户信息
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String token) {
        Object object = redisTemplate.opsForValue().get(token);
        if(object!=null){
            String s = JSON.toJSONString(object);
            User loginUser = JSON.parseObject(s,User.class);
            Map<String,Object> data=new HashMap<>();
            data.put("username",loginUser.getUsername());
            data.put("status",loginUser.getStatus());
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

}
