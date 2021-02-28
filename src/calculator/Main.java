package calculator;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Converter converter = new Converter(new ExceptionHandler());
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

                    // convert numbers with proper operator and insert them into array
                    try {
                        String[] inputInArr = converter.changeEquationToArray(input);
                        System.out.println(Calculator.addUpElemInArray(inputInArr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid expression");
                    }
                    break;
            }

        } while (!exit);
    }


}

