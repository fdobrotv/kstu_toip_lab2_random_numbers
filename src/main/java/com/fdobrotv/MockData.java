package com.fdobrotv;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class MockData {
    public static TreeMap<Integer, BigDecimal> getDiscreteRandomTable() {
        return new TreeMap<>() {{
            put(1, BigDecimal.valueOf(0.02));
            put(10, BigDecimal.valueOf(0.05));
            put(15, BigDecimal.valueOf(0.1));
            put(23, BigDecimal.valueOf(0.28));
            put(29, BigDecimal.valueOf(0.23));
            put(38, BigDecimal.valueOf(0.22));
            put(42, BigDecimal.valueOf(0.1));
        }};
    }

    // For distribution diagram
    // Map<Integer, Integer> mapHistory = generateRandomDataSet();
    private Map<Integer, Integer> generateRandomDataSet() {
        int height = 256;
        int width = 256;
        int[][] data = new int[width][height];
        for (int c = 0; c < height; c++) {
            for (int r = 0; r < width; r++) {
                data[c][r] = (int) (height * Math.random());
            }
        }
        Map<Integer, Integer> mapHistory = new TreeMap<Integer, Integer>();
        for (int c = 0; c < data.length; c++) {
            for (int r = 0; r < data[c].length; r++) {
                int value = data[c][r];
                int amount = 0;
                if (mapHistory.containsKey(value)) {
                    amount = mapHistory.get(value);
                    amount++;
                } else {
                    amount = 1;
                }
                mapHistory.put(value, amount);
            }
        }
        return mapHistory;
    }
}
