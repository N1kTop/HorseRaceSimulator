import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance.
 * Race object can be initialised with different race lengths.
 * At the end of the race the winner is displayed.
 *
 * @author Nikita Topolskis
 * @version 2.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private static char fallenSymbol = 'X';


    /**
     * Constructor for objects of class Race
     * Can be initialized with none, 1, 2 or 3 horses already in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     * @param horse1 horse in the first lane
     * @param horse2 horse in the second lane
     * @param horse3 horse in the third lane
     */
    public Race(int distance, Horse horse1, Horse horse2, Horse horse3)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = horse1;
        lane2Horse = horse2;
        lane3Horse = horse3;
    }
    public Race(int distance, Horse horse1, Horse horse2)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = horse1;
        lane2Horse = horse2;
        lane3Horse = null;
    }
    public Race(int distance, Horse horse1)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = horse1;
        lane2Horse = null;
        lane3Horse = null;
    }
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }


    //Accessor methods:
    public void setRaceLength(int newLength) {raceLength = newLength;}

    public int getRaceLength() {return raceLength;}

    public static void setFallenSymbol(char newSymbol) {fallenSymbol = newSymbol;}

    public static char getFallenSymbol() {return fallenSymbol;}

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     * finally, winning message printed
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //reset all the lanes (all horses not fallen and back to 0).
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();

        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);

            //print the race positions
            printRace();

            //if any of the three horses has won, the race is finished
            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
            {
                finished = true;
            }

            //if all the horses have fallen, the race is finished
            if ( lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen() )
            {
                finished = true;
            }

            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch(Exception e){e.printStackTrace();}
        }

        finaliseResult();
    }

    /**
     * checks for who won the race and runs printWinner() method to display the winning horse
     * if all the horses have fallen - no winner
     * if multiple horses finish at the same time - winner is decided by random
     */
    private void finaliseResult() {
        //initialise winning horse
        Horse winningHorse = null;

        //check if horse1 finished the race
        if (raceWonBy(lane1Horse)) {
            winningHorse = lane1Horse; //set horse1 as the winner
        }

        //check if horse2 finished teh race
        if (raceWonBy(lane2Horse)) {
            if (winningHorse != null) { //check if there already is a winning horse
                Random random = new Random();
                if (random.nextInt(2) == 1) { //flip a coin to decide which horse wins
                    winningHorse = lane2Horse;
                }
            }
            //set horse2 as the winner
            else winningHorse = lane2Horse;
        }

        //check if horse3 finished teh race
        if (raceWonBy(lane3Horse)) {
            if (winningHorse != null) { //check if there already is a winning horse
                Random random = new Random();
                if (random.nextInt(2) == 1) { //flip a coin to decide which horse wins
                    winningHorse = lane3Horse;
                }
            }
            //set horse3 as the winner
            else winningHorse = lane3Horse;
        }

        //check if winner exists
        if (winningHorse == null) {
            System.out.println("\nNo Winner - all the horses failed to finish the race."); //all the horses have lost
            return;
        }

        //prints the winner
        printWinner(winningHorse);
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen

        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                theHorse.moveForward();
            }

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also depend exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        printLane(lane1Horse);
        System.out.println();

        printLane(lane2Horse);
        System.out.println();

        printLane(lane3Horse);
        System.out.println();

        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print(fallenSymbol);
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }

        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);

        //print the | for the end of the track
        System.out.print('|');

        //print horse name and confidence
        System.out.print("     " + theHorse.getName() + " (" + theHorse.getConfidenceFormatted() + ")");
    }


    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
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
}
