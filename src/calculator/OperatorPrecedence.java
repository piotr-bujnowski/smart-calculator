package calculator;

public class OperatorPrecedence {

    public static int getPrecedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }

        if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }

        if (operator.equals("(") || operator.equals(")")) {
            return 0;
        }
        return -1; // if given wrong operator
    }
}
