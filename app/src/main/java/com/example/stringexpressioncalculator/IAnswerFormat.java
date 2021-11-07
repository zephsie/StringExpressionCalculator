package com.example.stringexpressioncalculator;

/**
 * The interface Answer format.
 */
public interface IAnswerFormat {
    /**
     * Gets formatted answer.
     *
     * @param result the result
     * @param space  the space
     * @return the formatted answer
     */
    String getFormattedAnswer(double result, int space);
}
