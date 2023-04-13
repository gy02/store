package com.admin.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-04-09 09:48:06
 */
@SuppressWarnings("serial")
public class User extends Model<User> {
    //用户编号
    private Integer userId;
    //用户名
    private String username;
    //用户密码
    private String password;
    //电话
    private String phone;
    //邮箱
    private String email;
    //权限：-1-黑名单用户，0-普通用户,1-管理员
    private Integer status;
    //头像图片链接
    private String avatar;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
//    @Override
//    protected Serializable pkVal() {
//        return this.userId;
//    }
    }

