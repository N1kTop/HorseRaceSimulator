import java.util.ArrayList;
import java.util.Arrays;

/**
 * Write a description of class Horse here.
 *
 * @author Nikita Topolskis
 * @version v2
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private double confidence;
    private int distance = 0;
    private boolean fallen = false;
    private ArrayList<Character> currentRaceRecord;
    //Customisation:
    private String breed;
    private String coatColor = "Brown";
    private String accessory = "None";
    //Statistics:
    private int totalWins = 0;
    private int totalRaces = 0;
    private int totalFalls = 0;
    private int totalDistance = 0;
    private int totalTime = 0;
    private ArrayList<Integer> finishingTimes;

    public static final Horse[] defaultHorses = {new Horse('♘', "Anya", 0.4, "Arabian"), new Horse('♞', "Oliver", 0.5, "Friesian"), new Horse('♔', "King", 0.6, "Appaloosa")};
    private static ArrayList<Horse> allHorses = new ArrayList<>(Arrays.asList(defaultHorses));
    private final static String[] breedChoices = {"Arabian", "Friesian", "Mustang Shire", "Thoroughbred", "Appaloosa", "American Quarter", "Clydesdale", "Breton", "Cob", "American Paint", "Rahvan"};
    private final static String[] colorChoices = {"Brown", "Red", "Orange", "Yellow", "Green", "Lime", "Aqua", "Turquoise", "Blue", "Purple", "Pink", "Black", "Grey", "White", "Coffee"};
    private final static String[] accessoryChoices = {"None", "Lucky Charm", "Amulet of Speed", "Viking Helmet", "Advanced Saddle", "Chain Armor"};

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseBreed)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
        breed = horseBreed;
    }

    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
    }

    public Horse(char horseSymbol, String horseName)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = 0.5;
    }



    //Other methods of class Horse
    public void fall()
    {
        if (fallen) return;
        if (confidence > 0.1) confidence -= 0.1;
        else confidence = 0.0;
        fallen = true;
    }

    public void win()
    {
        if (confidence < 0.9) confidence += 0.1;
        else confidence = 1.0;
    }

    public double getConfidence()
    {
        return confidence;
    }

    public int getDistanceTravelled()
    {
        return distance;
    }

    public String getName()
    {
        return name;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public void goBackToStart()
    {
        distance = 0;
    }

    public boolean hasFallen()
    {
        return fallen;
    }

    public void moveForward()
    {
        distance++;
    }

    public void setName(String newName) {name = newName;}

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;
    }

    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }

    public String getBreed() {return breed;}
    public String getCoatColor() {return coatColor;}
    public String getAccessory() {return accessory;}
    public void setCoatColor(String newColor) {coatColor = newColor;}
    public void setAccessory(String newAcc) {coatColor = accessory = newAcc;}

    public void incTotalDistance() {totalDistance++;}
    public void incTotalTime() {totalTime++;}
    public int getTotalWins() {return totalWins;}
    public int getTotalRaces() {return totalRaces;}
    public int getTotalDistance() {return totalDistance;}
    public int getTotalTime() {return totalTime;}
    public double getAverageSpeed() {
        if (totalDistance == 0) return 0;
        return (double) totalDistance / (double) totalTime;
    }

    public int getTotalFalls() {return totalFalls;}
    public void incTotalFalls() {totalFalls++;}
    public void incTotalWins() {totalWins++;}
    public void incTotalRaces() {totalRaces++;}
    public double getWinRate() {
        if (totalRaces == 0) return 0;
        return (double) totalWins / (double) totalRaces;
    }

    public ArrayList<Integer> getFinishingTimes() {return finishingTimes;}
    public void addFinishingTime(int newTime) {finishingTimes.add(newTime);}

    public ArrayList<Character> getCurrentRaceRecord() {return currentRaceRecord;}

    public void addStepToRecord(char step) {currentRaceRecord.add(step);}

    public void clearRaceRecord() {currentRaceRecord = new ArrayList<>();}

    public void printHorseInfo() {
        System.out.println("\n---Horse Info---");
        System.out.println("Name: " + name);
        System.out.println("Symbol: " + symbol);
        System.out.println("Confidence: " + confidence);
        System.out.println("Breed: " + breed);
        System.out.println("Coat Color: " + coatColor);
        System.out.println("Accessory: " + accessory);
    }

    public void printHorseStats() {
        System.out.println("\n---Horse Statistic---");
        System.out.println("Total Distance: " + totalDistance);
        System.out.println("Average Speed: " + getAverageSpeed());
        System.out.println("Wins: " + totalWins);
        System.out.println("Races: " + totalRaces);
        System.out.println("Win Rate: " + getWinRate());
        System.out.println("\nFinishing Times:\n" + finishingTimes);
    }


    public static ArrayList<Horse> getAllHorses() {
        return allHorses;
    }

    public static Horse getHorse(int horseIndex) {
        return allHorses.get(horseIndex);
    }

    public static void addHorse(Horse newHorse) {
        allHorses.add(newHorse);
    }

    public static void printHorses() {
        int count = 1;
        System.out.println("List of all horses:");
        for (Horse horse : allHorses) {
            System.out.println(count + " " + horse.name + " (" + horse.breed + ") " + horse.symbol + " - " + horse.confidence);
            count++;
        }
        System.out.println("");
    }

    public static int getTotalHorseNumber() {return allHorses.size();}

    public static String getBreedChoice(int breedIndex) {return breedChoices[breedIndex];}

    public static String getColorChoice(int colorIndex) {return colorChoices[colorIndex];}

    public static String getAccessoryChoice(int accessoryIndex) {return accessoryChoices[accessoryIndex];}

    public static int getBreedChoicesLength() {return breedChoices.length;}

    public static int getColorChoicesLength() {return colorChoices.length;}

    public static int getAccessoryChoicesLength() {return accessoryChoices.length;}

    public static void printBreedChoices() {
        int count = 1;
        for (String breedChoice : breedChoices) {
            System.out.println(count + " " + breedChoice);
            count++;
        }
    }

    public static void printColorChoices() {
        int count = 1;
        for (String colorChoice : colorChoices) {
            System.out.println(count + " " + colorChoice);
            count++;
        }
    }
    public static void printAccessoryChoices() {
        int count = 1;
        for (String accessoryChoice : accessoryChoices) {
            System.out.println(count + " " + accessoryChoice);
            count++;
        }
    }
}
