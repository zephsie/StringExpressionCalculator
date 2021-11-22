package com.example.stringexpressioncalculator;

/**
 * The interface of the {@link Calculator} class that implements the {@link Calculator#calculate} method.
 *
 * {@inheritDoc}
 */
public interface ICalculator {

    double calculate(String expression) throws CalculatorException;
}
