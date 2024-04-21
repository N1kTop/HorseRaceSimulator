import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        menu();
    }


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
                (s)tatistics
                (e)xit
                Enter a letter to choose:
                """;
        char choice;
        while ((choice = inputChar(message)) != 'e') {
            if (choice == 'h') {
                horsesMenu();
            }
            if (choice == 'r') {
                raceMenu();
            }
            if (choice == 's') {
                statsMenu();
            }
        }
        Race.saveRecordingNames();
        System.out.println("\nYou have exited the program");
    }

    public static void listHorses() {
        Horse.printHorses();

        int horseChoice = -1;
        while (horseChoice < 0 || horseChoice >= Horse.getAllHorses().size()) {
            horseChoice = inputInt("Enter horse index: ") - 1;
        }
        Horse chosenHorse = Horse.getHorse(horseChoice);
        chosenHorse.printHorseInfo();

        String message = """
                
                You can change horses information or view statistics
                (n)ame
                (s)ymbol
                (c)oat
                (a)ccessory
                (v)iew statistics
                (e)xit
                Enter a letter to choose:
                """;
        char choice;
        while ((choice = inputChar(message)) != 'e') {
            switch (choice) {
                case 'n' -> chosenHorse.setName(input("Enter new name: "));
                case 's' -> chosenHorse.setSymbol(inputChar("Enter new symbol: "));
                case 'c' -> {
                    int colorIndex = -1;
                    Horse.printColorChoices();
                    while (colorIndex < 0 || colorIndex >= Horse.getColorChoicesLength()) {
                        colorIndex = inputInt("Enter color number: ") - 1;
                    }
                    chosenHorse.setCoatColor(Horse.getColorChoice(colorIndex));
                }
                case 'a' -> {
                    int accessoryIndex = -1;
                    Horse.printAccessoryChoices();
                    while (accessoryIndex < 0 || accessoryIndex >= Horse.getAccessoryChoicesLength()) {
                        accessoryIndex = inputInt("Enter accessory number: ") - 1;
                    }
                    chosenHorse.setAccessory(Horse.getAccessoryChoice(accessoryIndex));
                }
                case 'v' -> {
                    chosenHorse.printHorseStats();
                }
            }
            if (choice != 'v') chosenHorse.printHorseInfo();
        }

    }

    public static void createHorse() {
        String name = input("Enter name: ");
        char symbol = inputChar("Enter symbol: ");

        int breedIndex = -1;

        Horse.printBreedChoices();
        while (breedIndex < 0 || breedIndex >= Horse.getBreedChoicesLength()) {
            breedIndex = inputInt("Enter breed number: ") - 1;
        }

        Horse newHorse = new Horse(symbol, name, 0.5, Horse.getBreedChoice(breedIndex));

        Horse.addHorse(newHorse);
        System.out.println("New horse added to the list: " + name + " (" + newHorse.getBreed() + ") " + symbol);
    }

    public static void horsesMenu() {
        String message = """

                (l)ist horses
                (c)reate horse
                (e)xit
                Enter a letter to choose:
                """;
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
        int distance = 0;
        while (distance < 5 || distance > 200) {
            distance = inputInt("Enter race distance (between 5 and 200): ");
        }
        int lanesNum = 0;
        int totalHorseNumber = Horse.getTotalHorseNumber();
        while (lanesNum < 1 || lanesNum > 20 || lanesNum > totalHorseNumber) {
            if (lanesNum > totalHorseNumber) System.out.println("You do not have enough horses for that race\nYou can create more horses in the Menu");
            lanesNum = inputInt("Enter number of lanes (between 1 and 20): ");
        }
        Race newRace = new Race(distance, lanesNum);

        int horseIndex;
        for (int i = 1; i <= lanesNum; i++) {
            Horse.printHorses();
            horseIndex = inputInt("Enter Horse Index for lane " + i + ": ");
            newRace.addHorse(Horse.getHorse(horseIndex - 1), i);
        }
        newRace.startRace();
    }

    public static void statsMenu() {
        Race.printOverallStats();
        String message = """

                (r)ace records
                (e)xit
                Enter a letter to choose:
                """;
        char choice;
        while ((choice = inputChar(message)) != 'e') {
            if (choice == 'r') {
                recordsMenu();
            }

        }
    }

    public static void recordsMenu() {
        if (Race.getNumberOfRecords() == 0) return;

        System.out.println("Race Recordings:\n" + Race.getRecordFileNames());
        String fileName = input("Enter recording name: ");
        Race recordedRace = Race.loadRaceRecord(fileName + ".txt");
        if (recordedRace != null) recordedRace.watchRecording();
    }
}
