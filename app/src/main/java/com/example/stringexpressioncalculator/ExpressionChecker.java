package com.example.stringexpressioncalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionChecker implements IExpressionChecker {
    public boolean check(List<String> inputItems, String currentItem) {
        if (!inputItems.isEmpty()) {
            currentItem = currentItem
                    .replace("e", "" + Math.E)
                    .replace("π", "" + Math.PI)
                    .replace(",", ".");

            boolean isEOrPI = false;

            if (inputItems.get(inputItems.size() - 1).equals("e")
                    || inputItems.get(inputItems.size() - 1)
                    .replace("π", "pi").equals("pi")) isEOrPI = true;

            StringBuilder items0 = new StringBuilder();

            for (String inputItem : inputItems) {
                items0.append(inputItem);
            }

            String expression = items0.toString()
                    .replace("e", "" + Math.E)
                    .replace("π", "" + Math.PI)
                    .replace(",", ".")
                    .replaceFirst("^-", "0-")
                    .replace("(-", "(0-");

            ArrayList<String> items = new ArrayList<>();

            String regex = "([0-9]+[.]?[0-9]*)|[+\\-*/^()]";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(expression);

            int bktCount = 0;

            while (matcher.find()) {
                items.add(matcher.group());

                if (matcher.group().equals("(")) bktCount++;
                if (matcher.group().equals(")")) bktCount--;
            }

            String lastItem = items.get(items.size() - 1);

            switch (lastItem) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "^": {
                    if (currentItem.matches("[.+\\-*/^)]")) return false;
                    break;
                }
                case "(": {
                    if (currentItem.matches("[.+*/^)]")) return false;
                    break;
                }
                case ")": {
                    if (currentItem.matches("[\\d.(]+")) return false;
                    break;
                }
                case "0": {
                    if (currentItem.matches("\\d")) return false;
                    break;
                }
                default: {
                    if (currentItem.length() > 1 && currentItem.contains(".")) return false;
                    if (lastItem.contains(".") && currentItem.contains(".")) return false;
                    if (lastItem.endsWith(".") && !currentItem.matches("\\d")) return false;
                    if (currentItem.equals("(")) return false;
                    if (isEOrPI && currentItem.matches("\\d")) return false;
                }
            }

            return bktCount != 0 || !currentItem.equals(")");
        } else {
            return !currentItem.matches("[.+*/^)]");
        }
    }
}
