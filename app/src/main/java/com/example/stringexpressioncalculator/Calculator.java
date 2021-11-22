package com.example.stringexpressioncalculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class implements the calculation of the value of an expression.
 */
public class Calculator implements ICalculator {

    /**
     * The value of the output string in case of incorrect input.
     */
    private static final String INVALID_INPUT = "Invalid input";

    /**
     * The value of the output string when you enter an expression containing division by zero.
     */
    private static final String DIV_BY_ZERO = "Divided by zero";

    /**
     * Calculates the value of expression and returns result.
     * @param expression the expression taken as {@linkplain String}.
     * @return Returns the calculated value of the expression or {@value INVALID_INPUT} or {@value DIV_BY_ZERO} in the case of invalid input.
     * @throws CalculatorException if the input is invalid.
     */
    public double calculate(String expression) throws CalculatorException {
        expression = expression
                .replaceFirst("^-", "0-")
                .replace("(-", "(0-");

        double result;

        try {
            result = getAnswer(getReversePolishNotation(getItems(expression)));
        } catch (CalculatorException e) {
            throw new CalculatorException(e.getMessage());
        } catch (Exception e) {
            throw new CalculatorException(INVALID_INPUT);
        }

        return result;
    }

    /**
     * Converts the input string of an expression into an array {@linkplain ArrayList} of operators, operands, and brackets using regular expressions.
     * @param expression the expression taken as {@link String}
     * @return Returns an expression as an {@linkplain ArrayList}, the elements of which are operators, operands, and brackets.
     * @see ArrayList
     * @see java.util.regex
     */
    private ArrayList<String> getItems(String expression) {
        ArrayList<String> items = new ArrayList<>();

        String regex = "([0-9]+[.]?[0-9]*)|[+\\-*/^()]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()){
            items.add(matcher.group());
        }

        return items;
    }

    /**
     * Converts an array {@linkplain ArrayList} obtained from {@link Calculator#getItems} into an expression in the form of Reverse Polish Notation.
     * @param items expression in the infix notation as an array {@linkplain ArrayList} obtained in {@link Calculator#getItems}.
     * @return Returns the expression in the form of Reverse Polish Notation as an {@linkplain ArrayList}.
     * @see <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Reverse Polish notation</a>
     */
    private ArrayList<String> getReversePolishNotation(ArrayList<String> items) {
        Deque<String> stack = new ArrayDeque<>();

        ArrayList<String> itemsRPN = new ArrayList<>();

        for (String item : items) {
            int itemPrior = getPriority(item);

            switch (itemPrior) {
                case 0:
                    itemsRPN.add(item);
                    break;
                case 4:
                    stack.push(item);
                    break;
                case 5:
                    while (getPriority(Objects.requireNonNull(stack.peek())) != 4) {
                        itemsRPN.add(stack.pop());
                    }

                    stack.pop();
                    break;
                default:
                    if (!stack.isEmpty()) {
                        boolean isPop;

                        do {
                            isPop = false;

                            int latestItemPrior = getPriority(Objects.requireNonNull(stack.peek()));

                            if (latestItemPrior >= itemPrior && latestItemPrior < 4) {
                                itemsRPN.add(stack.pop());

                                isPop = true;
                            }
                        } while (isPop && !stack.isEmpty());

                    }

                    stack.push(item);
                    break;
            }
        }

        if (!stack.isEmpty()) {
            do {
                itemsRPN.add(stack.pop());
            } while (!stack.isEmpty());
        }

        return itemsRPN;
    }

    /**
     * Calculates expression values in Reverse Polish Notation.
     * @param itemsRPN the expression in the form of Reverse Polish Notation as an {@linkplain ArrayList} derived from the method {@linkplain Calculator#getReversePolishNotation}
     * @return Returns the result of the calculation or {@value INVALID_INPUT} if the input is invalid.
     * @throws CalculatorException if there is an unnecessary bracket.
     */
    private double getAnswer(ArrayList<String> itemsRPN) throws CalculatorException {
        if (itemsRPN.contains("(")) {
            throw new CalculatorException(INVALID_INPUT);
        }

        double temp = 0;

        int i = 0;

        while (i < itemsRPN.size()) {
            boolean isOperator = true;

            switch (itemsRPN.get(i)) {
                case "+": {
                    temp = Double.parseDouble(itemsRPN.get(i - 2)) + Double.parseDouble(itemsRPN.get(i - 1));
                    break;
                }
                case "-": {
                    temp = Double.parseDouble(itemsRPN.get(i - 2)) - Double.parseDouble(itemsRPN.get(i - 1));
                    break;
                }
                case "*": {
                    temp = Double.parseDouble(itemsRPN.get(i - 2)) * Double.parseDouble(itemsRPN.get(i - 1));
                    break;
                }
                case "/": {
                    if (Double.parseDouble(itemsRPN.get(i - 1)) == 0) {
                        throw new CalculatorException(DIV_BY_ZERO);
                    }

                    temp = Double.parseDouble(itemsRPN.get(i - 2)) / Double.parseDouble(itemsRPN.get(i - 1));
                    break;
                }
                case "^": {
                    temp = Math.pow(Double.parseDouble(itemsRPN.get(i - 2)), Double.parseDouble(itemsRPN.get(i - 1)));
                    break;
                }
                default: isOperator = false;
            }

            if (isOperator) {
                itemsRPN.remove(i - 2);
                itemsRPN.remove(i - 2);
                itemsRPN.set(i - 2, "" + temp);

                i = 0;
            } else {
                i++;
            }
        }

        if (itemsRPN.size() > 1) {
            throw new CalculatorException(INVALID_INPUT);
        }

        return Double.parseDouble(itemsRPN.get(0));
    }

    /**
     * Returns the priority of the symbols from the expression for Reverse Polish Notation.
     * @param item symbol.
     * @return Returns the priority of the symbol.
     */
    private static int getPriority(String item) {
        int priority = 0;

        switch (item) {
            case ")": {
                priority = 5;
                break;
            }
            case "(": {
                priority = 4;
                break;
            }
            case "^": {
                priority = 3;
                break;
            }
            case "*":
            case "/": {
                priority = 2;
                break;
            }
            case "+":
            case "-": {
                priority = 1;
                break;
            }
            default: {
                break;
            }
        }

        return priority;
    }
}
