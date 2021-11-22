package com.example.stringexpressioncalculator;

import java.util.List;

/**
 * The interface of the {@link ExpressionChecker} class that implements the {@link ExpressionChecker#check} method.
 *
 * {@inheritDoc}
 */
public interface IExpressionChecker {

    boolean check(List<String> inputItems, String currentItem);
}
