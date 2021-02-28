package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

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
        Pattern pattern = Pattern.compile("[+-]* *[0-9]+\\b");
        Matcher matcher = pattern.matcher(string);
        int count = 0;

        while (matcher.find()) { // count all numbers in string
            count++;
        }
        matcher.reset();
        String[] outputArr = new String[count];
        count = 0;

        while (matcher.find()) {
            String stringWithoutSpaces = matcher.group().replaceAll("\\s*", "");
            Character operator = getOperator(stringWithoutSpaces); // check what operator the number have

            // insert number to an array with a proper operator -/+
            if (operator.equals('+')) {
                outputArr[count] = matcher.group().replaceAll("[+-]*\\s*", "");
            } else if (operator.equals('-')) {
                String output = matcher.group().replaceAll("[-+]*\\s*", "");
                outputArr[count] = "-" + output;
            }

            count++;
        }
        return outputArr;
    }

}
