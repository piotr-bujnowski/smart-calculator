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
        String regEx = "\\(?[+\\- ]*[0-9]+ *\\)?|" +
                "\\(?[+\\- ]*[a-zA-Z]+ *\\)?|" +
                "\\(?[*/ ]+[0-9]+ *\\)?|" +
                "\\(?[*/ ]+[a-zA-Z]+ *\\)?";

        Matcher matcherEquation = Pattern.compile(regEx).matcher(input);
        int count = 0;

        if (isEndingWrong(input)) {
            System.out.println("ends worin");
            isEq = false;
            this.exceptionHandler.throwInvalidExpression();
        }

        while (matcherEquation.find()) {
            //check if there is no operator between numbers
            System.out.println(matcherEquation.group());
            if (count > 0 && !isContainingOperator(matcherEquation.group())) {
                System.out.println("op");
                isEq = false;
            } else if (matcherEquation.group().contains("*") && matcherEquation.group().contains("/")) {
                System.out.println("cont");
                isEq = false;
            }

            if (!isEq) exceptionHandler.throwInvalidExpression();

            count++;
        }
        return isEq;
    }

    public static boolean isContainingOperator(String input) {
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
