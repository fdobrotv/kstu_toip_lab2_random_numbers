package com.fdobrotv.methods;

import com.fdobrotv.MathHelper;
import com.fdobrotv.TableHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;

public class ResidualMethod {
    private double value = Math.pow(2, -10);
    private ArrayList<Double> values = new ArrayList<>();

    public float getNext() {
        double nextBigDecimal = getNextBigDecimal();
        values.add(nextBigDecimal);
        return (float) nextBigDecimal;
    }

    public ArrayList<Double> getValues(int count) {
        for (int i = 0; i < count; i++) {
            float next = this.getNext();
            System.out.print(next + ", ");
        }
        System.out.println();
        return values;
    }

    private float getNextByString() {
        double result = this.value;
        double preValue = PI * result;
        int indexOfDelimiter = Double.toString(preValue).indexOf(".");
        String substring = Double.toString(preValue).substring(indexOfDelimiter + 3, indexOfDelimiter + 9);
        value = Double.valueOf("0." + substring);
        return (float) result;
    }

    private double getNextBigDecimal() {
        double result = this.value;
        double preValue = PI * result;
        BigDecimal bigDecimal = BigDecimal.valueOf(preValue);
        BigDecimal bigDecimal1 = bigDecimal.movePointRight(2);
        BigDecimal bigDecimal2 = bigDecimal1.remainder(BigDecimal.ONE);
        BigDecimal bigDecimal3 = bigDecimal2.setScale(6, RoundingMode.DOWN);
        value = bigDecimal3.doubleValue();
        return value;
    }

    public double getMathematicalExpectation() {
        return MathHelper.getMathematicalExpectation(values);
    }

    public double getDispersion() {
        return MathHelper.getDispersion(values);
    }

    public void printFrequencyTable(int intervalCount) {
        TableHelper.printFrequencyTable(values, intervalCount);
    }

    public Map<Integer, List<Double>> getValuesByIntervals(int intervalCount) {
        return MathHelper.splitValuesByIntervals(values, intervalCount);
    }
}
