package com.flower.utils;


    public class NumberUtils {
    public static double round(double value, int scale) {
        if (scale < 0) throw new IllegalArgumentException("Scale must be >= 0");
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }
}
