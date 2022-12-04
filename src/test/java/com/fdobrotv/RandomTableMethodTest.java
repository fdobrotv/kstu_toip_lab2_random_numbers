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


public class RandomTableMethodTest {
    @Test
    public void generateByDiscreteTableValuesTest() throws InterruptedException {
        TreeMap<Integer, BigDecimal> discreteRandomTable = new TreeMap<>() {{
            put(1, BigDecimal.valueOf(0.02));
            put(10, BigDecimal.valueOf(0.05));
            put(15, BigDecimal.valueOf(0.1));
            put(23, BigDecimal.valueOf(0.28));
            put(29, BigDecimal.valueOf(0.23));
            put(38, BigDecimal.valueOf(0.22));
            put(42, BigDecimal.valueOf(0.1));
        }};

        RandomTableMethod randomTableMethod = new RandomTableMethod(discreteRandomTable);
        for (int i = 0; i < 1000; i++) {
            float next = randomTableMethod.getNext();
            System.out.print(next + ", ");
        }

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
}
