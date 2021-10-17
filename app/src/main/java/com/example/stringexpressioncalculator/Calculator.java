package com.example.stringexpressioncalculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator implements ICalculator {
    public double calculate(String expression) throws CalculatorException, DivideByZeroException {
        expression = expression
                .replaceFirst("^-", "0-")
                .replace("(-", "(0-");

        double result;

        try {
            result = getAnswer(getReversePolishNotation(getItems(expression)));
        } catch (DivideByZeroException e) {
            throw new DivideByZeroException();
        } catch (Exception e) {
            throw new CalculatorException();
        }

        return result;
    }

    private ArrayList<String> getItems(String expression) {
        ArrayList<String> items = new ArrayList<>();

        String regex = "([0-9]+[.]?[0-9]*)|[+\\-*^/()]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()){
            items.add(matcher.group());
        }

        return items;
    }

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

    private double getAnswer(ArrayList<String> itemsRPN) throws CalculatorException, DivideByZeroException {
        if (itemsRPN.contains("(")) {
            throw new CalculatorException();
        }

        double temp;

        while (itemsRPN.size() > 1) {
            for (int i = 0; i < itemsRPN.size(); i++) {
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
                            throw new DivideByZeroException();
                        }

                        temp = Double.parseDouble(itemsRPN.get(i - 2)) / Double.parseDouble(itemsRPN.get(i - 1));
                        break;
                    }
                    case "^": {
                        temp = Math.pow(Double.parseDouble(itemsRPN.get(i - 2)), Double.parseDouble(itemsRPN.get(i - 1)));
                        break;
                    }
                    default: continue;
                }

                itemsRPN.remove(i - 2);
                itemsRPN.remove(i - 2);
                itemsRPN.set(i - 2, "" + temp);

                break;
            }
        }

        return Double.parseDouble(itemsRPN.get(0));
    }

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
