package com.example.stringexpressioncalculator;

import java.text.DecimalFormat;

public class AnswerFormat implements IAnswerFormat{
    public String getFormattedAnswer(double result, int space) {
        String outputResult;

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(false);

        int indexE = String.valueOf(result).lastIndexOf("E");

        String partWithE = indexE > -1 ? String.valueOf(result).substring(indexE) : "";
        String partWithoutE = indexE > -1 ? String.valueOf(result).substring(0, indexE) : String.valueOf(result);

        int partWithoutEBeforePointLength = String.valueOf(Math.round(Double.parseDouble(partWithoutE))).length();

        decimalFormat.setMaximumFractionDigits(space - partWithoutEBeforePointLength - 1 - partWithE.length());

        outputResult = decimalFormat.format(Double.parseDouble(partWithoutE)).replace(",", ".");

        outputResult += partWithE;

        return outputResult;
    }
}
