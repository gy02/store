package com.admin.controller;

import com.admin.entity.User;
import com.admin.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gy
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        return Result.success("查询成功",userService.list());
    }

    @PostMapping ("/login")
    public Result<Map<String, Object>> login(@RequestBody User user){
        Map<String, Object> data=userService.login(user);
        if (data!=null)
            return Result.success(data);
        else
            return Result.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        Map<String,Object> data=userService.getUserInfo(token);
        if(data!=null)
            return Result.success(data);
        else
            return Result.fail(20003,"登录已过期，请重新登录");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
       userService.logout(token);
       return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false)String username,
                                              @RequestParam(value = "phone",required = false)String phone,
                                              @RequestParam(value = "pageNo") Long pageNo,
                                              @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        String pwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        userService.save(user);
        return Result.success("新增用户成功");
    }

    @PutMapping//“修改密码”功能在个人主页实现
    public Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);//设置为空，则字段不更新
        userService.updateById(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer userId){
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer userId){
        userService.removeById(userId);
        return Result.success("删除用户成功");
    }
}
