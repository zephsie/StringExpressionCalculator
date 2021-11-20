package com.example.stringexpressioncalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void calculateTest() throws Exception {
        double result = calculator.calculate("2+2");
        Assert.assertEquals(4, result, 0.1);
    }



}