package com.flower.controller;

import com.flower.service.LineChartsService;
import com.flower.vo.LineChartSVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/train")
@Slf4j
public class LineChartsController {
        @Autowired
        private LineChartsService lineChartsService;

        /**
         * 展示训练集
         * @param pattern 匹配模式
         * @return 训练集or测试集
         */
        @GetMapping("/line")
        public LineChartSVO getTrainData(String pattern) {

                return lineChartsService.getTrainingMetrics(pattern);
        }
}
