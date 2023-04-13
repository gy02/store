package com.example.store;


import com.admin.entity.User;
import com.admin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }
    @Resource
    private UserMapper userMapper;

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
    @Test
    void testUserDao(){
        for (User user : userMapper.selectList(null)) {
            System.out.println(user);
        }

    }
}
