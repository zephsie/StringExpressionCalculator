package com.example.stringexpressioncalculator;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnswerFormatTest {
    private IAnswerFormat format;

    @Before
    public void setUp() { format = new AnswerFormat();}

    @Test
    public void Test() {
        double result = 66666666;
        String formResult = format.getFormattedAnswer(result, MainActivity.SPACE);
        Assert.assertEquals(formResult, "6.6666666E7");
    }

    @Test
    public void Test2() {
        double result = 6666666;
        String formResult = format.getFormattedAnswer(result, MainActivity.SPACE);
        Assert.assertEquals(formResult, "6666666");
    }
}