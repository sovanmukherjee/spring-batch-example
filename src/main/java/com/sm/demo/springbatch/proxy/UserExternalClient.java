package com.sm.demo.springbatch.proxy;

import com.sm.demo.springbatch.proxy.model.UserExternalClientResponse;
import com.sm.demo.springbatch.repository.entity.UserEntity;

public interface UserExternalClient {
    UserExternalClientResponse sendNotification(UserEntity user);
}
