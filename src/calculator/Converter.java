package calculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    private final ExceptionHandler exceptionHandler;

    public Converter(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    // gets operator from string (eg. ----23 will get +)
    private Character getOperator(String inputArr) {
        Pattern pattern = Pattern.compile("-");
        Matcher matcher = pattern.matcher(inputArr);
        char operator = '\0';
        int minusCount = 0;

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
    public String[] changeEquationToArray(String string) {
        Pattern pattern = Pattern.compile("[+\\- ]*[0-9]+\\b");
        Matcher matcher = pattern.matcher(string);
        int count = 0;

        if (!isInputValid(string)) {
            exceptionHandler.throwInvalidExpression();
        }

        while (matcher.find()) { // count all numbers in string
            if (count > 0 && !matcher.group().matches("\\s*[+\\-]+\\s*[0-9]+")) { // if there is no operator between numbers
                exceptionHandler.throwInvalidExpression();
            }
            count++;
        }
        matcher.reset();
        String[] outputArr = new String[count];
        count = 0;

        while (matcher.find()) {
            String stringWithoutSpaces = matcher.group().replaceAll("\\s*", ""); // delete all space from " ----+ 23"
            Character operator = getOperator(stringWithoutSpaces); // check what operator the number have
            String stringWithoutOperator = stringWithoutSpaces.replaceAll("[-+]*", "");

            // insert number to an array with proper operator -/+
            if (operator.equals('+')) {
                outputArr[count] = stringWithoutOperator; // positive num
            } else if (operator.equals('-')) {

                outputArr[count] = "-" + stringWithoutOperator; // add minus
            }

            count++;
        }
        return outputArr;
    }

    private boolean isInputValid(String string) {
        Matcher matcherCheckIfLetters = Pattern.compile("[!-'*,/:-~]").matcher(string);
        boolean isValid = true;

        if (matcherCheckIfLetters.find()) { // check if there are letters or other non numeric
            isValid = false;
        } else if (string.endsWith(" ") || string.endsWith("+") || string.endsWith("-")) { // does input ends with -/+/space
            isValid = false;
        }
        return isValid;
    }

}
