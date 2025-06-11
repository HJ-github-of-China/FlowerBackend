package com.flower.vo;

import com.flower.entity.SeriesItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineChartSVO {

     private List<Integer> rounds;       // 所有轮次
    private List<SeriesItem> series;    // 各指标数据
}
