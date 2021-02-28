package calculator;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Converter converter = new Converter();
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
                    // converted numbers with proper operator
                    String[] inputInArr = converter.changeEquationToArray(input);
                    for (String number : inputInArr) {
                        equation += Integer.parseInt(number);
                    }
                    System.out.println(equation);
                    break;
            }

        } while (!exit);
    }


}

