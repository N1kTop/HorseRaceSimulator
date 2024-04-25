import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {


    private final static Color[] GUIcolors = {Color.ORANGE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.GREEN, Color.CYAN, Color.BLUE, Color.BLUE, Color.MAGENTA, Color.PINK, Color.BLACK, Color.GRAY, Color.WHITE, Color.LIGHT_GRAY};

    /**
     * main method, runs the menu() method
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //if (inputChar("Enter 1 for textual version\nEnter anything else for GUI version\n") == '1') menu();
        /*else*/ GUImenu();
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

    public static void GUIpopUp(String message) {
        JFrame frame = new JFrame("Message");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 5, 5));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setPreferredSize(new Dimension(300, 60));
        messageLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(messageLabel);

        JButton okButton = new JButton("Ok");
        okButton.setHorizontalAlignment(JTextField.CENTER);
        okButton.addActionListener(e -> {frame.dispose();});

        panel.add(okButton);


        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUImenu() {
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
        raceButton.addActionListener(e -> {
            GUIraceMenu();
            frame.dispose();
        });

        JButton horseButton = new JButton("Horses Stable");
        horseButton.addActionListener(e -> {
            GUIhorsesMenu();
            frame.dispose();
        });

        JButton statsButton = new JButton("Statistics");
        statsButton.addActionListener(e -> {
            GUIstatsMenu();
            frame.dispose();
        });

        JButton shopButton = new JButton("Shop");
        shopButton.addActionListener(e -> {
            GUIshopMenu();
            frame.dispose();
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {
            GUIsettingsMenu();
            frame.dispose();
        });

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
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUImenu();
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            frame.dispose();
            Race race = new Race(raceLengthSlider.getValue());
            frame.dispose();
            GUIhorsePick(race);
        });

        sliderPanel.add(exitButton);
        sliderPanel.add(nextButton);



        panel.add(sliderPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIhorsePick(Race race) {
        JFrame frame = new JFrame("Pick Horses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel pickLabel = new JLabel("Pick Horses (0/" + race.getLanesNum() + ")");
        pickLabel.setPreferredSize(new Dimension(300, 60));
        pickLabel.setHorizontalAlignment(JTextField.CENTER);
        pickLabel.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(pickLabel, BorderLayout.NORTH);

        JPanel horsePanel = new JPanel();
        int horseNum = Horse.getTotalHorseNumber();
        JToggleButton[] horseButtons = new JToggleButton[horseNum];

        for (int i = 0; i < horseNum; i++) {
            Horse horse = Horse.getHorse(i);
            horseButtons[i] = new JToggleButton(horse.getName() + " " + horse.getSymbol() + " (" + horse.getConfidence() + ")");
            horseButtons[i].addActionListener(e -> {
                int totalSelected = 0;
                for (int j = 0; j < horseNum; j++) {
                    if (horseButtons[j].isSelected()) totalSelected++;
                }
                pickLabel.setText("Pick Horses (" + totalSelected + "/" + race.getLanesNum() + ")");
            });
            horsePanel.add(horseButtons[i]);
        }

        panel.add(horsePanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUIraceMenu();
        });
        buttonPanel.add(exitButton);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            int totalSelected = 0;
            for (int i = 0; i < horseNum; i++) {
                if (horseButtons[i].isSelected()) totalSelected++;
            }
            if (totalSelected != race.getLanesNum()) {
                GUIpopUp("You have to select " + race.getLanesNum() + " horses");
                return;
            }
            int laneCount = 0;
            for (int i = 0; i < horseNum; i++) {
                if (horseButtons[i].isSelected()) race.addHorse(Horse.getHorse(i), laneCount++); //IMPROVE: error here
            }
            frame.dispose();
            race.gambleGUI();
        });
         buttonPanel.add(startButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIhorsesMenu() {
        JFrame frame = new JFrame("Horses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Horses Menu:");
        welcomeText.setPreferredSize(new Dimension(300, 60));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(welcomeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 5, 5));

        JButton modifyButton = new JButton("Modify Horses (" + Horse.getTotalHorseNumber() + ")");
        modifyButton.addActionListener(e -> {
            GUIhorsesList();
            frame.dispose();
        });

        JLabel moneyLabel = new JLabel("Money: " + Race.getMoney());
        moneyLabel.setPreferredSize(new Dimension(300, 60));
        moneyLabel.setHorizontalAlignment(JTextField.CENTER);

        JButton buyButton = new JButton("Buy Horse (" + Horse.getHorseCost() + ")");
        buyButton.addActionListener(e -> {
            if (Race.getMoney() >= Horse.getHorseCost()) {
                Race.subtractMoney(Horse.getHorseCost());
                Horse.multiplyHorseCost();
                buyButton.setText("Buy Horse (" + Horse.getHorseCost() + ")");
                moneyLabel.setText("Money: " + Race.getMoney());
                GUIcreateHorse();
                frame.dispose();
            }
        });

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUImenu();
        });

        buttonsPanel.add(modifyButton);
        buttonsPanel.add(buyButton);
        buttonsPanel.add(exitButton);


        panel.add(buttonsPanel, BorderLayout.CENTER);

        panel.add(moneyLabel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIhorsesList() {
        JFrame frame = new JFrame("Horses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Choose horse to modify:");
        welcomeText.setPreferredSize(new Dimension(300, 60));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(welcomeText, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2, 5, 5));

        for (int i = 0; i < Horse.getTotalHorseNumber(); i++) {
            Horse horse = Horse.getHorse(i);
            JButton horseButton = new JButton(horse.getName() + " " + horse.getSymbol() + " (" + horse.getConfidence() + ")");
            horseButton.addActionListener(e -> {
                GUImodifyHorse(horse);
                frame.dispose();
            });
            buttonsPanel.add(horseButton);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUIhorsesMenu();
        });
        panel.add(exitButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUImodifyHorse(Horse theHorse) {
        JFrame frame = new JFrame("Horse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 5));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(theHorse.getName());
        panel.add(nameField);

        JLabel symbolLabel = new JLabel("Symbol:");
        symbolLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(symbolLabel);

        JTextField symbolField = new JTextField("" + theHorse.getSymbol());
        panel.add(symbolField);

        JLabel breedLabel = new JLabel("Breed:");
        breedLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(breedLabel);

        JLabel breedString = new JLabel("" + theHorse.getBreed());
        breedString.setHorizontalAlignment(JTextField.CENTER);
        panel.add(breedString);

        JLabel confidenceLabel = new JLabel("Confidence:");
        confidenceLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(confidenceLabel);

        JLabel confidenceValue = new JLabel("" + theHorse.getConfidence());
        confidenceValue.setHorizontalAlignment(JTextField.CENTER);
        panel.add(confidenceValue);

        JLabel coatColorLabel = new JLabel("Coat Color:");
        coatColorLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(coatColorLabel);

        JButton coatColorButton = new JButton(theHorse.getCoatColor());
        coatColorButton.addActionListener(e -> {
            GUIchooseCoatColor(theHorse);
            frame.dispose();
        });
        panel.add(coatColorButton);

        JLabel accessoryLabel = new JLabel("Accessory:");
        accessoryLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(accessoryLabel);

        JButton accessoryButton = new JButton(theHorse.getAccessory());
        accessoryButton.addActionListener(e -> {
            GUIchooseAccessory(theHorse);
            frame.dispose();
        });
        panel.add(accessoryButton);

        JButton statsButton = new JButton("View Statistics");
        statsButton.addActionListener(e -> {GUIhorseStats(theHorse);});
        panel.add(statsButton);

        JButton removeButton = new JButton("Remove Horse");
        removeButton.addActionListener(e -> {
            if (Horse.getTotalHorseNumber() <= 3) {
                GUIpopUp("You can not remove any more horses right now");
                return;
            }
            Horse.removeHorse(theHorse);
            Horse.divideHorseCost();
            frame.dispose();
            GUIhorsesList();
        });
        panel.add(removeButton);

        JButton exitButton = new JButton("Confirm");
        exitButton.addActionListener(e -> {
            theHorse.setName(nameField.getText());
            char symbol;
            if (symbolField.getText().equals("")) symbol = 'Q';
            else symbol = symbolField.getText().charAt(0);
            theHorse.setSymbol(symbol);
            frame.dispose();
            GUIhorsesList();
        });

        panel.add(exitButton); //IMPROVE: make this look nice


        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIchooseCoatColor(Horse theHorse) {
        JFrame frame = new JFrame("Coat Colors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));



        for (int i = 0; i < Horse.getColorChoicesLength(); i++) {
            JButton colorChoice = new JButton(Horse.getColorChoice(i));
            int finalI = i;
            colorChoice.addActionListener(e -> {
                theHorse.setCoatColor(Horse.getColorChoice(finalI));
                frame.dispose();
                GUImodifyHorse(theHorse);
            });
            colorChoice.setForeground(GUIcolors[finalI]);
            panel.add(colorChoice);
        }


        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIchooseAccessory(Horse theHorse) {
        JFrame frame = new JFrame("Accessories");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));



        for (int i = 0; i < Horse.getNumberOfShopItems(); i++) {
            if (!Horse.accessoryOwned(i)) continue;

            JButton accessoryChoice = new JButton(Horse.getAccessory(i));
            int finalI = i;
            accessoryChoice.addActionListener(e -> {
                theHorse.setAccessory(Horse.getAccessory(finalI));
                frame.dispose();
                GUImodifyHorse(theHorse);
            });
            panel.add(accessoryChoice);
        }


        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIhorseStats(Horse theHorse) {
        JFrame frame = new JFrame("Horse Statistics:");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(7, 1, 5, 5));

        JLabel horseStats = new JLabel(theHorse.getName() + " Statistics:");
        horseStats.setHorizontalAlignment(JTextField.CENTER);
        textPanel.add(horseStats);

        JLabel distance = new JLabel("Total Distance: " + theHorse.getTotalDistance());
        distance.setHorizontalAlignment(JTextField.CENTER);
        textPanel.add(distance);

        JLabel averageSpeed = new JLabel("Average Speed: " + theHorse.getAverageSpeed());
        averageSpeed.setHorizontalAlignment(JTextField.CENTER);
        textPanel.add(averageSpeed);

        JLabel wins = new JLabel("Wins: " + theHorse.getTotalWins());
        wins.setHorizontalAlignment(JTextField.CENTER);
        textPanel.add(wins);

        JLabel races = new JLabel("Races: " + theHorse.getTotalRaces());
        races.setHorizontalAlignment(JTextField.CENTER);
        textPanel.add(races);

        JLabel winRate = new JLabel("Win Rate: " + theHorse.getWinRate());
        winRate.setHorizontalAlignment(JTextField.CENTER);
        textPanel.add(winRate);

        JPanel finishingTimesPanel = new JPanel();
        finishingTimesPanel.setLayout(new GridLayout(7, 1, 5, 5));

        JLabel finishingTimesTitle = new JLabel("Finishing Times:");
        finishingTimesTitle.setHorizontalAlignment(JTextField.CENTER);
        finishingTimesPanel.add(finishingTimesTitle);
        for (int time : theHorse.getFinishingTimes()) {
            JLabel finishingTime = new JLabel(Integer.toString(time));
            finishingTime.setHorizontalAlignment(JTextField.CENTER);
            finishingTimesPanel.add(finishingTime);
        }

        panel.add(textPanel, BorderLayout.NORTH);

        panel.add(finishingTimesPanel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> {frame.dispose();});
        panel.add(exitButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void GUIcreateHorse() {
        JFrame frame = new JFrame("New Horse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel newHorseText1 = new JLabel("Create");
        newHorseText1.setHorizontalAlignment(JTextField.RIGHT);
        fieldPanel.add(newHorseText1);

        JLabel newHorseText2 = new JLabel("New Horse:");
        newHorseText2.setHorizontalAlignment(JTextField.LEFT);
        fieldPanel.add(newHorseText2);

        JLabel nameText = new JLabel("Name:");
        nameText.setHorizontalAlignment(JTextField.CENTER);
        fieldPanel.add(nameText);

        JTextField nameField = new JTextField();
        fieldPanel.add(nameField);

        JLabel symbolText = new JLabel("Symbol:");
        symbolText.setHorizontalAlignment(JTextField.CENTER);
        fieldPanel.add(symbolText);

        JTextField symbolField = new JTextField();
        fieldPanel.add(symbolField);

        panel.add(fieldPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 3, 1, 1));

        JRadioButton[] breedButtons = new JRadioButton[Horse.getBreedChoicesLength()];
        for (int i = 0; i < Horse.getBreedChoicesLength(); i++) {
            breedButtons[i] = new JRadioButton(Horse.getBreedChoice(i));
            int len = Horse.getBreedChoicesLength();
            int finalI = i;
            breedButtons[i].addActionListener(r -> {
                for(int j = 0; j < len; j++) {
                    if (j == finalI) continue;
                    breedButtons[j].setSelected(false);
                }
            });
            buttonsPanel.add(breedButtons[i]);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Create");
        confirmButton.addActionListener(e -> {
            char symbol;
            if (symbolField.getText().equals("")) symbol = 'Q';
            else symbol = symbolField.getText().charAt(0);
            String breed = "Arabian";
            for (JRadioButton breedChoice : breedButtons) {
                if (breedChoice.isSelected()) breed = breedChoice.getText();
            }
            Horse.addHorse(new Horse(symbol, nameField.getText(), breed));
            frame.dispose();
            GUIhorsesMenu();
        });

        panel.add(confirmButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
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
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUImenu();
        });

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
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUImenu();
        });

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
        exitButton.addActionListener(e -> {
            frame.dispose();
            GUImenu();
        });

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
        frame.setSize(300, 200);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Enter new fence symbol:");
        welcomeText.setPreferredSize(new Dimension(300, 50));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 14));
        panel.add(welcomeText, BorderLayout.NORTH);

        JTextField field = new JTextField("" + Race.getFenceSymbol());
        field.setHorizontalAlignment(JTextField.CENTER);
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
        frame.setSize(300, 200);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Enter new fall symbol:");
        welcomeText.setPreferredSize(new Dimension(300, 50));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 14));
        panel.add(welcomeText, BorderLayout.NORTH);

        JTextField field = new JTextField("" + Race.getFenceSymbol());
        field.setHorizontalAlignment(JTextField.CENTER);
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
