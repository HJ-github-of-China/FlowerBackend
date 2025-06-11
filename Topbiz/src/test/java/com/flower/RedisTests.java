package com.flower;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flower.entity.TrainData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class RedisTests {
     @Autowired
    private StringRedisTemplate redisTemplate;




    @Test
    public void testGetLoss() {
        // 获取所有匹配的键，例如 "ROUND:*"
        List<String> keys = redisTemplate.keys("*Training*")
            .stream()
            .sorted()
            .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

       List<TrainData> dataList = keys.stream().map(key -> {
        try {
        String value = redisTemplate.opsForValue().get(key);

        // 1. 解析 JSON 字符串为 Map 或自定义对象
        TrainData data = mapper.readValue(value, TrainData.class);

        // 2. 从 key 提取 Round 编号
        String roundStr = key.split(" ")[1]; // 取出 "1"
        data.setRound(Integer.parseInt(roundStr));
        return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());


        dataList.forEach(data -> {
            System.out.println("Round: " + data.getRound() + data);
        });
    }


}
