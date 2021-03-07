package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        Pattern pattern = Pattern.compile("[+\\- ]*[0-9]+\\b|[+\\- ]*[a-zA-Z]+\\b");
        Matcher matcher = pattern.matcher(string);   //34 * (43 +++ 54) / 4 ----- 54 -> 34, * (43, +++54), / 4
        int count = 0;

        List<String> outputArr = new ArrayList<>();

        while (matcher.find()) {
            String stringWithoutSpaces = matcher.group().replaceAll("\\s*", ""); // delete all space from " ----+ 23"
            Character operator = getOperator(stringWithoutSpaces); // check what operator the number have
            String stringWithoutOperator = stringWithoutSpaces.replaceAll("[-+]*", "");

            // insert number to an array with proper operator -/+
            if (operator.equals('+')) {
                outputArr.add(stringWithoutOperator); // positive num
            } else if (operator.equals('-')) {
                outputArr.add("-" + stringWithoutOperator); // add minus
            }
        }
        return outputArr;
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

}
