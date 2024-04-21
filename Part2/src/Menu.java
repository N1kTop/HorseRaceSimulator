import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {


    /**
     *
     * @param message will be printed before scanning for input
     * @return first character of the input in lower case
     */
    public static char inputChar(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine().toLowerCase().charAt(0);
    }

    /**
     *
     * @param message will be printed before scanning for input
     * @return the entered string
     */
    public static String input(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    public static int inputInt(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextInt();
    }

    public static void menu() {
        String message = """

                (h)orses
                (r)ace
                (e)xit
                Enter a letter to choose:\n""";
        char choice;
        while ((choice = inputChar(message)) != 'e') {
            if (choice == 'h') {
                horsesMenu();
            }
            if (choice == 'r') {
                raceMenu();
            }
        }
        System.out.println("\nYou have exited the program");
    }

    public static void listHorses() {
        Horse.printHorses();
        int horseChoice = -1;
        while (horseChoice < 0 || horseChoice >= Horse.getAllHorses().size()) {
            horseChoice = inputInt("Enter horse index: ");
        }
        Horse ChosenHorse = Horse.getHorse(horseChoice);
        ChosenHorse.printHorseInfo();

    }

    public static void createHorse() {
        String name = input("Enter name: ");
        char symbol = inputChar("Enter symbol: ");

        int breedIndex = -1;

        Horse.printBreedChoices();
        while (breedIndex < 0 || breedIndex > Horse.breedChoices.length) {
            breedIndex = inputInt("Enter breed number: ") - 1;
        }

        Horse newHorse = new Horse(symbol, name, 0.5, Horse.breedChoices[breedIndex]);

        Horse.addHorse(newHorse);
        System.out.println("New horse added to the list: " + name + " (" + newHorse.getBreed() + ") " + symbol);
    }

    public static void horsesMenu() {
        Horse.printHorses();
        String message = """

                (l)ist horses
                (c)reate horse
                (e)xit
                Enter a letter to choose:\n""";
        char choice;
        while ((choice = inputChar(message)) != 'e') {
            if (choice == 'c') {
                createHorse();
            }
            else if (choice == 'l') {
                listHorses();
            }
        }
    }

    public static void raceMenu() {
        System.out.println("Race");
    }
}
