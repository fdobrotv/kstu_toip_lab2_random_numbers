package com.fdobrotv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class MathHelper {
    public static double getMathematicalExpectation(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(Double.NaN);
    }

    public static BigDecimal getMathematicalExpectation(TreeMap<Integer, BigDecimal> values) {
        BigDecimal mathExpectation = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> valueToProbability: values.entrySet()){
            BigDecimal value = valueToProbability.getValue().multiply(BigDecimal.valueOf(valueToProbability.getKey()));
            mathExpectation = mathExpectation.add(value);
        }
        return mathExpectation.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    public static double getDispersion(List<Double> values) {
        double acc = 0;
        for (Double val : values) {
            acc += Math.pow(val, 2) * 1/values.size();
        }
        return acc - Math.pow(getMathematicalExpectation(values), 2);
    }

    public static BigDecimal getDispersion(TreeMap<Integer, BigDecimal> values) {
        BigDecimal dispersion = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> entry: values.entrySet()){
            BigDecimal randomValuePoweredTwo = BigDecimal.valueOf(entry.getKey()).pow(2);
            BigDecimal value = randomValuePoweredTwo.multiply(entry.getValue());
            dispersion = dispersion.add(value);
        }
        dispersion = dispersion.subtract(getMathematicalExpectation(values).pow(2)).abs();
        return dispersion.stripTrailingZeros();
    }

    public static Map<Integer, List<Double>> splitValuesByIntervals(List<Double> values, int subIntervals) {
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
