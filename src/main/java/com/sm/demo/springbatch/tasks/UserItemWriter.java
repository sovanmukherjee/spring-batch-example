package com.sm.demo.springbatch.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class UserItemWriter implements ItemWriter<Map<Integer, String>> {

    @Override
    public void write(Chunk<? extends Map<Integer, String>> data) {
        log.info("<<<<<<<<<<Task-3.UserItemWriter>>>>>>>>>>>");
        Optional.ofNullable(data.getItems())
                .orElseThrow(RuntimeException::new)
                .forEach(map -> map.entrySet().forEach(this::printLog));
    }

    private void printLog(Map.Entry<Integer, String> map) {
        log.info("Id: {}, Status: {}", map.getKey(), map.getValue());
    }
}
