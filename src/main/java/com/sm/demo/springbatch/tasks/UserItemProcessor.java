package com.sm.demo.springbatch.tasks;

import com.sm.demo.springbatch.proxy.UserExternalClient;
import com.sm.demo.springbatch.proxy.model.UserExternalClientResponse;
import com.sm.demo.springbatch.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class UserItemProcessor implements ItemProcessor<List<UserEntity>, Map<Integer, String>> {
    private final UserExternalClient userExternalClient;
    @Override
    public Map<Integer, String> process(List<UserEntity> users)  {
        log.info("<<<<<<<<<<Task-2.UserItemProcessor>>>>>>>>");
        return processUsers(users);
    }

    private Map<Integer, String> processUsers(List<UserEntity> users){
        Map<Integer, String> map = new HashMap<>();
        users.forEach(user -> processUserNotification(user, map));
        return map;
    }

    private void processUserNotification(UserEntity userEntity, Map<Integer, String> map) {
        try{
            UserExternalClientResponse userExternalClientResponse = userExternalClient.sendNotification(userEntity);
            map.put(userEntity.getId(), userExternalClientResponse.isSuccess() ? "SUCCESS": "FAILED");
        }catch(Exception e){
            e.printStackTrace();
            map.put(userEntity.getId(), "FAILED");
        }
    }
}
