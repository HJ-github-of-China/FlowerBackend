package com.flower.vo;

import com.flower.entity.MatrixData;
import lombok.Data;

import java.util.List;

@Data
public class ScatterPlotVO {
    List<MatrixData> matrixData;
    Integer clusterCount;
}
