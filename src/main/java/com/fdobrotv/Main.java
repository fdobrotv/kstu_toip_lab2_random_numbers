package com.fdobrotv;

import com.fdobrotv.methods.RandomTableMethod;
import com.fdobrotv.methods.ResidualMethod;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.fdobrotv.MockData.getDiscreteRandomTable;

public class Main {
    public static void main(String[] args) {
        ResidualMethod mm = new ResidualMethod();
        ArrayList<Double> values = mm.getValues(100);

        TreeMap<Integer, BigDecimal> discreteRandomTable = getDiscreteRandomTable();
        RandomTableMethod rtm = new RandomTableMethod(discreteRandomTable);
        ArrayList<Double> valuesByRTM = rtm.getValues(1000);
        Map<Integer, List<Double>> valuesByIntervals = rtm.getValuesByIntervals(10);
        EventQueue.invokeLater(() -> new DistributionHistogram(valuesByIntervals));
    }
}
