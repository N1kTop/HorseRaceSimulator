import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A horse race, each horse running in its own lane
 * for a given distance
 *
 * @author Nikita Topolskis
 * @version 3.0
 */
public class Race {
    private int raceLength;
    private Horse[] horseLanes;
    private int betAmount;
    private int betLaneIndex;
    private static char fallenSymbol = 'X';
    private static char fenceSymbol = '=';
    private static int totalRaces = 0;
    private static int totalFinishes = 0;
    private static ArrayList<String> recordFileNames = loadRecordingNames();
    private static int money = 10000;
    private final static int moneyPerRace = 50;
    private final static int minBet = 5;
    private final static int minDistance = 5;
    private final static int maxDistance = 200;
    private int weatherCondition = 1;
    private static boolean weatherChanging = true;
    private boolean raceEnd = false;
    private int stepCount = 0; //used for to count number of steps of the race (time taken)


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the horseLanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int lanesNum, Horse[] horses) {
        // initialise instance variables
        raceLength = distance;
        horseLanes = new Horse[lanesNum];

        // put horses into horseLanes
        for (int i = 0; i < horses.length && i < horseLanes.length; i++) {
            horseLanes[i] = horses[i];
        }

    }
    public Race(int distance, int lanesNum) {
        // initialise instance variables
        raceLength = distance;
        horseLanes = new Horse[lanesNum];
    }
    public Race(int distance) {
        // initialise instance variables
        raceLength = distance;
        horseLanes = new Horse[3];
    }

    //Accessor methods:
    public int getLanesNum() {return horseLanes.length;}

    public static void setFallenSymbol(char newSymbol) {
        fallenSymbol = newSymbol;
    }

    public static char getFallenSymbol() {
        return fallenSymbol;
    }

    public static void setFenceSymbol(char newSymbol) {
        fenceSymbol = newSymbol;
    }

    public static char getFenceSymbol() {
        return fenceSymbol;
    }

    public static int getTotalRaces() {
        return totalRaces;
    }

    public static int getTotalFinishes() {return totalFinishes;}

    //get names of all saved recordings:
    public static ArrayList<String> getRecordFileNames() {
        return recordFileNames;
    }

    public static void deleteRecord(String fileName) {
        recordFileNames.remove(fileName);
    }

    public static void addRecord(String fileName) {
        recordFileNames.add(fileName);
    }

    public static int getNumberOfRecords() {
        return recordFileNames.size();
    }

    public static int getMoney() {return money;}

    public static void addMoney(int moneyBonus) {money += moneyBonus;}

    public static void subtractMoney(int moneyLoss) {money -= moneyLoss;}

    //boolean setting for weather effects during races:
    public static boolean isWeatherChanging() {return weatherChanging;}

    //minimum distance for the race:
    public static int getMinDistance() {return minDistance;}

    //maximum distance for the race:
    public static int getMaxDistance() {return maxDistance;}

    /**
     * toggles weather setting between true and false
     */
    public static void switchWeather() {
        if (weatherChanging) weatherChanging = false;
        else weatherChanging = true;
    }


    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse   the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        laneNumber--;
        if (laneNumber >= 0 && laneNumber < horseLanes.length) {
            horseLanes[laneNumber] = theHorse;
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * sets up all the race settings:
     * checks if chosen horses are valid
     * checks for weather conditions
     * asks if user wants to gamble
     *
     */
    public void raceSetup() {
        //check if every lane has a horse
        for (Horse horse : horseLanes) {
            if (horse == null) {
                System.out.println("Cannot start race, since not all lanes are filled");
                return;
            }
        }

        if (weatherChanging) {
            weatherCondition = (int) (Math.random() * 2) + 1; //randomly choose weather condition
        }
        switch (weatherCondition) {
            case 1 -> System.out.println("\nThe weather is good"); //1 = clear
            case 2 -> System.out.println("\nIt is raining, chances of falling are doubled"); //2 = rain
            case 3 -> System.out.println("\nThe weather is disastrous, chances of falling are tripled"); //3 = storm
        }

        stepCount = 0;

        //gambling:
        betAmount = 0;
        betLaneIndex = -1;
        if (horseLanes.length > 1) {
            char choice = Menu.inputCharLowerCase("\nDo you want to gamble?\n");
            if (choice == 'y') gamble();
        }
        Menu.input("\nPress enter to start the race");
        startRace();
    }

    /**
     * Provides gambling options
     * Asks for gambling amount and a horse to bet on
     */
    private void gamble() {
        //print details:
        printHorseLanes();
        System.out.println("Distance: " + raceLength);
        printMoney();

        //ask for horse to bet on:
        while (betLaneIndex < 0 || betLaneIndex >= horseLanes.length) {
            betLaneIndex = Menu.inputInt("\nEnter lane number to bet on: ") - 1;
        }
        //ask for amount to bet:
        System.out.println("Minimum bet: " + minBet);
        while (betAmount < minBet || betAmount > money) {
            betAmount = Menu.inputInt("Enter bet amount: ");
            if (betAmount > money) System.out.println("You do not have enough money (" + money + "/" + betAmount + ")");
        }

        subtractMoney(betAmount);
        System.out.println("You put a bet of " + betAmount + " on " + horseLanes[betLaneIndex].getName());
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     * finally, winning message printed
     */
    public void startRace() {

        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //increment races count
        totalRaces++;

        //reset all the horseLanes (all horses not fallen and back to 0) and increment total number of taken races.
        for (Horse horse : horseLanes) {
            horse.goBackToStart();
            horse.incTotalRaces();
            horse.clearCurrentRaceRecord();
        }

        while (!finished) {
            //move each horse
            for (Horse horse : horseLanes) {
                moveHorse(horse);
            }
            stepCount++;

            //print the race positions
            printRace();


            //if all the horses have fallen, the race is finished
            finished = true;
            for (int i = 0; finished && i < horseLanes.length; i++) {
                if (!horseLanes[i].hasFallen()) finished = false;
            }

            //if any of the horses has won, the race is finished
            for (Horse horse : horseLanes) {
                if (raceWonBy(horse)) finished = true;
            }

            //wait for 100 milliseconds
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //check winner
        boolean winnerExists = false;
        for (Horse horse : horseLanes) {
            if (raceWonBy(horse)) {
                if (winnerExists) { //if multiple horses finish simultaneously
                    printAlmostWinner(horse); //prints message that the second horse was very close
                    continue;
                }
                printWinner(horse);
                horse.addStepToCurrentRaceRecord('w'); //records end of race (w represents win)
                totalFinishes++; //increment total finishes statistic
                winnerExists = true;
                if (betAmount > 0) { //if user had gambled
                    if (horseLanes[betLaneIndex].equals(horse)) { //check if bet was successful or not
                        int winningAmount = betAmount * horseLanes.length;
                        System.out.println("Your bet was successful\nYou have won " + winningAmount);
                        addMoney(winningAmount);
                    }
                    else {
                        System.out.println("You have lost your bet of " + betAmount);
                    }
                }
            }
        }
        if (!winnerExists) { //if no winner
            System.out.println("\n No Winner - all the horses failed to finish the race.");
            if (betAmount > 0) System.out.println("\nYou have lost your bet of " + betAmount);
        }

        //add money bonus for conducting the race and print details:
        raceMoneyBonus();
        printMoney();

        //save recording:
        char askToSave = Menu.inputChar("\nDo you want to save the recording of this race?\n");
        if (askToSave == 'y') {
            String recordName = Menu.input("\nEnter record name to save the race: ");
            saveRaceRecord(recordName);
        }
    }

    public void gambleGUI() {
        JFrame frame = new JFrame("Gambling?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeText = new JLabel("Gambling (optional):");
        welcomeText.setPreferredSize(new Dimension(300, 60));
        welcomeText.setHorizontalAlignment(JTextField.CENTER);
        welcomeText.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(welcomeText, BorderLayout.NORTH);

        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel betAmountLabel = new JLabel("Bet Amount: 0");
        bettingPanel.add(betAmountLabel);

        JSlider betAmountSlider = new JSlider(0, money, 0);
        betAmountSlider.addChangeListener(e -> {betAmountLabel.setText("Bet Amount: " + betAmountSlider.getValue());});
        bettingPanel.add(betAmountSlider);

        JLabel betTargetLabel = new JLabel("Bet on:");
        bettingPanel.add(betTargetLabel);

        JSlider betTargetSlider = new JSlider(1, getLanesNum(), 1);
        betTargetSlider.addChangeListener(e -> {
            Horse horse = horseLanes[betTargetSlider.getValue() - 1];
            betTargetLabel.setText("Bet on: " + horse.getName() + " " + horse.getSymbol() + " (" + horse.getConfidenceFormatted() + ")");
        });
        bettingPanel.add(betTargetSlider);

        JButton nextButton = new JButton("Continue");
        nextButton.addActionListener(e -> {
            betAmount = betAmountSlider.getValue();
            betLaneIndex = betTargetSlider.getValue() - 1;
            frame.dispose();
            startRaceGUI();
        });


        panel.add(bettingPanel, BorderLayout.CENTER);

        panel.add(nextButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void startRaceGUI() {
        JFrame frame = new JFrame("Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(raceLength * 25, 600 + getLanesNum() * 25);
        frame.setResizable(false);
        Font raceFont = new Font("Ariel", Font.PLAIN, 20);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(getLanesNum() + 4, 1, 5, 0));

        String multipleFence = "";
        for (int i = 0; i < raceLength; i++) multipleFence += fenceSymbol;

        JLabel fenceLabel1 = new JLabel(multipleFence);
        fenceLabel1.setFont(raceFont);
        panel.add(fenceLabel1);

        JLabel[] laneLabels = new JLabel[getLanesNum()];
        String laneString;

        for (int i = 0; i < getLanesNum(); i++) {
            laneString = getLaneAsString(horseLanes[i]);
            laneLabels[i] = new JLabel(laneString);
            laneLabels[i].setFont(raceFont);
            panel.add(laneLabels[i]);
        }

        JLabel fenceLabel2 = new JLabel(multipleFence);
        fenceLabel2.setFont(raceFont);
        panel.add(fenceLabel2);

        JLabel winnerLabel = new JLabel("");
        winnerLabel.setFont(raceFont);
        panel.add(winnerLabel);

        JButton nextButton = new JButton("Continue");
        nextButton.addActionListener(e -> {
            saveRaceGUI();
            frame.dispose();
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);


        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        raceEnd = false;
        stepCount = 0;

        //increment races count
        totalRaces++;

        //reset all the horseLanes (all horses not fallen and back to 0) and increment total number of taken races.
        for (Horse horse : horseLanes) {
            horse.goBackToStart();
            horse.incTotalRaces();
            horse.clearCurrentRaceRecord();
        }

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //no action if race ended
                if (raceEnd) return;

                //move each horse
                for (Horse horse : horseLanes) {
                    moveHorse(horse);
                    stepCount++;
                }

                //update lanes
                for (int i = 0; i < getLanesNum(); i++) {
                    String laneString;
                    laneString = getLaneAsString(horseLanes[i]);
                    laneLabels[i].setText(laneString);
                }

                //if all the horses have fallen, the race is finished
                boolean finished = true;
                for (int i = 0; finished && i < horseLanes.length; i++) {
                    if (!horseLanes[i].hasFallen()) finished = false;
                }

                //if any of the horses has won, the race is finished
                for (Horse horse : horseLanes) {
                    if (raceWonBy(horse)) finished = true;
                }

                if (finished && !raceEnd) {
                    Horse winningHorse = finaliseResultsGUI();
                    if (winningHorse == null) winnerLabel.setText("No Winner - all the horses failed to finish the race");
                    else winnerLabel.setText("The Winner is: " + winningHorse.getName());
                    panel.add(nextButton);
                    raceEnd = true;
                }

            }
        });
        timer.start();
    }

    private Horse finaliseResultsGUI() {
        Horse winningHorse = null;
        //check winner
        boolean winnerExists = false;
        for (Horse horse : horseLanes) {
            if (raceWonBy(horse)) {
                if (winnerExists) {
                    continue;
                }
                winningHorse = horse;
                //increment winners win count:
                winningHorse.win();
                winningHorse.addFinishingTime(stepCount, raceLength);
                horse.addStepToCurrentRaceRecord('w');
                totalFinishes++;
                winnerExists = true;
                if (betAmount > 0) {
                    if (horseLanes[betLaneIndex].equals(horse)) {
                        int winningAmount = betAmount * horseLanes.length;
                        Menu.GUIpopUp("Your bet was successful\nYou have won " + winningAmount);
                        addMoney(winningAmount);
                    }
                    else {
                        Menu.GUIpopUp("You have lost your bet of " + betAmount);
                    }
                }
            }
        }
        if (!winnerExists && betAmount > 0) Menu.GUIpopUp("\nYou have lost your bet of " + betAmount);

        raceMoneyBonus();

        return winningHorse;
    }

    private void saveRaceGUI() {

        JFrame frame = new JFrame("Save Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel saveRaveLabel1 = new JLabel("Save Race");
        saveRaveLabel1.setPreferredSize(new Dimension(300, 60));
        saveRaveLabel1.setHorizontalAlignment(JTextField.RIGHT);
        saveRaveLabel1.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(saveRaveLabel1);

        JLabel saveRaveLabel2 = new JLabel(" Recoding:");
        saveRaveLabel2.setPreferredSize(new Dimension(300, 60));
        saveRaveLabel2.setHorizontalAlignment(JTextField.LEFT);
        saveRaveLabel2.setFont(new Font("Ariel", Font.PLAIN, 18));
        panel.add(saveRaveLabel2);

        JLabel recordingNameLabel = new JLabel("Enter Recording Name:");
        recordingNameLabel.setHorizontalAlignment(JTextField.CENTER);
        panel.add(recordingNameLabel);

        JTextField recordingNameField = new JTextField();
        recordingNameField.setPreferredSize(new Dimension(20, 10));
        recordingNameField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(recordingNameField);

        JButton nextButton = new JButton("Save");
        nextButton.addActionListener(e -> {
            if (recordingNameField.getText().equals("")) {
                Menu.GUIpopUp("The name cannot be empty");
                return;
            }
            saveRaceRecord(recordingNameField.getText());
            frame.dispose();
            Menu.GUImenu();
        });

        JButton cancelButton = new JButton("Do not Save");
        cancelButton.addActionListener(e -> {
            frame.dispose();
            Menu.GUImenu();
        });

        panel.add(cancelButton);
        panel.add(nextButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void watchRecordingGUI() {
        JFrame frame = new JFrame("Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(raceLength * 25, 600 + getLanesNum() * 25);
        frame.setResizable(false);
        Font raceFont = new Font("Ariel", Font.PLAIN, 20);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(getLanesNum() + 4, 1, 5, 0));

        String multipleFence = "";
        for (int i = 0; i < raceLength; i++) multipleFence += fenceSymbol;

        JLabel fenceLabel1 = new JLabel(multipleFence);
        fenceLabel1.setFont(raceFont);
        panel.add(fenceLabel1);

        JLabel[] laneLabels = new JLabel[getLanesNum()];
        String laneString;

        for (int i = 0; i < getLanesNum(); i++) {
            laneString = getLaneAsString(horseLanes[i]);
            laneLabels[i] = new JLabel(laneString);
            laneLabels[i].setFont(raceFont);
            panel.add(laneLabels[i]);
        }

        JLabel fenceLabel2 = new JLabel(multipleFence);
        fenceLabel2.setFont(raceFont);
        panel.add(fenceLabel2);

        JLabel winnerLabel = new JLabel("");
        winnerLabel.setFont(raceFont);
        panel.add(winnerLabel);

        JButton nextButton = new JButton("Back");
        nextButton.addActionListener(e -> {
            Menu.GUIrecordsMenu();
            frame.dispose();
        });
        panel.add(nextButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);


        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        raceEnd = false;
        stepCount = 0;

        //increment races count
        totalRaces++;

        //reset all the horseLanes (all horses not fallen and back to 0) and increment total number of taken races.
        for (Horse horse : horseLanes) {
            horse.goBackToStart();
        }

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char c;

                //no action if race ended
                if (raceEnd) return;

                //move each horse
                for (Horse horse : horseLanes) {
                    //check recording steps
                    if (stepCount < horse.getCurrentRaceRecord().size()) {
                        c = horse.getCurrentRaceRecord().get(stepCount);
                        if (c == 'm') { //m represents movement
                            horse.moveForward();
                        } else if (c == 'f') { //f represents fall
                            horse.fall();
                        }
                    }
                }
                stepCount++;

                //update lanes
                for (int i = 0; i < getLanesNum(); i++) {
                    String laneString;
                    laneString = getLaneAsString(horseLanes[i]);
                    laneLabels[i].setText(laneString);
                }

                //if all the horses have fallen, the race is finished
                boolean finished = true;
                for (int i = 0; finished && i < horseLanes.length; i++) {
                    if (!horseLanes[i].hasFallen()) finished = false;
                }

                //if any of the horses has won, the race is finished
                for (Horse horse : horseLanes) {
                    if (raceWonBy(horse)) finished = true;
                }

                if (finished && !raceEnd) {
                    raceEnd = true;
                }

            }
        });
        timer.start();
    }


    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen

        if (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
                theHorse.incTotalDistance(); //increment total distance statistic of the horse
                theHorse.addStepToCurrentRaceRecord('m'); //adds 'm' to record (representing movement)
            } else {
                theHorse.addStepToCurrentRaceRecord('n'); //adds 'n' to record (representing no movement)
            }

            theHorse.incTotalTime(); //increment total run time statistic of the horse

            //the probability that the horse will fall is very small (max is 0.05 when clear weather and 0.15 when storm)
            //but will also depend exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            //weather condition can double or triple falling chances
            if (Math.random() < (0.05 * weatherCondition * theHorse.getConfidence() * theHorse.getConfidence())) {
                theHorse.fall();
                theHorse.addStepToCurrentRaceRecord('f'); //adds 'f' to record (representing fall)

            }
        }
    }

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint(fenceSymbol, raceLength + 3); //top edge of track
        System.out.println();

        for (Horse horse : horseLanes) {
            printLane(horse);
            System.out.println();
        }

        multiplePrint(fenceSymbol, raceLength + 3); //bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ', spacesBefore);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if (theHorse.hasFallen()) {
            System.out.print(fallenSymbol);
        } else {
            System.out.print(theHorse.getSymbol());
        }

        //print the spaces after the horse
        multiplePrint(' ', spacesAfter);

        //print the | for the end of the track
        System.out.print('|');

        //print horse name and confidence
        System.out.print("     " + theHorse.getName() + " (" + theHorse.getConfidenceFormatted() + ")");
    }

    private String getLaneAsString(Horse theHorse) {
        String lane = "";
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //add a | for the beginning of the lane
        lane += '|';

        //print the spaces before the horse
        lane += multipleCharacters(' ', spacesBefore);

        //if the horse has fallen then add fallen symbol
        //else add the horse's symbol
        if (theHorse.hasFallen()) {
            lane += fallenSymbol;
        } else {
            lane += theHorse.getSymbol();
        }

        //add the spaces after the horse
        lane += multipleCharacters(' ', spacesAfter);

        //add the | for the end of the track
        lane += '|';

        //add horse name and confidence
        lane += "     " + theHorse.getName() + " (" + theHorse.getConfidenceFormatted() + ")";
        return lane;
    }

    private static String multipleCharacters(char aChar, int times) {
        String s = "";
        for (int i = 0; i < times; i++) s += ' ';
        return s;
    }


    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        int i = 0;
        while (i < times) {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    /**
     * print a winning message and increase winning horse's confidence
     *
     * @param winnerHorse The horse that has won
     */
    private void printWinner(Horse winnerHorse) {
        winnerHorse.win();
        winnerHorse.addFinishingTime(stepCount, raceLength);
        System.out.println("\nThe Winner of the race is " + winnerHorse.getName() + "!\n");
    }

    /**
     * prints a message that one of the losing horses was very close to winning
     *
     * @param horse The horse that has almost won
     */
    private void printAlmostWinner(Horse horse) {
        System.out.println("\n" + horse.getName() + " was close to winning, but finished finished less than a second late\n");
    }

    /**
     * prints horses with their winning chances before the user would gamble
     */
    private void printHorseLanes() {
        double totalWinRate = 0.0;
        boolean unpredictable = false;
        for (Horse horse : horseLanes) {
            if (horse.getTotalRaces() == 0) unpredictable = true; //if horse has never participated before - unpredictable
            totalWinRate += horse.getWinRate();
        }
        if (totalWinRate == 0.0) {unpredictable = true;} //if none of the horses have ever won - unpredictable
        int i = 1;
        System.out.println("Lanes:");

        //if race is unpredictable, give vague predictions based on win rate
        if (unpredictable) {
            String prediction = null;
            for (Horse horse : horseLanes) {
                if (horse.getTotalRaces() == 0) prediction = "Unknown";
                else if (horse.getWinRate() == 0) prediction = "Low";
                else if (horse.getWinRate() / totalWinRate > 0.5) prediction = "High";
                else prediction = "Medium";
                System.out.println(i++ + " " + horse.getName() + " (" + horse.getBreed() + ") " + horse.getSymbol() + " - " + horse.getConfidenceFormatted() + "   [Predicted Win Chance: " + prediction + "]");
            }
        }
        else { //give predictions based on win rate
            for (Horse horse : horseLanes) {
                System.out.println(i++ + " " + horse.getName() + " (" + horse.getBreed() + ") " + horse.getSymbol() + " - " + horse.getConfidenceFormatted() + "   [Predicted Win Chance: " + (horse.getWinRate() / totalWinRate) + "]");
            }
        }
    }

    public static void printMoney() {
        System.out.println("Money: " + money);
    }

    /**
     * prints general statistics
     */
    public static void printOverallStats() {
        System.out.println("\n---Overall Stats---");
        System.out.println("Total Races: " + totalRaces);
        System.out.println("Total Finishes " + totalFinishes);
        System.out.println("Current Number of Horses: " + Horse.getTotalHorseNumber());
        Horse topHorse = Horse.getTopHorse();
        System.out.println("Top Horse: " + topHorse.getName() + " " + topHorse.getSymbol() + " with " + topHorse.getTotalWins() + " wins");
    }

    /**
     * prints how much each horse has travelled during the race
     * used in watchRecording() method
     */
    private void printDistances() {
        System.out.println("Distances travelled:");
        for (Horse horse : horseLanes) {
            System.out.println(horse.getName() + ": " + horse.getDistanceTravelled());
        }
    }

    /**
     * shows a recording of the past race
     * the recording identically replicates the race and shows additional info at the end
     * the recordings are loaded from a text file
     *
     */
    public void watchRecording() {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        stepCount = 0;
        char c;

        //reset all the horseLanes (all horses not fallen and back to 0) and increment total number of taken races.
        for (Horse horse : horseLanes) {
            horse.goBackToStart();
            System.out.println(horse.getCurrentRaceRecord());
        }

        while (!finished) {
            //move each horse
            for (Horse horse : horseLanes) {
                //check recording steps
                if (stepCount < horse.getCurrentRaceRecord().size()) {
                    c = horse.getCurrentRaceRecord().get(stepCount);
                    if (c == 'm') { //m represents movement
                        horse.moveForward();
                    } else if (c == 'f') { //f represents fall
                        horse.fall();
                    }
                }
            }
            //increment step count
            stepCount++;


            //print the race positions
            printRace();


            //if all the horses have fallen, the race is finished
            finished = true;
            for (int i = 0; finished && i < horseLanes.length; i++) {
                if (!horseLanes[i].hasFallen()) finished = false;
            }

            //if any of the horses has won, the race is finished
            for (Horse horse : horseLanes) {
                if (raceWonBy(horse)) finished = true;
            }

            //wait for 100 milliseconds
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //check winner
        boolean winnerExists = false;
        for (Horse horse : horseLanes) {
            if (raceWonBy(horse)) {
                printWinner(horse);
                winnerExists = true;
            }
        }
        if (!winnerExists) System.out.println("\n No Winner - all the horses failed to finish the race.");
        printDistances();
    }

    /**
     * Saves recording of a race to a text file
     *
     * @param saveFileName name of the file
     */
    private void saveRaceRecord(String saveFileName) {
        try (FileWriter writer = new FileWriter(saveFileName + ".txt")) {
            writer.write(horseLanes.length + "\n");
            writer.write(raceLength + "\n");

            //saves horses information:
            for (Horse horse : horseLanes) {
                writer.write(horse.getSymbol());
                writer.write(horse.getName());
                writer.write("\n");
            }

            //saves horses steps:
            for (Horse horse : horseLanes) {
                String s = horse.getCurrentRaceRecord().toString().replace(", ", "").replace(", ", "").replace("[", "").replace("]", "");
                writer.write(s);
                writer.write("\n");
            }

            //saves recording name:
            addRecord(saveFileName);
            System.out.println("Race recording has been saved successfully\nYou can watch it in the statistics menu\n");
        } catch (IOException e) {
            System.out.println("Could not save teh recording");
        }
        ;
    }

    /**
     * Loads recording of a race from a text file
     *
     * @param saveFileName name of the file
     * @return the race that was recorded
     */
    public static Race loadRaceRecord(String saveFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFileName))) {
            //loads number of lanes:
            int lanesNum = Integer.parseInt(reader.readLine());
            //loads distance and initialises the race:
            Race recordedRace = new Race(Integer.parseInt(reader.readLine()), lanesNum);

            String s;
            //loads horses:
            for (int i = 1; i <= lanesNum && (s = reader.readLine()) != null; i++) {
                Horse newHorse = new Horse(s.charAt(0), s.substring(1));
                recordedRace.addHorse(newHorse, i);
            }

            //load horse steps:
            for (Horse horse : recordedRace.horseLanes) {
                horse.loadCurrentRaceRecord(reader.readLine());
            }

            return recordedRace;
        } catch (IOException e) {
            System.out.println("Could not load the recording");
        }
        return null;
    }

    /**
     * Saves all recording names to text file
     */
    public static void saveRecordingNames() {
        try (FileWriter writer = new FileWriter("recordings.txt")) {
            for (String fileName : recordFileNames) {
                writer.write(fileName + "\n");
            }
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    /**
     * Loads all recording names from a text file
     *
     * @return ArrayList with every recording name
     */
    private static ArrayList<String> loadRecordingNames() {
        try (BufferedReader reader = new BufferedReader(new FileReader("recordings.txt"))) {
            ArrayList<String> loadedRecords = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                loadedRecords.add(line);
            }
            return loadedRecords;
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    /**
     * adds money bonus for conducting a race
     */
    private static void raceMoneyBonus() {
        System.out.println("You have received " + moneyPerRace + " money bonus for conducting the race");
        addMoney(moneyPerRace);
    }
}
