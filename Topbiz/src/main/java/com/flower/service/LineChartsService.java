package com.flower.service;

import com.flower.vo.LineChartSVO;

public interface LineChartsService {
        LineChartSVO getTrainingMetrics(String pattern);
}
