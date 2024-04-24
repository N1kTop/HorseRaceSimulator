import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    /**
     * main method, runs the menu() method
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        if (inputChar("Enter 1 for textual version\nEnter anything else for GUI version\n") == '1') menu();
        else GUImenu();
    }


    /**
     * allows user to input a single character which will be turned into lowercase
     *
     * @param message will be printed before scanning for input
     * @return first character of the input in lower case
     */
    public static char inputCharLowerCase(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine().toLowerCase().charAt(0);
    }

    /**
     * allows user to input a single charcter
     *
     * @param message will be printed before scanning for input
     * @return first character of the input
     */
    public static char inputChar(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine().charAt(0);
    }

    /**
     * allows for user input
     *
     * @param message will be printed before scanning for input
     * @return the entered string
     */
    public static String input(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    /**
     * allows user to input a number
     *
     * @param message will be printed before scanning for input
     * @return entered number as an integer or -1 if input format is wrong
     */
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

    public static void GUImenu() throws IOException {
        JFrame frame = new JFrame("Horse Racing Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Welcome to Horse Racing!");
        welcomeText.setPreferredSize(new Dimension(300, 60));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(welcomeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2, 5, 5));

        JButton raceButton = new JButton("Race");
        raceButton.addActionListener(e -> {GUIraceMenu();});

        JButton horseButton = new JButton("Horses Stable");
        horseButton.addActionListener(e -> {GUIhorsesMenu();});

        JButton statsButton = new JButton("Statistics");
        statsButton.addActionListener(e -> {GUIstatsMenu();});

        JButton shopButton = new JButton("Shop");
        shopButton.addActionListener(e -> {GUIshopMenu();});

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {GUIsettingsMenu();});

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {System.exit(0);});

        buttonsPanel.add(raceButton);
        buttonsPanel.add(horseButton);
        buttonsPanel.add(statsButton);
        buttonsPanel.add(shopButton);
        buttonsPanel.add(settingsButton);
        buttonsPanel.add(exitButton);


        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIraceMenu() {
        JFrame frame = new JFrame("Race Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(5, 2, 5, 5));


        JSlider raceLengthSlider = new JSlider(Race.getMinDistance(), Race.getMaxDistance(), 25);

        JLabel raceLengthChoice = new JLabel("Choose length of the race: " + Integer.toString(raceLengthSlider.getValue()));
        sliderPanel.add(raceLengthChoice);

        raceLengthSlider.addChangeListener(e -> {raceLengthChoice.setText("Choose length of the race: " + Integer.toString(raceLengthSlider.getValue()));});
        sliderPanel.add(raceLengthSlider);

        JSlider lanesNumberSlider = new JSlider(1, Horse.getTotalHorseNumber(), 3);

        JLabel lanesNumberChoice = new JLabel("Choose number of lanes: " + Integer.toString(lanesNumberSlider.getValue()));
        sliderPanel.add(lanesNumberChoice);

        lanesNumberSlider.addChangeListener(e -> {lanesNumberChoice.setText("Choose number of lanes: " + Integer.toString(lanesNumberSlider.getValue()));});
        sliderPanel.add(lanesNumberSlider);


        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {frame.dispose();});

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {frame.dispose();});

        sliderPanel.add(exitButton);
        sliderPanel.add(nextButton);



        panel.add(sliderPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIhorsesMenu() {

    }

    public static void GUIstatsMenu() {
        JFrame frame = new JFrame("Statistics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel statsMenuTitle = new JLabel("Overall Statistics:");
        statsMenuTitle.setPreferredSize(new Dimension(300, 60));
        statsMenuTitle.setHorizontalAlignment(JTextField.CENTER);
        statsMenuTitle.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(statsMenuTitle, BorderLayout.NORTH);

        Horse topHorse = Horse.getTopHorse();
        JTextArea stats = new JTextArea("\nTotal Races: " + Race.getTotalRaces() + "\nTotal Finishes: " + Race.getTotalFinishes() + "\nCurrent Number of Horses: " + Horse.getTotalHorseNumber() + "\nTop Horse: " + topHorse.getName() + " " + topHorse.getSymbol() + " with " + topHorse.getTotalWins() + " wins\n");
        stats.setPreferredSize(new Dimension(300, 120));
        stats.setFont(new Font("Ariel", Font.PLAIN, 16));
        panel.add(stats, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4, 5, 5));


        JButton recordsButton = new JButton("Race Records");
        recordsButton.addActionListener(e -> {recordsMenu();});

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {frame.dispose();});

        buttonsPanel.add(recordsButton);
        buttonsPanel.add(exitButton);


        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIshopMenu() {
        JFrame frame = new JFrame("Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel accessoryMenuTitle = new JLabel("Accessories:");
        accessoryMenuTitle.setPreferredSize(new Dimension(300, 60));
        accessoryMenuTitle.setHorizontalAlignment(JTextField.CENTER);
        accessoryMenuTitle.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(accessoryMenuTitle, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 2, 5, 5));

        String buttonLabel;
        for (int i = 1; i < Horse.getNumberOfShopItems(); i++) {
            if (Horse.accessoryOwned(i)) buttonLabel = Horse.getAccessory(i) + " (owned)";
            else buttonLabel = Horse.getAccessory(i) + " (" + Horse.getAccessoryPrice(i) + ")";
            JButton itemButton = new JButton(buttonLabel);
            int finalI = i;
            itemButton.addActionListener(e -> {
                if (!Horse.accessoryOwned(finalI)) {
                    Horse.buyAccessory(finalI);
                    frame.dispose();
                    GUIshopMenu();
                }
            });
            buttonsPanel.add(itemButton);
        }


        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {frame.dispose();});

        buttonsPanel.add(exitButton);


        panel.add(buttonsPanel, BorderLayout.CENTER);

        JLabel moneyLabel = new JLabel("Money: " + Race.getMoney());
        moneyLabel.setPreferredSize(new Dimension(300, 50));
        moneyLabel.setHorizontalAlignment(JTextField.CENTER);
        moneyLabel.setFont(new Font("Ariel", Font.PLAIN, 16));
        panel.add(moneyLabel, BorderLayout.SOUTH);


        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIrecordsMenu() {

    }

    public static void GUIsettingsMenu() {
        JFrame frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Settings:");
        welcomeText.setPreferredSize(new Dimension(300, 60));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(welcomeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JButton editFenceSymbolButton = new JButton("Edit Fence Symbol (" + Race.getFenceSymbol() + ")");
        editFenceSymbolButton.addActionListener(e -> {
            GUIchangeFenceSymbol();
            frame.dispose();
        });

        JButton editFallenSymbolButton = new JButton("Edit Fall Symbol (" + Race.getFallenSymbol() + ")");
        editFallenSymbolButton.addActionListener(e -> {
            GUIchangeFallenSymbol();
            frame.dispose();
        });


        String weatherONorOFF = "OFF";
        if (Race.isWeatherChanging()) weatherONorOFF = "ON";
        JButton weatherConditionsButton = new JButton("Weather Conditions (" + weatherONorOFF + ")");
        weatherConditionsButton.addActionListener(e -> {
            Race.switchWeather();
            if (Race.isWeatherChanging()) weatherConditionsButton.setText("Weather Conditions (ON)");
            else weatherConditionsButton.setText("Weather Conditions (OFF)");
        });

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {frame.dispose();});

        buttonsPanel.add(editFenceSymbolButton);
        buttonsPanel.add(editFallenSymbolButton);
        buttonsPanel.add(weatherConditionsButton);
        buttonsPanel.add(exitButton);


        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIchangeFenceSymbol() {
        JFrame frame = new JFrame("Fence Symbol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Enter new fence symbol:");
        welcomeText.setPreferredSize(new Dimension(300, 50));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 14));
        panel.add(welcomeText, BorderLayout.NORTH);

        JTextField field = new JTextField(Race.getFenceSymbol());
        panel.add(field, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {

            if (!field.getText().equals("")) {
                Race.setFenceSymbol(field.getText().charAt(0));
                frame.dispose();
                GUIsettingsMenu();
            }
        });

        panel.add(confirmButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIchangeFallenSymbol() {
        JFrame frame = new JFrame("Fallen Symbol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Enter new fall symbol:");
        welcomeText.setPreferredSize(new Dimension(300, 50));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 14));
        panel.add(welcomeText, BorderLayout.NORTH);

        JTextField field = new JTextField(Race.getFenceSymbol());
        panel.add(field, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {

            if (!field.getText().equals("")) {
                Race.setFallenSymbol(field.getText().charAt(0));
                frame.dispose();
                GUIsettingsMenu();
            }
        });

        panel.add(confirmButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    /**
     * main menu of the program
     * allows choice from 5 different options, use numbers to choose
     * runs other menu methods when needed, stops after entering 0
     */
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
    public static void settingsMenu() {
        String weatherONorOFF = "OFF";
        if (Race.isWeatherChanging()) weatherONorOFF = "ON";
        String message = "\n" +
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
    public static void shopMenu() {
        if (Horse.getNumberOfOwnedAccessories() == Horse.getNumberOfShopItems()) {
            System.out.println("There is no more items to buy");
            return;
        }

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
