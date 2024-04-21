import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A horse race, each horse running in its own lane
 * for a given distance
 *
 * @author Nikita Topolskis
 * @version 2.0
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
    private int weatherCondition = 1;


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


    public void setRaceLength(int newLength) {
        raceLength = newLength;
    }

    public int getRaceLength() {
        return raceLength;
    }

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

    public static ArrayList<String> getRecordFileNames() {
        return recordFileNames;
    }

    public static int getNumberOfRecords() {
        return recordFileNames.size();
    }

    public static int getMoney() {return money;}

    public static void addMoney(int moneyBonus) {money += moneyBonus;}

    public static void subtractMoney(int moneyLoss) {money -= moneyLoss;}


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

    public void raceSetup() {
        //check if every lane has a horse
        for (Horse horse : horseLanes) {
            if (horse == null) {
                System.out.println("Cannot start race, since not all lanes are filled");
                return;
            }
        }
        weatherCondition = (int) (Math.random() * 2) + 1;
        switch (weatherCondition) {
            case 1 -> System.out.println("The weather is good\n");
            case 2 -> System.out.println("It is raining, chances of falling are doubled");
            case 3 -> System.out.println("The weather is disastrous, chances of falling are tripled");
        }
        betAmount = 0;
        betLaneIndex = -1;
        char choice = Menu.inputChar("\nDo you want to gamble?\n");
        if (choice == 'y') gamble();
        Menu.input("\nPress enter to start the race");
        startRace();
    }

    public void gamble() {
        printHorseLanes();
        printMoney();
        while (betLaneIndex < 0 || betLaneIndex >= horseLanes.length) {
            betLaneIndex = Menu.inputInt("\nEnter lane number to bet on: ") - 1;
        }
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
                horse.addStepToCurrentRaceRecord('w');
                totalFinishes++;
                winnerExists = true;
                if (betAmount > 0) {
                    if (horseLanes[betLaneIndex].equals(horse)) {
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
        if (!winnerExists) {
            System.out.println("\n No Winner - all the horses failed to finish the race.");
            System.out.println("\nYou have lost your bet of " + betAmount);
        }

        raceMoneyBonus();
        printMoney();
        System.out.print("\nEnter record name to save the race: ");
        String recordName = new Scanner(System.in).nextLine();
        saveRaceRecord(recordName);
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

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also depend exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
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
        System.out.println("\nThe Winner of the race is " + winnerHorse.getName() + "!\n");
    }

    public void printHorseLanes() {
        double totalWinRate = 0.0;
        boolean unpredictable = false;
        for (Horse horse : horseLanes) {
            if (horse.getTotalRaces() == 0) unpredictable = true;
            totalWinRate += horse.getWinRate();
        }
        if (totalWinRate == 0.0) {unpredictable = true;}
        int i = 1;
        System.out.println("Lanes:");
        if (unpredictable) {
            String prediction = null;
            for (Horse horse : horseLanes) {
                if (horse.getTotalRaces() == 0) prediction = "Unknown";
                else if (horse.getWinRate() == 0) prediction = "Low";
                else if (horse.getWinRate() / totalWinRate > 0.5) prediction = "High";
                else prediction = "Medium";
                System.out.println(i++ + " " + horse.getName() + " (" + horse.getBreed() + ") " + horse.getSymbol() + " - " + horse.getConfidence() + "   [Predicted Win Chance: " + prediction + "]");
            }
        }
        else {
            for (Horse horse : horseLanes) {
                System.out.println(i++ + " " + horse.getName() + " (" + horse.getBreed() + ") " + horse.getSymbol() + " - " + horse.getConfidence() + "   [Predicted Win Chance: " + (horse.getWinRate() / totalWinRate) + "]");
            }
        }
    }

    public static void printMoney() {
        System.out.println("Money: " + money);
    }

    public static void printOverallStats() {
        System.out.println("\n---Overall Stats---");
        System.out.println("Total Races: " + totalRaces);
        System.out.println("Total Finishes " + totalFinishes);
        System.out.println("Current Number of Horses: " + Horse.getTotalHorseNumber());
        Horse topHorse = Horse.getTopHorse();
        System.out.println("Top Horse: " + topHorse.getName() + " " + topHorse.getSymbol() + " with " + topHorse.getTotalWins() + " wins");
    }

    public void watchRecording() {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        int stepCount = 0;
        char c;

        //reset all the horseLanes (all horses not fallen and back to 0) and increment total number of taken races.
        for (Horse horse : horseLanes) {
            horse.goBackToStart();
            System.out.println(horse.getCurrentRaceRecord());
        }

        while (!finished) {
            //move each horse
            for (Horse horse : horseLanes) {
                if (stepCount < horse.getCurrentRaceRecord().size()) {
                    c = horse.getCurrentRaceRecord().get(stepCount);
                    if (c == 'm') {
                        horse.moveForward();
                    } else if (c == 'f') {
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
    }

    public void saveRaceRecord(String saveFileName) {
        try (FileWriter writer = new FileWriter(saveFileName + ".txt")) {
            writer.write(horseLanes.length + "\n");
            writer.write(raceLength + "\n");

            for (Horse horse : horseLanes) {
                writer.write(horse.getSymbol());
                writer.write(horse.getName());
                writer.write("\n");
            }
            for (Horse horse : horseLanes) {
                String s = horse.getCurrentRaceRecord().toString().replace(", ", "").replace(", ", "").replace("[", "").replace("]", "");
                writer.write(s);
                writer.write("\n");
            }
            recordFileNames.add(saveFileName);
            System.out.println("Race recording has been saved successfully\nYou can watch it in the statistics menu\n");
        } catch (IOException e) {
            System.out.println("Could not save teh recording");
        }
        ;
    }

    public static Race loadRaceRecord(String saveFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFileName))) {
            int lanesNum = Integer.parseInt(reader.readLine());
            Race recordedRace = new Race(Integer.parseInt(reader.readLine()), lanesNum);
            String s;
            for (int i = 1; i <= lanesNum && (s = reader.readLine()) != null; i++) {
                Horse newHorse = new Horse(s.charAt(0), s.substring(1));
                recordedRace.addHorse(newHorse, i);
            }
            for (Horse horse : recordedRace.horseLanes) {
                horse.loadCurrentRaceRecord(reader.readLine());
            }

            return recordedRace;
        } catch (IOException e) {
            System.out.println("Could not load the recording");
        }
        return null;
    }

    public static void saveRecordingNames() {
        try (FileWriter writer = new FileWriter("recordings.txt")) {
            for (String fileName : recordFileNames) {
                writer.write(fileName + "\n");
            }
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    public static ArrayList<String> loadRecordingNames() {
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

    public static void raceMoneyBonus() {
        System.out.println("You have received " + moneyPerRace + " money bonus for conducting the race");
        addMoney(moneyPerRace);
    }
}
