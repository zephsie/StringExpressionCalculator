package com.example.stringexpressioncalculator;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ALL")
public class ExpressionCheckerTest {
    private IExpressionChecker checker;

    @Before
    public void setUp() { checker = new ExpressionChecker();}

    @Test
    public void onlyDotTest() {
        List<String> inputItems = Collections.EMPTY_LIST;
        boolean isValid = checker.check(inputItems,".");
        Assert.assertFalse(isValid);
    }

    @Test
    public void onlyDivTest() {
        List<String> inputItems = Collections.EMPTY_LIST;
        boolean isValid = checker.check(inputItems,"/");
        Assert.assertFalse(isValid);
    }

    @Test
    public void onlyNumTest() {
        List<String> inputItems = Collections.EMPTY_LIST;
        boolean isValid = checker.check(inputItems,"1");
        Assert.assertTrue(isValid);
    }

    @Test
    public void eTest() {
        List<String> inputItems = Arrays.asList("1","+","e");
        boolean isValid = checker.check(inputItems,"1");
        Assert.assertFalse(isValid);
    }

    @Test
    public void piTest() {
        List<String> inputItems = Arrays.asList("1","+","π");
        boolean isValid = checker.check(inputItems,"1");
        Assert.assertFalse(isValid);
    }

    @Test
    public void powTest1() {
        List<String> inputItems = Arrays.asList("1","+","2","^");
        boolean isValid = checker.check(inputItems,".");
        Assert.assertFalse(isValid);
    }

    @Test
    public void powTest2() {
        List<String> inputItems = Arrays.asList("1","+","2","^");
        boolean isValid = checker.check(inputItems,"2");
        Assert.assertTrue(isValid);
    }

    @Test
    public void dotTest1() {
        List<String> inputItems = Arrays.asList("1","+","e");
        boolean isValid = checker.check(inputItems,".");
        Assert.assertFalse(isValid);
    }

    @Test
    public void dotTest2() {
        List<String> inputItems = Arrays.asList("1","+",".");
        boolean isValid = checker.check(inputItems,".");
        Assert.assertFalse(isValid);
    }

    @Test
    public void bktTest1() {
        List<String> inputItems = Arrays.asList("1","+","(","2","+",")","+");
        boolean isValid = checker.check(inputItems,")");
        Assert.assertFalse(isValid);
    }

    @Test
    public void bktTest2() {
        List<String> inputItems = Arrays.asList("(","1","+","(","2","+","1",")",")");
        boolean isValid = checker.check(inputItems,")");
        Assert.assertFalse(isValid);
    }

    @Test
    public void bktTest3() {
        List<String> inputItems = Arrays.asList("(","1","+","(");
        boolean isValid = checker.check(inputItems,"+");
        Assert.assertFalse(isValid);
    }

    @Test
    public void bktTest4() {
        List<String> inputItems = Arrays.asList("(","1","+",")");
        boolean isValid = checker.check(inputItems,"(");
        Assert.assertFalse(isValid);
    }

    @Test
    public void bktTest5() {
        List<String> inputItems = Arrays.asList("(","1","+","(");
        boolean isValid = checker.check(inputItems,"2");
        Assert.assertTrue(isValid);
    }

    @Test
    public void zeroTest1() {
        List<String> inputItems = Arrays.asList("1","+","0");
        boolean isValid = checker.check(inputItems,"1");
        Assert.assertFalse(isValid);
    }

    @Test
    public void zeroTest2() {
        List<String> inputItems = Arrays.asList("1","+","0");
        boolean isValid = checker.check(inputItems,"+");
        Assert.assertTrue(isValid);
    }

    @Test
    public void Test8() {
        List<String> inputItems = Arrays.asList("1","+","e");
        boolean isValid = checker.check(inputItems,"e");
        Assert.assertFalse(isValid);
    }

    @Test
    public void Test9() {
        List<String> inputItems = Arrays.asList("1","+","1");
        boolean isValid = checker.check(inputItems,"e");
        Assert.assertFalse(isValid);
    }

}