package com.fdobrotv;

import java.util.List;
import java.util.Map;

public class TableHelper {
    public static void printFrequencyTable(List<Double> values, int subIntervals) {
        final Object[][] table = new String[subIntervals + 1][];
        table[0] = new String[] { "Interval", "Count of RV (frequency of match), " +
                "dropped into current interval", "Relative frequency of match" };

        Map<Integer, List<Double>> tableValues = MathHelper.splitValuesByIntervals(values, subIntervals);

        int summary = 0;
        for (int i = 1; i <= subIntervals; i++) {
            List<Double> doubles = tableValues.get(i);
            String frequencyOfMatch = Double.toString((double) doubles.size() / (double) values.size());
            String countOfValuesInInterval = Integer.toString(doubles.size());
            table[i] = new String[] { i + "/" + subIntervals, countOfValuesInInterval, frequencyOfMatch};
            summary += doubles.size();
        }

        for (final Object[] row : table) {
            System.out.format("| %15s | %65s | %29s |%n", row);
        }

        String[] args = {"", "Total count of RVs: " + summary};
        System.out.format("%17s | %65s |%n", (Object[]) args);
    }
}
