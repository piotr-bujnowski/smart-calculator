package calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        InputValidator inputValidator = new InputValidator(new ExceptionHandler());
        Map<String, Integer> variablesMap = new HashMap<>();
        Converter converter = new Converter(new ExceptionHandler(), variablesMap);
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        do {
            int equation = 0;
            String input = scanner.nextLine();

            switch (input) {
                case "/exit":
                    exit = true;
                    Printer.printBye();
                    break;
                case "/help":
                    Printer.printHelp();
                    break;
                case "":
                    continue;
                default:
                    if (input.startsWith("/")) {
                        System.out.println("Unknown command");
                        break;
                    }

                    try {

                        if (inputValidator.isVariableSetUpFormat(input)) {
                            String[] variable = input.replaceAll("\\s*", "").split("=");

                            String regEx = "(?i)[\\p{L}a-z]+\\d+|\\d+[\\p{L}a-z]|[^0-9a-z]+";
                            Matcher matcherInvalidId = Pattern.compile(regEx).matcher(variable[0]);
                            Matcher matcherInvalidAssignment = Pattern.compile(regEx).matcher(variable[1]);

                            if (matcherInvalidId.find()) {
                                System.out.println("Invalid identifier");
                                continue;
                            } else if (matcherInvalidAssignment.find()) {
                                System.out.println("Invalid assignment");
                                continue;
                            } else {
                                if (variablesMap.containsKey(variable[1])) {
                                    variablesMap.put(variable[0], variablesMap.get(variable[1]));
                                } else if (variable[0].matches("(?i)[a-z]+ *") && variable[1].matches("[0-9]+ *")) {
                                    variablesMap.put(variable[0], Integer.parseInt(variable[1]));
                                } else {
                                    System.out.println("Unknown variable this");
                                }
                            }

                        } else if (inputValidator.isEquationFormat(input)) {
                            String[] inputArr = input.replaceAll("\\s*", "").split("");
                            int eq = 0;

                            List<String> infixNotation = new ArrayList<>(converter.changeEquationIntoArray(input));
                            List<String> infixConverted = new ArrayList<>(converter.changeVariablesToNumsInList(infixNotation));
                            List<String> postfixNotation = new ArrayList<>(converter.convertToPostfixNotation(infixConverted.toString()));

                            System.out.println("Infix: " + infixNotation);

                            System.out.print("Postfix: ");
                            for (String s : postfixNotation) {
                                System.out.print(s + " ");
                            }

                            if (infixNotation.size() == 1) {
                                if (variablesMap.containsKey(input) && input.matches("(?i)[a-z]+")) {
                                    System.out.println(variablesMap.get(input));
                                } else if (!variablesMap.containsKey(input)) {
                                    System.out.println("Unknown variable this");
                                } else {
                                    System.out.println(infixNotation.get(0));
                                }
                                break;
                            }

                            try {
                                System.out.println();
                                equation = calculator.calculatePostfixExp(postfixNotation);
                                System.out.println("Result: " + equation);
                            } catch (Exception e) {
                                System.out.println("Something went horribly wrong");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid expression");
                    }
                    break;
            }

        } while (!exit);
    }


}

