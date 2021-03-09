package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Calculator {

    public int calculatePostfixExp(List<String> list) throws Exception {
        Deque<Integer> stack = new ArrayDeque<>();
        int equation = 0;

        try {


            for (String s : list) {
                if (isInteger(s)) {
                    stack.offerLast(Integer.parseInt(s));
                } else {
                    if (stack.size() >= 2) {
                        int firstNum = stack.pollLast();
                        int secondNum = stack.pollLast();

                        switch (s) {
                            case "+":
                                equation = secondNum + firstNum;
                                break;
                            case "-":
                                equation = secondNum - firstNum;
                                break;
                            case "*":
                                equation = secondNum * firstNum;
                                break;
                            case "/":
                                equation = secondNum / firstNum;
                                break;

                        }

                        stack.offerLast(equation);
                    }
                }
            }
            return stack.peek();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public int addUpElemInArray(List<String> inputArr) {
        int equation = 0;

        for (String number : inputArr) {

            equation += Integer.parseInt(number);
        }
        return equation;
    }
}
