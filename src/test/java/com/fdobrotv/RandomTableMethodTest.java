package com.fdobrotv;

import com.fdobrotv.methods.RandomTableMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.fdobrotv.MockData.getDiscreteRandomTable;


public class RandomTableMethodTest {
    @Test
    public void generate1000ValuesTest() throws InterruptedException {
        TreeMap<Integer, BigDecimal> discreteRandomTable = getDiscreteRandomTable();

        RandomTableMethod randomTableMethod = new RandomTableMethod(discreteRandomTable);
        randomTableMethod.getValues(1000);

        //Get table math expectation
        BigDecimal theoreticalMathematicalExpectation = MathHelper.getMathematicalExpectation(discreteRandomTable);
        System.out.println("Theoretical math expectation is: " + theoreticalMathematicalExpectation);
        double mathematicalExpectation = randomTableMethod.getMathematicalExpectation();
        System.out.println("Math expectation is: " + mathematicalExpectation);

        //Get table dispersion
        BigDecimal theoreticalDispersion = MathHelper.getDispersion(discreteRandomTable);
        System.out.println("Theoretical dispersion is: " + theoreticalDispersion);
        double dispersion = randomTableMethod.getDispersion();
        System.out.println("Dispersion is: " + dispersion);

        //Get frequency table and show on the screen
        int intervalCount = 10;
        randomTableMethod.printFrequencyTable(intervalCount);

        Map<Integer, List<Double>> valuesByIntervals = randomTableMethod.getValuesByIntervals(intervalCount);
        GraphHelper.showHistogram(valuesByIntervals);

        CountDownLatch siteWasRenderedLatch = new CountDownLatch(1);
        siteWasRenderedLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void mathematicalExpectationOfTableTest() {
        BigDecimal probability = BigDecimal.ONE.divide(BigDecimal.valueOf(6), 8, RoundingMode.HALF_UP);
        TreeMap<Integer, BigDecimal> discreteRandomTable = new TreeMap<>();
        IntStream.rangeClosed(1, 6).forEach(i -> discreteRandomTable.put(i, probability));
        BigDecimal mathematicalExpectation = MathHelper.getMathematicalExpectation(discreteRandomTable)
                .setScale(1, RoundingMode.HALF_UP);
        BigDecimal expected = BigDecimal.valueOf(3.5);
        Assertions.assertEquals(expected, mathematicalExpectation);
    }

    @Test
    public void dispersionOfTableTest() {
        BigDecimal probability = BigDecimal.ONE.divide(BigDecimal.valueOf(6), 8, RoundingMode.HALF_UP);
        TreeMap<Integer, BigDecimal> discreteRandomTable = new TreeMap<>();
        IntStream.rangeClosed(1, 6).forEach(i -> discreteRandomTable.put(i, probability));
        BigDecimal dispersion = MathHelper.getDispersion(discreteRandomTable);
        double expected = 2.917;
        double roundedDispersion =
                dispersion.setScale(3, RoundingMode.HALF_UP).doubleValue();
        Assertions.assertEquals(expected, roundedDispersion);
    }

    @Test
    public void splitValuesByIntervalsTest() {
        int intervalCount = 10;
        List<Double> integers = new RandomTableMethod(MockData.getDiscreteRandomTable()).getValues(1000);
        Map<Integer, List<Double>> integerListMap = MathHelper.splitValuesByIntervals(integers, intervalCount);
        Assertions.assertEquals(10, integerListMap.size());
        Integer sumOfRandomNumbers = integerListMap.values().stream().map(List::size).reduce(0, Integer::sum);
        Assertions.assertEquals(1000, sumOfRandomNumbers);
    }
}
