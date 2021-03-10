package calculator;

public class Printer {

    public static void printHelp() {
        System.out.println("\nProgram supports only natural numbers for this moment\n" +
                " and [-, +, *, /] operators\n" +
                " --- Input / Output examples: ---\n" +
                " 3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)\n" +
                " 121\n\n" +
                " 8 * 3 + 12 * (4 - 2)\n" +
                " 48\n\n" +
                " 2 - 2 + 3\n" +
                " 3\n\n" +
                " 4 * (2 + 3\n" +
                " Invalid expression\n\n" +
                " -10\n" +
                " -10\n\n" +
                " veryBig = 80000000000000000000000000000\n" +
                " BIG = 100000000000000000000000\n" +
                " a=-4\n" +
                " b=5\n" +
                " c=6\n\n" +
                " a\n" +
                " -4\n\n" +
                " 123abc = 23\n" +
                " Invalid Identifier\n\n" +
                " abc = ewq432d\n" +
                " Invalid assignment\n\n" +
                " abc = 12 - 2 = 32\n" +
                " Invalid assignment\n\n" +
                " a*2+b*3+c*(2+3)\n" +
                " 53\n\n" +
                " 1 +++ 2 * 3 -- 4\n" +
                " 11\n\n" +
                " 3 *** 5\n" +
                " Invalid expression\n\n" +
                " 4+3)\n" +
                " Invalid expression\n");
    }

    public static void printMenu() {
        System.out.println("--- Smart Calculator Program ---\n" +
                " /help      : more information\n" +
                " /variables : print entered variables\n" +
                " /menu      : print menu\n" +
                " /exit      : exit program");
    }

    public static void noVariables() {
        System.out.println("\nNo variables have been entered yet\n" +
                " Please enter variables in this format first:\n" +
                " n=13");
    }

    public static void printBye() {
        System.out.println("Bye");
    }

}
