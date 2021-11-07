package com.example.stringexpressioncalculator;

/**
 * The interface Calculator.
 */
public interface ICalculator {
    /**
     * Calculate double.
     *
     * @param expression the expression
     * @return the double
     * @throws CalculatorException the calculator exception
     */
    double calculate(String expression) throws CalculatorException;
}
