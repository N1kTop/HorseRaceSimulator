import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class that uses Race and Horse classes to perform races
 */
public class Main {

    /**
     * Prints message and receives user input.
     * Input validation is present.
     *
     * @param message to be printed before waiting for input
     * @return number entered by the user
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

    /**
     * Endlessly performs races, the race length is chosen by the user.
     * Enter 0 to exit.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        int distance;
        Race race;

        //create horses
        Horse horse1 = new Horse('♘', "Anya", 0.4);
        Horse horse2 = new Horse('♞', "Oliver", 0.5);
        Horse horse3 = new Horse('♔', "King", 0.6);

        //input race length
        while ((distance = inputInt("Enter Race Length (0 to exit): ")) > 0) {
            if (distance > 200) {
                System.out.println("Too large distance");
                continue;
            }
            //create race and add horses
            race = new Race(distance);
            race.addHorse(horse1, 1);
            race.addHorse(horse2, 2);
            race.addHorse(horse3, 3);

            //run race
            race.startRace();
        }
    }
}