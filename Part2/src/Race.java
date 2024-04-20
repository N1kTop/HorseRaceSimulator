import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author Nikita Topolskis
 * @version 2.0
 */
public class Race
{
    private int raceLength;
    private Horse[] horseLanes;
    private static char fallenSymbol = 'X';
    private static char fenceSymbol = '=';
    private static ArrayList<raceRecord> records = new ArrayList<raceRecord>();


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the horseLanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int lanes_num, Horse[] horses)
    {
        // initialise instance variables
        raceLength = distance;
        horseLanes = new Horse[lanes_num];

        // put horses into horseLanes
        for (int i = 0; i < horses.length && i < horseLanes.length; i++) {
            horseLanes[i] = horses[i];
        }

    }

    public Race(int distance, int lanes_num)
    {
        // initialise instance variables
        raceLength = distance;
        horseLanes = new Horse[lanes_num];
    }

    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        horseLanes = new Horse[3];
    }


    public void setRaceLength(int newLength) {raceLength = newLength;}

    public int getRaceLength() {return raceLength;}

    public static void setFallenSymbol(char newSymbol) {fallenSymbol = newSymbol;}

    public static char getFallenSymbol() {return fallenSymbol;}

    public static void setFenceSymbol(char newSymbol) {fenceSymbol = newSymbol;}

    public static char getFenceSymbol() {return fenceSymbol;}

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        laneNumber--;
        if (laneNumber >= 0 && laneNumber < horseLanes.length) {
            horseLanes[laneNumber] = theHorse;
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
        //check if every lane has a horse
        for (Horse horse : horseLanes) {
            if (horse == null) {
                System.out.println("Cannot start race, since not all lanes are filled");
                return;
            }
        }

        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //reset all the horseLanes (all horses not fallen and back to 0).
        for (Horse horse : horseLanes) {
            horse.goBackToStart();
        }

        while (!finished)
        {
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
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch(Exception e) {e.printStackTrace();}
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

        multiplePrint(fenceSymbol,raceLength+3); //top edge of track
        System.out.println();

        for (Horse horse : horseLanes) {
            printLane(horse);
            System.out.println();
        }

        multiplePrint(fenceSymbol,raceLength+3); //bottom edge of track
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
