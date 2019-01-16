package com.yichu.test;

import com.yichu.order.OrderApplication;
import com.yichu.order.distributeservice.OrderService2;
import com.yichu.order.service.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderApplicationTests {
    @Before
    public void start() {
        System.out.println("开始测试##############");
    }

    @After
    public void finish() {
        System.out.println("结束##############");
    }

    @Autowired
    OrderService orderService;

    @Autowired
    OrderService2 orderService2;

    @Test
    public void orderCreate() throws Exception {
        // 未实现分布式事务的方法测试
        orderService.createOrder("tony", "买了根黄瓜");
    }

    @Test
    public void orderCreate2() throws Exception {
        // 实现分布式事务的方法测试
        orderService2.createOrder("tony", "买了80年代根黄瓜");
    }

}
