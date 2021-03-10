package calculator;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Calculator {

    public BigInteger calculatePostfixExp(List<String> list) throws Exception {
        Deque<BigInteger> stack = new ArrayDeque<>();
        BigInteger equation = null;

        try {
            for (String s : list) {
                if (isBigInteger(s)) {
                    stack.offerLast(new BigInteger(s));
                } else {
                    if (stack.size() >= 2) {
                        BigInteger firstNum = new BigInteger(String.valueOf(stack.pollLast()));
                        BigInteger secondNum = new BigInteger(String.valueOf(stack.pollLast()));

                        switch (s) {
                            case "+":
                                equation = (secondNum).add(firstNum);
                                break;
                            case "-":
                                equation = (secondNum).subtract(firstNum);
                                break;
                            case "*":
                                equation = (secondNum).multiply(firstNum);
                                break;
                            case "/":
                                equation = (secondNum).divide(firstNum);
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

    public boolean isBigInteger(String s) {
        try {
            new BigInteger(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
