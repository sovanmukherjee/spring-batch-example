package com.sm.demo.springbatch.proxy;

import com.sm.demo.springbatch.model.User;
import com.sm.demo.springbatch.proxy.model.UserExternalClientResponse;
import com.sm.demo.springbatch.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class UserExternalClientImpl implements UserExternalClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user-notification-url}")
    private String userNotificationUrl;

    @Override
    public UserExternalClientResponse sendNotification(UserEntity userEntity) {
        log.info("---UserExternalClient---");
        User requestBody = new User();
        BeanUtils.copyProperties(userEntity, requestBody);
        //ResponseEntity<String> response = restTemplate.postForEntity(userNotificationUrl, s, String.class);
        return UserExternalClientResponse.builder().isSuccess(true).build();
    }
}
