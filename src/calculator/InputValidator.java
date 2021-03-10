package calculator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private ExceptionHandler exceptionHandler;

    public InputValidator(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public boolean isVariableSetUpFormat(String input) { // check if input is in variable format eg. n=10 or n = 10
        return input.matches("[\\p{all}a-zA-Z0-9]+ *= *[\\p{all}a-zA-Z0-9]+ *");
    }

    public boolean isEquationFormat(String input) { // check if input is in good format
        boolean isEq = true;
        String regEx = "[+\\-*/ ]*\\(*?[+\\-*/ ]*[0-9]+ *\\)*|" +
                "[+\\-*/ ]*\\(*?[+\\-*/ ]*[a-zA-Z]+ *\\)*";
        // regex for checking if there are chunks with more than one * or /
        String regExMoreThanOne = "[*]{2,}|[/]{2,}";

        Matcher matcherEquation = Pattern.compile(regEx).matcher(input);
        Matcher matcherMoreThanOne = Pattern.compile(regExMoreThanOne).matcher(input);

        if (matcherMoreThanOne.find()) exceptionHandler.throwInvalidExpression();

        int count = 0;

        if (isEndingWrong(input)) {
            isEq = false;
            this.exceptionHandler.throwInvalidExpression();
        }

        while (matcherEquation.find()) {
            //check if there is no operator between numbers
            if (count > 0 && !isExpressionValid(matcherEquation.group())) {
                isEq = false;
                exceptionHandler.throwInvalidExpression();
            }

            count++;
        }
        return isEq;
    }

    // checks if divided input is correct example: (+++--23 or ***45 -> good), (/*54 or ++/* 90 -> wrong)
    private boolean isExpressionValid(String input) {
        Matcher matcher = Pattern.compile("[*/]").matcher(input);

        if (!isContainingOperator(input))  return false;
        else if (input.contains("*") && input.contains("/")) return false;
        else if ((input.contains("+") || input.contains("-")) && matcher.find() && !input.contains("("))  return false;

        return true;
    }

    public static boolean isContainingOperator(String input) {
        boolean containsOperator = false;

        if (input.contains("+") || input.contains("-")
        || input.contains("*") || input.contains("/"))
            containsOperator = true;

        return containsOperator;
    }

    private boolean isEndingWrong(String input) {
//        Matcher matcher = Pattern.compile("[+\\-=!,.{}:\\\\*/ ]\\b").matcher(input);
        return input.matches(".*[+\\-=!,.{}:\\\\*/ ]\\b");
    }
}
