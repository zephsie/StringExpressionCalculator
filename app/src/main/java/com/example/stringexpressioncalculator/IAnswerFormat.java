package com.example.stringexpressioncalculator;

/**
 * The interface of the {@link AnswerFormat} class that implements the {@link AnswerFormat#getFormattedAnswer} method.
 *
 * {@inheritDoc}
 */
public interface IAnswerFormat {

    String getFormattedAnswer(double result, int space);
}
