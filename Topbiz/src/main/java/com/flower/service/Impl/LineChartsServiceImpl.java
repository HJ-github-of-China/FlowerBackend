package com.flower.service.Impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flower.entity.SeriesItem;
import com.flower.entity.TrainData;
import com.flower.service.LineChartsService;
import com.flower.utils.NumberUtils;
import com.flower.vo.LineChartSVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j

public class LineChartsServiceImpl implements LineChartsService {


    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;
    private final View error;

    public LineChartsServiceImpl(StringRedisTemplate redisTemplate, View error) {
        this.redisTemplate = redisTemplate;
        this.mapper = new ObjectMapper();
        this.mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.error = error;
    }

    // 全局异常处理
    @Override
    public LineChartSVO getTrainingMetrics(String pattern) {
        if (pattern == null)
        {
            log.error("数据为空");
            return null;
        }
        log.info("判断数据{}是否为空", pattern);
        pattern = "*" + pattern + "*";
        List<String> keys = redisTemplate.keys(pattern)
                .stream()
                .sorted()
                .collect(Collectors.toList());

        List<TrainData> dataList = keys.stream().map(key -> {
            try {
                String value = redisTemplate.opsForValue().get(key);
                TrainData data = mapper.readValue(value, TrainData.class);

                String roundStr = key.split(" ")[1];
                data.setRound(Integer.parseInt(roundStr));
                return data;
            } catch (Exception e) {
                log.error("解析训练数据失败: {}", e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        List<Integer> rounds = dataList.stream()
                .map(TrainData::getRound)
                .collect(Collectors.toList());

        List<SeriesItem> series = new ArrayList<>();
        series.add(new SeriesItem("Loss", dataList.stream()
            .map(d -> NumberUtils.round(d.getLoss(), 2))
            .collect(Collectors.toList())));
        series.add(new SeriesItem("Accuracy", dataList.stream()
            .map(d -> NumberUtils.round(d.getAccuracy(), 2))
            .collect(Collectors.toList())));
        series.add(new SeriesItem("Recall", dataList.stream()
            .map(d -> NumberUtils.round(d.getRecall(), 2))
            .collect(Collectors.toList())));
        series.add(new SeriesItem("Precision", dataList.stream()
            .map(d -> NumberUtils.round(d.getPrecision(), 2))
            .collect(Collectors.toList())));
        series.add(new SeriesItem("F1", dataList.stream()
            .map(d -> NumberUtils.round(d.getF1(), 2))
            .collect(Collectors.toList())));
        return new LineChartSVO(rounds, series);
    }


}
