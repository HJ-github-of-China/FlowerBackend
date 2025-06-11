package com.flower.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

// 自定义反序列化器
public class DoubleArray2DDeserializerUtils extends StdDeserializer<double[][]> {
     public DoubleArray2DDeserializerUtils() {
        this(null);
    }

    public DoubleArray2DDeserializerUtils(Class<?> vc) {
        super(vc);
    }

    @Override
    public double[][] deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String text = node.asText();
        text = text.replace("[", "").replace("]", "");
        String[] rows = text.split(",");

        int rowCount = (int) Math.sqrt(rows.length); // Assuming square matrix
        double[][] result = new double[rowCount][rowCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                result[i][j] = Double.parseDouble(rows[i * rowCount + j].trim());
            }
        }

        return result;
    }
}
