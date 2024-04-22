import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
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
    public static char inputCharLowerCase(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine().toLowerCase().charAt(0);
    }

    public static char inputChar(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine().charAt(0);
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
        try {
            return new Scanner(System.in).nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input\n");
            return -1;
        }
    }

    public static void menu() {
        String message = """
                
                ---Main Menu---
                (1) Race
                (2) Horses Stable
                (3) Statistics
                (4) Shop
                (5) Settings
                (0) Exit
                Enter a number to choose:
                """;
        char choice;
        while ((choice = inputCharLowerCase(message)) != '0') {
            switch (choice) {

                case '1' -> raceMenu();
                case '2' -> horsesMenu();
                case '3' -> statsMenu();
                case '4' -> shopMenu();
                case '5' -> settingsMenu();
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
                (1) Name
                (2) Symbol
                (3) Coat
                (4) Accessory
                (5) View statistics
                (6) Remove horse
                (0) Exit
                Enter a number to choose:
                """;
        char choice;
        while ((choice = inputCharLowerCase(message)) != '0') {
            switch (choice) {
                case '1' -> chosenHorse.setName(input("Enter new name: "));
                case '2' -> chosenHorse.setSymbol(inputChar("Enter new symbol: "));
                case '3' -> {
                    int colorIndex = -1;
                    Horse.printColorChoices();
                    while (colorIndex < 0 || colorIndex >= Horse.getColorChoicesLength()) {
                        colorIndex = inputInt("Enter color number: ") - 1;
                    }
                    chosenHorse.setCoatColor(Horse.getColorChoice(colorIndex));
                }
                case '4' -> {
                    int accessoryIndex = -1;
                    Horse.printAccessoryChoices();
                    while (accessoryIndex < 0 || accessoryIndex >= Horse.getNumberOfShopItems() || !Horse.accessoryOwned(accessoryIndex)) {
                        accessoryIndex = inputInt("Enter accessory number: ") - 1;
                    }
                    chosenHorse.setAccessory(Horse.getAccessory(accessoryIndex));
                }
                case '5' -> {
                    chosenHorse.printHorseStats();
                    return;
                }
                case '6' -> {
                    if (Horse.getTotalHorseNumber() < 3) {
                        System.out.println("You cannot remove anymore horses right now");
                        return;
                    }
                    Horse.removeHorse(horseChoice);
                    Horse.divideHorseCost();
                    System.out.println("Horse was removed from the collection");
                    return;
                }
            }
            chosenHorse.printHorseInfo();
        }

    }

    public static void buyHorse() {
        int cost = Horse.getHorseCost();
        Race.printMoney();
        System.out.println("Do you want to buy a horse (" + cost + ")");
        if (new Scanner(System.in).nextLine().charAt(0) != 'y') return;
        if (Race.getMoney() < cost) {
            System.out.println("Not enough money (" + Race.getMoney() + "/" + cost + ")");
            return;
        }
        Race.subtractMoney(cost);
        Horse.multiplyHorseCost();
        createHorse();
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
                
                ---Horses Menu---
                (1) Modify Horses
                (2) Buy New Horse
                (0) Exit
                Enter a number to choose:
                """;
        char choice;
        while ((choice = inputCharLowerCase(message)) != '0') {
            if (choice == '1') {
                listHorses();
            }
            else if (choice == '2') {
                buyHorse();
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
            if (lanesNum > totalHorseNumber) System.out.println("\nYou do not have enough horses for that race\nYou can create more horses in the Menu");
            lanesNum = inputInt("Enter number of lanes (between 1 and 20): ");
        }
        Race newRace = new Race(distance, lanesNum);

        int horseIndex;
        ArrayList<Integer> chosenIndexes = new ArrayList<>();
        for (int i = 1; i <= lanesNum; i++) {
            Horse.printHorses();
            horseIndex = 0;
            while ((horseIndex < 1 || horseIndex > Horse.getTotalHorseNumber()) || chosenIndexes.contains(horseIndex)) {
                horseIndex = inputInt("Enter Horse Index for lane " + i + ": ");
            }
            newRace.addHorse(Horse.getHorse(horseIndex - 1), i);
            chosenIndexes.add(horseIndex);
        }
        newRace.raceSetup();
    }

    public static void settingsMenu() {
        String weatherONorOFF = "OFF";
        if (Race.isWeatherChanging()) weatherONorOFF = "ON";
        String message = "" +
                "---Settings---\n" +
                "(1) Edit Fence Symbol (" + Race.getFenceSymbol() +  ")\n" +
                "(2) Edit Fall Symbol (" + Race.getFallenSymbol() + ")\n" +
                "(3) Weather Conditions (" + weatherONorOFF + ")\n" +
                "(0) Exit\n" +
                "Enter a number to choose:\n";
        char choice;
        while ((choice = inputCharLowerCase(message)) != '0') {
            switch (choice) {
                case '1' -> {
                    Race.setFenceSymbol(inputChar("Enter new symbol: "));
                    return;
                }
                case '2' -> {
                    Race.setFallenSymbol(inputChar("Enter new symbol: "));
                    return;
                }
                case '3' -> {
                    Race.switchWeather();
                    return;
                }
            }
        }
    }

    public static void statsMenu() {
        Race.printOverallStats();
        String message = """

                (1) Race Records
                (0) Exit
                Enter a number to choose:
                """;
        char choice;
        while ((choice = inputCharLowerCase(message)) != '0') {
            if (choice == '1') {
                recordsMenu();
            }

        }
    }

    public static void recordsMenu() {
        if (Race.getNumberOfRecords() == 0) {
            System.out.println("You have no recordings yet");
            return;
        }

        System.out.println("Race Recordings:\n" + Race.getRecordFileNames());
        String fileName = input("Enter recording name: ");
        Race recordedRace = Race.loadRaceRecord(fileName + ".txt");
        if (recordedRace == null) {
            System.out.println("Could not load the recording");
            return;
        }

        recordedRace.watchRecording();
        char choice;
        String message = "\n---Recording " + fileName + "---\n" + """
                (1) Watch
                (2) Rename
                (3) Delete
                (0) Exit
                Enter a number to choose:
                """;
        while ((choice = inputCharLowerCase(message)) != '0') {
            if (choice == '1') {
                recordedRace = Race.loadRaceRecord(fileName + ".txt");
                if (recordedRace != null) recordedRace.watchRecording();
            }
            else if (choice == '2') {
                File file = new File(fileName + ".txt");
                String newName = input("Enter new name for the recording: ");
                File file2 = new File(newName + ".txt");

                if (file2.exists()) {
                    System.out.println("File with that name already exists");
                    continue;
                }
                if (file.renameTo(file2)) {
                    System.out.println("File was renamed to " + newName);
                    Race.deleteRecord(fileName);
                    Race.addRecord(newName);
                }
                else {
                    System.out.println("Failed to rename the file");
                }
            }
            else if (choice == '3') {
                File file = new File(fileName + ".txt");
                if (file.delete()) {
                    System.out.println("Deleted the file: " + fileName);
                    Race.deleteRecord(fileName);
                    break;
                } else {
                    System.out.println("Failed to delete the file.");
                }
            }
        }
    }

    public static void shopMenu() {
        Horse.printShop();
        Race.printMoney();

        int choice;
        while ((choice = inputInt("\nEnter index of the item to buy or 0 to exit: ")) != 0) {
            if (choice > 0 && choice < Horse.getNumberOfShopItems()) {
                if (Horse.accessoryOwned(choice)) {
                    System.out.println("You already own that accessory");
                }
                else {
                    Horse.buyAccessory(choice);
                    Horse.printShop();
                    Race.printMoney();
                }
            }
        }
    }
}
