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
        for (int i = 0; i < subIntervals; i++) {
            List<Double> doubles = tableValues.get(i);
            double frequencyOfMatch = 0.0;
            if (doubles.size() != 0) {
                frequencyOfMatch = (double) doubles.size() / (double) values.size();
            }
            String countOfValuesInInterval = Integer.toString(doubles.size());
            table[i + 1] = new String[] {
                    i + "/" + subIntervals,
                    countOfValuesInInterval,
                    Double.toString(frequencyOfMatch)
            };
            summary += doubles.size();
        }

        for (final Object[] row : table) {
            System.out.format("| %15s | %65s | %29s |%n", row);
        }

        String[] args = {"", "Total count of RVs: " + summary};
        System.out.format("%17s | %65s |%n", (Object[]) args);
    }
}
