package com.fdobrotv.methods;

import com.fdobrotv.MathHelper;
import com.fdobrotv.TableHelper;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomTableMethod {

    private final Logger logger =  Logger.getLogger(this.getClass().getName());
    private final TreeMap<Integer, BigDecimal> discreteRandomTable;
    private final TreeMap<Double, Double> nProbabilityRanges;
    private final ArrayList<Double> values = new ArrayList<>();

    public RandomTableMethod(TreeMap<Integer, BigDecimal> discreteRandomTable) {
        this.discreteRandomTable = discreteRandomTable;
        this.nProbabilityRanges = getProbabilityRanges(discreteRandomTable);
    }

    private static TreeMap<Double, Double> getProbabilityRanges(TreeMap<Integer, BigDecimal> discreteRandomTable) {
        TreeMap<Double, Double> nProbabilityRanges = new TreeMap<>();
        double probabilityStart = 0;
        int rangeNum = 1;
        for (Map.Entry<Integer, BigDecimal> entry: discreteRandomTable.entrySet()){
            double probabilityEnd = BigDecimal.valueOf(probabilityStart).add(entry.getValue()).doubleValue();
            nProbabilityRanges.put(probabilityStart, probabilityEnd);
            System.out.println("Probability range " + rangeNum + ": " + probabilityStart + " " + probabilityEnd);
            probabilityStart = probabilityEnd;
            rangeNum++;
        }
        return nProbabilityRanges;
    }

    public float getNext() {
        double nextBigDecimal = getNextBigDecimal();
        values.add(nextBigDecimal);
        return (float) nextBigDecimal;
    }

    public ArrayList<Double> getValues(int count) {
        for (int i = 0; i < count; i++) {
            float next = this.getNext();
            logger.log(Level.FINE, next + ", ");
        }
        System.out.println();
        return values;
    }

    private double getNextBigDecimal() {
        Random random = new Random();
        double probabilityVal = random.nextDouble();
        Map.Entry<Double, Double> doubleDoubleEntry = nProbabilityRanges.floorEntry(probabilityVal);
        logger.log(Level.FINE, "Selected random " + probabilityVal + " from probability range " +
                doubleDoubleEntry.getKey() + " => " + doubleDoubleEntry.getValue());

        int index = 0;
        for (Map.Entry<Double, Double> entry: nProbabilityRanges.entrySet()){
            if (entry.equals(doubleDoubleEntry))
                break;
            index++;
        }

        List<Map.Entry<Integer, BigDecimal> > entryList
                = new ArrayList<>(discreteRandomTable.entrySet());

        Map.Entry<Integer, BigDecimal> integerBigDecimalEntry = entryList.get(index);

        logger.log(Level.FINE, "Random value is: " + integerBigDecimalEntry.getKey() +
                " of probability " + integerBigDecimalEntry.getValue());

        return integerBigDecimalEntry.getKey();
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

    public TreeMap<Integer, List<Double>> getValuesByIntervals(int intervalCount) {
        return MathHelper.splitValuesByIntervals(values, intervalCount);
    }
}
