import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Horse class, stores information about the horse,
 * such as name, symbol to display during the race, confidence factor and customisation choices.
 * All created horses are stored in an ArrayList.
 *
 * @author Nikita Topolskis
 * @version v3.0
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
    private ArrayList<Integer> finishingTimes = new ArrayList<>(); //IMPROVE: finish this
    private static int horseCost = 250;
    private final static int horseCostMultiplier = 2; //after buying a horse, horse price multiplies by this factor

    public static final Horse[] defaultHorses = {new Horse('♘', "Anya", 0.4, "Arabian"), new Horse('♞', "Oliver", 0.5, "Friesian"), new Horse('♔', "King", 0.6, "Appaloosa")};
    private static ArrayList<Horse> allHorses = new ArrayList<>(Arrays.asList(defaultHorses)); //ArrayList containing every created horse
    private final static String[] breedChoices = {"Arabian", "Friesian", "Mustang Shire", "Thoroughbred", "Appaloosa", "American Quarter", "Clydesdale", "Breton", "Cob", "American Paint", "Rahvan"};
    private final static String[] colorChoices = {"Brown", "Red", "Orange", "Yellow", "Green", "Lime", "Aqua", "Turquoise", "Blue", "Purple", "Pink", "Black", "Grey", "White", "Coffee"};
    private final static String[] shopAccessories = {"None", "Lucky Charm", "Amulet of Speed", "Viking Helmet", "Advanced Saddle", "Chain Armor", "Top Hat", "Champion Crown"};
    private final static int[] shopPrices = {0, 100, 200, 400, 600, 800, 1000, 1200};
    private static boolean[] ownedAccessories = initializeItems(); //boolean representing if the item of that index is owned or not


    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse.
     * Can be initialised with or without a set confidence and breed.
     * If confidence not specified, it is set to default 0.5.
     * If breed not specified, the default option is chosen (Arabian).
     *
     * @param horseSymbol symbol that represents the horse during a race
     * @param horseName name of the horse
     * @param horseConfidence confidence factor influence horse speed and falling chances
     * @param horseBreed breed of the horse (can not be changed later)
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseBreed)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
        breed = horseBreed;
    }
    public Horse(char horseSymbol, String horseName, String horseBreed)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = 0.5;
        breed = horseBreed;
    }
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
        breed = breedChoices[0];
    }
    public Horse(char horseSymbol, String horseName)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = 0.5;
        breed = breedChoices[0];
    }



    //Other methods of class Horse

    /**
     * if the horse is not fallen, reduce its confidence by 0.1 and set it to fallen state
     * if confidence is smaller than 0.1, it becomes 0.0 (lowest limit)
     */
    public void fall()
    {
        if (fallen) return;
        if (confidence > 0.1) confidence -= 0.1;
        else confidence = 0.0;
        fallen = true;
        incTotalFalls();
    }

    /**
     * increases confidence of the horse
     * if confidence is higher than 0.9, it becomes 1.0 (upper limit)
     */
    public void win()
    {
        if (confidence < 0.9) confidence += 0.1;
        else confidence = 1.0;
        incTotalWins();
    }

    /**
     * sets the horse to not fallen state with 0 distance travelled
     */
    public void goBackToStart()
    {
        fallen = false;
        distance = 0;
    }


    //Accessor methods:
    public double getConfidence()
    {
        return confidence;
    }

    public String getConfidenceFormatted() {return String.format( "%.1f", confidence);}

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

    public boolean hasFallen()
    {
        return fallen;
    }

    public void moveForward()
    {
        distance++;
    }

    public void setName(String newName) {name = newName;}

    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }

    public String getBreed() {return breed;}
    public String getCoatColor() {return coatColor;}
    public String getAccessory() {return accessory;}
    public void setCoatColor(String newColor) {coatColor = newColor;}
    public void setAccessory(String newAcc) {accessory = newAcc;}

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

    //Race records:
    public ArrayList<Character> getCurrentRaceRecord() {return currentRaceRecord;}

    public void addStepToCurrentRaceRecord(char step) {currentRaceRecord.add(step);}

    public void clearCurrentRaceRecord() {currentRaceRecord = new ArrayList<>();}

    /**
     * loads String steps into the currentRaceRecord
     * @param steps String containing one horses recording of the race
     */
    public void loadCurrentRaceRecord(String steps) {
        currentRaceRecord = new ArrayList<Character>();
        for (char c : steps.toCharArray()) {
            currentRaceRecord.add(c);
        }
    }

    /**
     * prints different horse information
     */
    public void printHorseInfo() {
        System.out.println("\n---Horse Info---");
        System.out.println("Name: " + getName());
        System.out.println("Symbol: " + getSymbol());
        System.out.println("Confidence: " + getConfidence());
        System.out.println("Breed: " + getBreed());
        System.out.println("Coat Color: " + getCoatColor());
        System.out.println("Accessory: " + getAccessory());
    }

    /**
     * prints different horse stats
     */
    public void printHorseStats() {
        System.out.println("\n---Horse Statistic---");
        System.out.println("Total Distance: " + getTotalDistance());
        System.out.println("Average Speed: " + getAverageSpeed());
        System.out.println("Wins: " + getTotalWins());
        System.out.println("Races: " + getTotalRaces());
        System.out.println("Falls: " + getTotalFalls());
        System.out.println("Win Rate: " + getWinRate());
        System.out.println("\nFinishing Times:\n" + finishingTimes);
    }

    public static int getHorseCost() {return horseCost;}

    public static void multiplyHorseCost() {horseCost *= horseCostMultiplier;}

    public static void divideHorseCost() {horseCost /= horseCostMultiplier;}

    public static ArrayList<Horse> getAllHorses() {
        return allHorses;
    }

    //returns horse at specified index:
    public static Horse getHorse(int horseIndex) {return allHorses.get(horseIndex);}

    //adds new horse to collection:
    public static void addHorse(Horse newHorse) {
        allHorses.add(newHorse);
    }

    //removes horse at specified index from the collection:
    public static void removeHorse(int horseIndex) {allHorses.remove(horseIndex);}

    //removes horse from the collection:
    public static void removeHorse(Horse theHorse) {allHorses.remove(theHorse);}

    /**
     * prints list of all the horses numbered
     */
    public static void printHorses() {
        int count = 1;
        System.out.println("\nList of all horses:");
        for (Horse horse : allHorses) {
            System.out.println(count++ + " " + horse.getName() + " (" + horse.getBreed() + ") " + horse.getSymbol() + " - " + horse.getConfidence());
        }
        System.out.println("");
    }

    public static int getTotalHorseNumber() {return allHorses.size();}

    //returns breed choice at specified index:
    public static String getBreedChoice(int breedIndex) {return breedChoices[breedIndex];}

    //returns color choice at specified index:
    public static String getColorChoice(int colorIndex) {return colorChoices[colorIndex];}

    //returns accessory at specified index:
    public static String getAccessory(int accessoryIndex) {return shopAccessories[accessoryIndex];}

    public static int getAccessoryPrice(int index) {return shopPrices[index];}

    public static int getNumberOfShopItems() {return shopAccessories.length;}

    //returns if the accessory at specified index is owned or not
    public static boolean accessoryOwned(int accessoryIndex) {return ownedAccessories[accessoryIndex];}

    public static int getBreedChoicesLength() {return breedChoices.length;}

    public static int getColorChoicesLength() {return colorChoices.length;}

    public static int getNumberOfOwnedAccessories() {
        int count = 0;
        for (boolean owned : ownedAccessories) {
            if (owned) count++;
        }
        return count;
    }

    /**
     * buys a specified accessory from shop
     *
     * @param accessoryIndex index of accessory to buy
     */
    public static void buyAccessory(int accessoryIndex) {
        int price = shopPrices[accessoryIndex];
        if (Race.getMoney() >= price) { //check if enough money
            ownedAccessories[accessoryIndex] = true; //set owned to true
            Race.subtractMoney(price); //subtract money for the item
            System.out.println("\nYou have bought: " + shopAccessories[accessoryIndex]);
        }
        else { //if not enough money
            System.out.println("\nNot enough money (" + Race.getMoney() + "/" + shopPrices[accessoryIndex] + ")");
        }
    }

    /**
     * prints breed choices to select from numbered from 1
     */
    public static void printBreedChoices() {
        int count = 1;
        for (String breedChoice : breedChoices) {
            System.out.println(count++ + " " + breedChoice);
        }
    }

    /**
     * prints coat colour's choices to select from numbered from 1
     */
    public static void printColorChoices() {
        int count = 1;
        for (String colorChoice : colorChoices) {
            System.out.println(count++ + " " + colorChoice);
        }
    }

    /**
     * prints owned accessory choices to select from
     */
    public static void printAccessoryChoices() {
        int count = 1;
        for (String item : shopAccessories) {
            if (ownedAccessories[count-1]) System.out.println(count + " " + item);
            count++;
        }
    }

    /**
     * prints all available items in the shop with corresponding prices
     * already acquired items are skipped when listing
     */
    public static void printShop() {
        System.out.println("\n---Shop---");
        for (int i = 1; i < shopAccessories.length && i < shopPrices.length; i++) {
            if (!ownedAccessories[i]) System.out.println(i + " " + shopAccessories[i] + ": " + shopPrices[i]);
        }
    }

    /**
     * initialises default owned items (all false except from initialItems[0])
     *
     * @return boolean array representing initially unlocked accessories
     */
    public static boolean[] initializeItems() {
        boolean[] initialItems = new boolean[shopAccessories.length];
        initialItems[0] = true;
        for (int i = 1; i < initialItems.length; i++) {
            initialItems[i] = false;
        }
        return initialItems;
    }

    /**
     * @return the horse with the most wins
     */
    public static Horse getTopHorse() {
        Horse topHorse = allHorses.get(0);
        for (Horse horse : allHorses) {
            if (horse.totalWins > topHorse.totalWins) topHorse = horse;
        }
        return topHorse;
    }
}
