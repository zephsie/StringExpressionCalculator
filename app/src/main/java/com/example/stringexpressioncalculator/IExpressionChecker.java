package com.example.stringexpressioncalculator;

import java.util.List;

/**
 * The interface Expression checker.
 */
public interface IExpressionChecker {
    /**
     * Check boolean.
     *
     * @param inputItems  the input items
     * @param currentItem the current item
     * @return the boolean
     */
    boolean check(List<String> inputItems, String currentItem);
}
