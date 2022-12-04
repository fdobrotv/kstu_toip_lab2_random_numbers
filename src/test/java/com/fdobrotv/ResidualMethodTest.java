package com.fdobrotv;

import com.fdobrotv.methods.ResidualMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class ResidualMethodTest {
    @Test
    public void generate1000ValuesTest() throws InterruptedException {
        ResidualMethod residualMethod = new ResidualMethod();
        for (int i = 0; i < 1000; i++) {
            float next = residualMethod.getNext();
            System.out.print(next + ", ");
        }
        System.out.println();
        System.out.println("Mathematical expectation: " + residualMethod.getMathematicalExpectation());
        System.out.println("Dispersion: " + residualMethod.getDispersion());

        int intervalCount = 10;
        residualMethod.printFrequencyTable(intervalCount);

        Map<Integer, List<Double>> valuesByIntervals = residualMethod.getValuesByIntervals(intervalCount);
        GraphHelper.showHistogram(valuesByIntervals);

        CountDownLatch siteWasRenderedLatch = new CountDownLatch(1);
        siteWasRenderedLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void mathematicalExpectationTest() {
        List<Double> values = List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        double mathematicalExpectation = MathHelper.getMathematicalExpectation(values);
        double expected = 3.5;
        Assertions.assertEquals(expected, mathematicalExpectation);
    }

    @Test
    public void dispersionTest() {
        List<Double> values = List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        double dispersion = MathHelper.getDispersion(values);
        double expected = 2.917;
        double roundedDispersion =
                BigDecimal.valueOf(dispersion).setScale(3, RoundingMode.HALF_UP).doubleValue();
        Assertions.assertEquals(expected, roundedDispersion);
    }
}
