package com.zaf.clientbackend;

import com.zaf.apiclinetsdk.client.UserClient;
import com.zaf.apiclinetsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class ClientBackendApplicationTests {
    @Resource
    UserClient userClient;
    @Test
    void contextLoads() throws UnsupportedEncodingException {
        userClient.GetByName("周奥飞");
        userClient.PostByName("周奥飞");
        User user = new User();
        user.setName("周奥飞");
        userClient.PostByUserName(user);
    }

}
