package com.flower.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flower.utils.DoubleArray2DDeserializerUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainData {

    private int round;        // 训练轮次
    private double loss;      // 损失值
    private double accuracy;  // 准确率public class TrainData
    private double recall;
    private double precision;
    @JsonProperty("F1") // 明确告诉 Jackson，JSON 中的 "F1" 对应 Java 中的 f1 字段
    private double f1;
    // 使用自定义的反序列化数据模型
    @JsonDeserialize(using = DoubleArray2DDeserializerUtils.class)
    private double[][] matrix;
}
