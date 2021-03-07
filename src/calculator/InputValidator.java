package calculator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private ExceptionHandler exceptionHandler;

    public InputValidator(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public boolean isVariableSetUpFormat(String input) { // check if input is in variable fromat eg. n=10 or n = 10
        return input.matches("[\\p{all}a-zA-Z0-9]+ *= *[\\p{all}a-zA-Z0-9]+ *");
    }

    public boolean isEquationFormat(String input) { // check if input is in good format
        boolean isEq = true;
        String regEx = "[+\\- ]*[0-9]+\\b|[+\\- ]*[a-zA-Z]+\\b";
        Matcher matcherEquation = Pattern.compile(regEx).matcher(input);
        int count = 0;

        if (isEndingWrong(input)) {
            isEq = false;
            this.exceptionHandler.throwInvalidExpression();
        }

        while (matcherEquation.find()) {
            //check if there is no operator between numbers
            if (count > 0 && !isContainingOperator(matcherEquation.group())) {
                isEq = false;
                this.exceptionHandler.throwInvalidExpression();
            }
            count++;
        }
        return isEq;
    }

    private boolean isContainingOperator(String input) {
        boolean containsOperator = false;

        if (input.contains("+") || input.contains("-")
        || input.contains("*") || input.contains("/"))
            containsOperator = true;

        return containsOperator;
    }

    private boolean isEndingWrong(String input) {
        return input.matches("(?i)[a-z0-9+\\- ]*[+\\-=!,.{}:\\\\ ]+");
    }
}
