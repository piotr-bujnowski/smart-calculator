package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        InputValidator inputValidator = new InputValidator(new ExceptionHandler());
        Map<String, Integer> variablesMap = new HashMap<>();
        Converter converter = new Converter(new ExceptionHandler(), variablesMap);
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
                                    System.out.println("Unknown variable");
                                }
                            }

                        } else if (inputValidator.isEquationFormat(input)) {
                            ArrayList<String> inputArr = new ArrayList<>(converter.changeEquationIntoArray(input));
                            int eq = 0;

                            converter.changeVariablesToNumsInList(inputArr);

                            if (inputArr.size() == 1) {
                                if (variablesMap.containsKey(input) && input.matches("(?i)[a-z]+")) {
                                    System.out.println(variablesMap.get(input));
                                } else if (!variablesMap.containsKey(input)) {
                                    System.out.println("Unknown variable this");
                                } else {
                                    System.out.println(inputArr.get(0));
                                }
                                break;
                            }

//                            for (String num : inputArr) {
//                                eq += Integer.parseInt(num);
//                            }

                            System.out.println(inputArr);
                            System.out.println(eq);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid expression");
                    }
                    break;
            }

        } while (!exit);
    }


}

