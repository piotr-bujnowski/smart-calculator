package calculator;

import java.util.List;
import java.util.Map;

public class Calculator {

    public int addUpElemInArray(List<String> inputArr) {
        int equation = 0;

        for (String number : inputArr) {

            equation += Integer.parseInt(number);
        }
        return equation;
    }
}
