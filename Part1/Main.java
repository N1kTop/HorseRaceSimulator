import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

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
    public static void main(String[] args) {
        int distance;
        Race race;
        Horse horse1 = new Horse('♘', "Anya", 0.4);
        Horse horse2 = new Horse('♞', "Oliver", 0.5);
        Horse horse3 = new Horse('♔', "King", 0.6);
        while ((distance = inputInt("Enter Race Length (0 to exit): ")) > 0) {
            if (distance > 200) {
                System.out.println("Too large distance");
                continue;
            }
            race = new Race(distance);
            race.addHorse(horse1, 1);
            race.addHorse(horse2, 2);
            race.addHorse(horse3, 3);
            race.startRace();
        }
    }
}