public class Main {
    public static void main(String[] args) {
        Horse horse1 = new Horse('♘', "Anya", 0.3);
        Horse horse2 = new Horse('♞', "Oliver", 0.5);
        Horse horse3 = new Horse('♕', "#32424", 0.8);
        Race race1 = new Race(50);
        race1.addHorse(horse1, 1);
        race1.addHorse(horse2, 2);
        race1.addHorse(horse3, 3);
        race1.startRace();

        Race race2 = new Race(30);
        race2.addHorse(horse1, 1);
        race2.addHorse(horse2, 2);
        race2.addHorse(horse3, 3);
        race2.startRace();
    }
}