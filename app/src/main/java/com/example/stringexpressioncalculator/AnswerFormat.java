package com.example.stringexpressioncalculator;

import java.text.DecimalFormat;

/**
 * The class implements the conversion of the result string into a format suitable for output.
 */
public class AnswerFormat implements IAnswerFormat{

    /**
     * Converts the result string to a format suitable for output.
     * @param result the raw output string of the {@linkplain Calculator}.
     * @param space sets the length of the output string.
     * @return Returns the formatted string.
     */
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
