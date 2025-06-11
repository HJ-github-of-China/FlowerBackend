package com.flower.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesItem {
    private String name;      // 指标名称，如 "Loss", "Accuracy"
    private List<Double> data; // 对应每一轮的值
}
