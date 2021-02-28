package calculator;

public class Calculator {

    public static int addUpElemInArray(String[] inputArr) {
        int equation = 0;

        for (String number : inputArr) {
            equation += Integer.parseInt(number);
        }
        return equation;
    }
}
