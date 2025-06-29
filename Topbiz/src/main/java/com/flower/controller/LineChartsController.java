package com.flower.controller;

import com.flower.service.LineChartsService;
import com.flower.vo.LineChartSVO;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "模型监控", description = "模型训练结果展示")
@RestController
@RequestMapping("api/monitor")
@Slf4j
public class LineChartsController {
        @Autowired
        private LineChartsService lineChartsService;

        /**
         * 展示训练集
         * @param pattern 匹配模式
         * @return 训练集or测试集
         */

        @Operation(summary = "展示折线图", description = "通过Echarts展示折线图")
        @GetMapping("/line")
        public LineChartSVO getTrainData(String pattern) {

                return lineChartsService.getTrainingMetrics(pattern);
        }
}
