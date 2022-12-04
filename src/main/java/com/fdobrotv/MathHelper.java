package com.fdobrotv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MathHelper {
    public static double getMathematicalExpectation(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(Double.NaN);
    }

    public static double getDispersion(List<Double> values) {
        double acc = 0;
        for (Double val : values) {
            acc += Math.pow(val, 2) * 1/values.size();
        }
        return acc - Math.pow(getMathematicalExpectation(values), 2);
    }

    protected static Map<Integer, List<Double>> splitValuesByIntervals(List<Double> values, int subIntervals) {
        List<Double> sortedValues = values.stream().sorted().collect(Collectors.toList());
        Double firstValue = sortedValues.get(0);
        Double lastValue = sortedValues.get(values.size() - 1);
        double range = lastValue - firstValue;
        Double intervalSize = range / subIntervals;
        Map<Integer, List<Double>> tableValues = new HashMap<>();
        Integer interval = 1;
        for (Double value : sortedValues) {
            if (value < interval * intervalSize) {
                insertTableValue(tableValues, interval, value);
            } else {
                insertTableValue(tableValues, interval, value);
                interval++;
            }
        }
        return tableValues;
    }

    private static void insertTableValue(Map<Integer, List<Double>> tableValues, Integer interval, Double value) {
        if (!tableValues.containsKey(interval)) {
            List<Double> rangeValues = new ArrayList<>() {{
                add(value);
            }};
            tableValues.put(interval, rangeValues);
        } else {
            List<Double> rangeValues = tableValues.get(interval);
            rangeValues.add(value);
        }
    }
}
