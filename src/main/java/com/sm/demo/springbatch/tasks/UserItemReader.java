package com.sm.demo.springbatch.tasks;

import com.sm.demo.springbatch.repository.UserRepository;
import com.sm.demo.springbatch.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@StepScope
@Component
@Slf4j
public class UserItemReader implements ItemReader<List<UserEntity>> {

    @Autowired
    private UserRepository userRepository;
    private boolean jobState = false;

    @Override
    public List<UserEntity> read() {
        if (!jobState) {
            log.info("<<<<<<<<<<Task-1.UserItemReader>>>>>>>>>>>");
            jobState = true;
            return userRepository.findAll();
       }
       return null;
    }
}
