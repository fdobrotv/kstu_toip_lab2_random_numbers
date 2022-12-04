package com.fdobrotv;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GraphHelper {
    static void showHistogram(Map<Integer, List<Double>> values) {
        EventQueue.invokeLater(() -> new DistributionHistogram(values));
    }
}
