package calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    private final ExceptionHandler exceptionHandler;
    private Map<String, Integer> variablesMap;

    public Converter(ExceptionHandler exceptionHandler, Map<String, Integer> variablesMap) {
        this.exceptionHandler = exceptionHandler;
        this.variablesMap = variablesMap;
    }

    // gets operator from string (eg. ----23 will get +)
    private Character getOperator(String inputArr) {
        Pattern pattern = Pattern.compile("-");
        Matcher matcher = pattern.matcher(inputArr);
        char operator = '\0';
        int minusCount = 0;

        if (inputArr.contains("*")) {
            return '*';
        } else if (inputArr.contains("/")) {
            return '/';
        } else if (!InputValidator.isContainingOperator(inputArr)) {
            return operator;
        }

        while (matcher.find()) {
            minusCount++;
        }

        if (minusCount % 2 == 0) {
            operator = '+';
        } else {
            operator = '-';
        }
        return operator;
    }

    // inserts string into array with separated numbers (eg. ---34 +++ 12 to {-34, 12})
    public List<String> changeEquationIntoArray(String string) {
        String regEx = "[+\\-*/ ]*\\(*?[+\\-*/ ]*[0-9]+ *\\)*|" +
                "[+\\-*/ ]*\\(*?[+\\-*/ ]*[a-zA-Z]+ *\\)*";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(string);
        int count = 0;

        List<String> equation = new ArrayList<>();

        while (matcher.find()) {
            String stringWithoutSpaces = matcher.group().replaceAll("\\s*", ""); // delete all space from " ----+ 23"
            Map<Character, Integer> charactersFreq = new HashMap<>();

            charactersFreq.put('(', 0);

            for (int i = 0; i < stringWithoutSpaces.length(); i++) {
                if (stringWithoutSpaces.charAt(i) == '(') {
                    charactersFreq.put(stringWithoutSpaces.charAt(i), charactersFreq.get(stringWithoutSpaces.charAt(i)) + 1);
                }
            }

            if (stringWithoutSpaces.contains("(")) {
                String[] split = stringWithoutSpaces.split("\\(+");

                Character operator = getOperator(split[0]);
                //Character operator2 = getOperator(split[1]);

                // throw exception when there is +, * or / just after after opening parenthesis
                if (InputValidator.isContainingOperator(split[1])) exceptionHandler.throwInvalidExpression();

                equation
                        .add(operator + "(".repeat(Math.max(0, charactersFreq.get('('))) + split[1].replaceAll("[-+/*]*", ""));

            } else if (stringWithoutSpaces.contains("-") || stringWithoutSpaces.contains("+")) {
                Character operator = getOperator(stringWithoutSpaces); // check what operator the number have
                String stringWithoutOperator = stringWithoutSpaces.replaceAll("[-+]*", "");

                // insert number to an array with proper operator -/+
                if (operator.equals('+'))
                    equation
                            .add("+" + stringWithoutOperator); // positive num
                else if (operator.equals('-'))
                    equation
                            .add("-" + stringWithoutOperator); // add minus

            } else {
                if (stringWithoutSpaces.contains("*")) {
                    stringWithoutSpaces = stringWithoutSpaces.replaceAll("\\*+", "");
                    equation
                            .add("*" + stringWithoutSpaces);
                } else if (stringWithoutSpaces.contains("/")) {
                    stringWithoutSpaces = stringWithoutSpaces.replaceAll("/", "");
                    equation
                            .add("/" + stringWithoutSpaces);
                } else if (!InputValidator.isContainingOperator(stringWithoutSpaces))
                    equation
                            .add(stringWithoutSpaces);
            }
            count++;
        }
        return equation;
    }

    // changes variables into according number if it is assigned earlier to number
    public List<String> changeVariablesToNumsInList(List<String> list) {
        List<String> changedList = new ArrayList<>();

        for (String var : list) {
            for (String varFromMap : variablesMap.keySet()) {
                if (var.replaceAll("-", "").equals(varFromMap)) {
                    String value = Integer.toString(variablesMap.get(varFromMap));

                    if (var.contains("-")) {
                        list.set(list.indexOf(var), "-" + value);
                    } else {
                        list.set(list.indexOf(var), value);
                    }
                }
            }
        }
        return list;
    }

    // converting expression from infix to postfix for easier calculation
    public List<String> convertToPostfixNotation(String string) {
        List<String> infixList = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        Matcher matcher = Pattern.compile("[-+*/()]|[0-9]+").matcher(string);

        while (matcher.find()) {
            String matched = matcher.group();

            if (InputValidator.isContainingOperator(matched)
                    || matched.contains("(") || matched.contains(")")) {
                int getCurrentNumPrecedence = OperatorPrecedence.getPrecedence(matched);

                if (stack.isEmpty() || matched.equals("(")) {
                    stack.offerLast(matched);
                } else if (OperatorPrecedence.getPrecedence(stack.peekLast()) < getCurrentNumPrecedence) {
                    stack.offerLast(matched);
                } else {
                    try {
                        int count = 0;
                        while (OperatorPrecedence.getPrecedence(stack.peekLast()) >= getCurrentNumPrecedence && count < 2) {
                            infixList.add(stack.pollLast());
                            if (stack.getLast().equals("(")) count++;
                        }
                        if (!matched.equals(")")) {
                            stack.offerLast(matched);
                        }
                    } catch (Exception ignored) {
                    }
                }

            } else {
                infixList.add(matched);
            }

        }

        while (!stack.isEmpty()) {
            infixList.add(stack.pollLast());
        }

        String[] ss = {"(", ")"};
        infixList.removeAll(Arrays.asList(ss));

        return infixList;
    }
}
