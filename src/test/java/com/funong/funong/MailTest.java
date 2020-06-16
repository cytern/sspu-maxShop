package com.funong.funong;

import com.funong.funong.plugin.MailTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

/**
 * @Author: cytern
 * @Date: 2020/6/15 11:21
 */
@SpringBootTest
public class MailTest {
    @Autowired
    MailTool mailTool = new MailTool();
    @Test
    public void test(){
        mailTool.sendEmail("测试","测试一下","18325930067@163.com");
    }
}
