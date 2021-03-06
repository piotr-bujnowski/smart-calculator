package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {

        InputValidator inputValidator = new InputValidator(new ExceptionHandler());
        Map<String, BigInteger> variablesMap = new HashMap<>();
        Converter converter = new Converter(new ExceptionHandler(), variablesMap);
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        Printer.printMenu();

        do {
            BigInteger equation = BigInteger.ZERO;
            System.out.print("\n>>");
            String input = scanner.nextLine();

            switch (input) {
                case "/exit":
                    exit = true;
                    Printer.printBye();
                    break;
                case "/help":
                    Printer.printHelp();
                    break;
                case "/variables":
                    if (!variablesMap.isEmpty()) {
                        for (String var : variablesMap.keySet()) {
                            System.out.println(var + " = " + variablesMap.get(var));
                        }
                    } else {
                        Printer.noVariables();
                    }
                    break;
                case "/menu":
                    Printer.printMenu();
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

                            String regExInvalidId = "(?i)[\\p{L}a-z]+\\d+|\\d+[\\p{L}a-z]|[^0-9a-z]+";
                            String regExInvalidAssignment = "(?i)[\\p{L}a-z]+\\d+|\\d+[\\p{L}a-z]|[^0-9a-z-]+";
                            Matcher matcherInvalidId = Pattern.compile(regExInvalidId).matcher(variable[0]);
                            Matcher matcherInvalidAssignment = Pattern.compile(regExInvalidAssignment).matcher(variable[1]);

                            if (matcherInvalidId.find()) {
                                System.out.println("Invalid identifier");
                                continue;
                            } else if (matcherInvalidAssignment.find() || variable.length > 2) {
                                System.out.println("Invalid assignment");
                                continue;
                            } else {
                                if (variablesMap.containsKey(variable[1])) {
                                    variablesMap.put(variable[0], variablesMap.get(variable[1]));
                                } else if (variable[0].matches("(?i)[a-z]+ *") && variable[1].matches("-?[0-9]+ *")) {
                                    variablesMap.put(variable[0], new BigInteger(variable[1]));
                                } else {
                                    System.out.println("Unknown variable this");
                                }
                            }

                        } else if (inputValidator.isEquationFormat(input)) {
                            int eq = 0;

                            List<String> infixNotation = new ArrayList<>(converter.changeEquationIntoArray(input));
                            List<String> infixConvertedVariables = new ArrayList<>(converter.changeVariablesToNumsInList(infixNotation));
                            List<String> postfixNotation = new ArrayList<>(converter.convertToPostfixNotation(infixConvertedVariables.toString()));

                            // printing infix and postfix for info
//                            System.out.println("Infix: " + infixNotation);
//
//                            System.out.print("Postfix: ");
//                            for (String s : postfixNotation) {
//                                System.out.print(s + " ");
//                            }
//                            System.out.println();

                            if (infixNotation.size() == 1) {
                                String inputNoSpaces = input.replaceAll("\\s*", "");

                                if (inputNoSpaces.matches("(?i)[-+]*[0-9]+")) {
                                    System.out.println(infixNotation.get(0));
                                    continue;
                                }

                                if (variablesMap.containsKey(inputNoSpaces) && inputNoSpaces.matches("(?i)[a-z]+")) {
                                    System.out.println(variablesMap.get(inputNoSpaces));
                                } else {
                                    System.out.println("Unknown variable");
                                    continue;
                                }
                                break;
                            }

                            equation = calculator.calculatePostfixExp(postfixNotation);
                            if (equation == null) throw new NumberFormatException();
                            System.out.println(equation);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid expression");
                    }
                    break;
            }

        } while (!exit);
    }


}

