package com.example.stringexpressioncalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorTest {
    private ICalculator calculator;

    @Before
    public void setUp() { calculator = new Calculator(); }

    @Test
    public void calculateTest() throws CalculatorException {
        double result = calculator.calculate("(((((5.434*32^1.2-(76*(23.3-533.24^0.5*43)/43)*3.43*(((42/43.5454-45.433^2.3)*24-4356)*65/8675))*2/45/(-54567)*5/456*754*(4*(343+54)^3)-4)/57^4.5-46.565^4)/(-555.34)^3)*32)*2.543786^3.38");
        Assert.assertEquals(20.6, result, 0.1);
    }

    @Test(expected = CalculatorException.class)
    public void zeroTest() throws CalculatorException {
        double result = calculator.calculate("20/0");
    }

    @Test(expected = CalculatorException.class)
    public void bracket1Test() throws CalculatorException {
        double result = calculator.calculate("(2+2");
    }

    @Test(expected = CalculatorException.class)
    public void bracket2Test() throws CalculatorException {
        double result = calculator.calculate("2+2)");
    }

    @Test(expected = CalculatorException.class)
    public void Test() throws CalculatorException {
        double result = calculator.calculate("2+2+");
    }

    @Test(expected = CalculatorException.class)
    public void emptyTest() throws CalculatorException {
        double result = calculator.calculate(" ");
    }
}