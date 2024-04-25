import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    private ArrayList<Integer> finishingTimes = new ArrayList<>();

    private static int horseCost = 250;
    private final static int horseCostMultiplier = 2;

    public static final Horse[] defaultHorses = {new Horse('♘', "Anya", 0.4, "Arabian"), new Horse('♞', "Oliver", 0.5, "Friesian"), new Horse('♔', "King", 0.6, "Appaloosa")};
    private static ArrayList<Horse> allHorses = new ArrayList<>(Arrays.asList(defaultHorses));
    private final static String[] breedChoices = {"Arabian", "Friesian", "Mustang Shire", "Thoroughbred", "Appaloosa", "American Quarter", "Clydesdale", "Breton", "Cob", "American Paint", "Rahvan"};
    private final static String[] colorChoices = {"Brown", "Red", "Orange", "Yellow", "Green", "Lime", "Aqua", "Turquoise", "Blue", "Purple", "Pink", "Black", "Grey", "White", "Coffee"};
    private final static String[] shopAccessories = {"None", "Lucky Charm", "Amulet of Speed", "Viking Helmet", "Advanced Saddle", "Chain Armor", "Top Hat", "Champion Crown"};
    private final static int[] shopPrices = {0, 100, 200, 400, 600, 800, 1000, 1200};
    private static boolean[] ownedAccessories = initializeItems();


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
    }

    public Horse(char horseSymbol, String horseName)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = 0.5;
    }



    //Other methods of class Horse

    /**
     *
     */
    public void fall()
    {
        if (fallen) return;
        if (confidence > 0.1) confidence -= 0.1;
        else confidence = 0.0;
        fallen = true;
    }

    /**
     *
     */
    public void win()
    {
        if (confidence < 0.9) confidence += 0.1;
        else confidence = 1.0;
        totalWins++;
    }

    /**
     *
     * @return
     */
    public double getConfidence()
    {
        return confidence;
    }

    /**
     *
     * @return
     */
    public int getDistanceTravelled()
    {
        return distance;
    }

    /**
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return
     */
    public char getSymbol()
    {
        return symbol;
    }

    /**
     *
     */
    public void goBackToStart()
    {
        distance = 0;
        fallen = false;
    }

    /**
     *
     * @return
     */
    public boolean hasFallen()
    {
        return fallen;
    }

    /**
     *
     */
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

    public ArrayList<Character> getCurrentRaceRecord() {return currentRaceRecord;}

    public void addStepToCurrentRaceRecord(char step) {currentRaceRecord.add(step);}

    public void clearCurrentRaceRecord() {currentRaceRecord = new ArrayList<>();}

    public void loadCurrentRaceRecord(String steps) {
        currentRaceRecord = new ArrayList<Character>();
        for (char c : steps.toCharArray()) {
            currentRaceRecord.add(c);
        }
    }

    public static HashMap<String, Integer> loadShopAccessoryItems() {
        try (BufferedReader reader = new BufferedReader(new FileReader("AccessoryShop.txt"))) {
            HashMap<String, Integer> accessories = new HashMap<>();
            String line;
            String[] splitLine;
            while ((line = reader.readLine()) != null) {
                splitLine = line.split(":");
                accessories.put(splitLine[0], Integer.parseInt(splitLine[1]));
            }
            return accessories;
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    /**
     *
     */
    public void printHorseInfo() {
        System.out.println("\n---Horse Info---");
        System.out.println("Name: " + name);
        System.out.println("Symbol: " + symbol);
        System.out.println("Confidence: " + confidence);
        System.out.println("Breed: " + breed);
        System.out.println("Coat Color: " + coatColor);
        System.out.println("Accessory: " + accessory);
    }

    /**
     *
     */
    public void printHorseStats() {
        System.out.println("\n---Horse Statistic---");
        System.out.println("Total Distance: " + totalDistance);
        System.out.println("Average Speed: " + getAverageSpeed());
        System.out.println("Wins: " + totalWins);
        System.out.println("Races: " + totalRaces);
        System.out.println("Win Rate: " + getWinRate());
        System.out.println("\nFinishing Times:\n" + finishingTimes);
    }


    public static int getHorseCost() {return horseCost;}

    public static void multiplyHorseCost() {horseCost *= horseCostMultiplier;}

    public static void divideHorseCost() {horseCost /= horseCostMultiplier;}

    public static ArrayList<Horse> getAllHorses() {
        return allHorses;
    }

    public static Horse getHorse(int horseIndex) {
        return allHorses.get(horseIndex);
    }

    public static void addHorse(Horse newHorse) {
        allHorses.add(newHorse);
    }

    public static void removeHorse(int horseIndex) {allHorses.remove(horseIndex);}

    public static void removeHorse(Horse theHorse) {allHorses.remove(theHorse);}

    /**
     *
     */
    public static void printHorses() {
        int count = 1;
        System.out.println("\nList of all horses:");
        for (Horse horse : allHorses) {
            System.out.println(count++ + " " + horse.name + " (" + horse.breed + ") " + horse.symbol + " - " + horse.confidence);
        }
        System.out.println("");
    }

    public static int getTotalHorseNumber() {return allHorses.size();}

    public static String getBreedChoice(int breedIndex) {return breedChoices[breedIndex];}

    public static String getColorChoice(int colorIndex) {return colorChoices[colorIndex];}

    public static String getAccessory(int accessoryIndex) {return shopAccessories[accessoryIndex];}

    public static int getAccessoryPrice(int index) {return shopPrices[index];}

    public static int getNumberOfShopItems() {return shopAccessories.length;}

    public static boolean accessoryOwned(int accessoryIndex) {return ownedAccessories[accessoryIndex];}

    /**
     *
     * @param accessoryIndex
     */
    public static void buyAccessory(int accessoryIndex) {
        int price = shopPrices[accessoryIndex];
        if (Race.getMoney() >= price) {
            ownedAccessories[accessoryIndex] = true;
            Race.subtractMoney(price);
            System.out.println("\nYou have bought: " + shopAccessories[accessoryIndex]);
        }
        else {
            System.out.println("\nNot enough money (" + Race.getMoney() + "/" + shopPrices[accessoryIndex] + ")");
        }
    }

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
     * returns boolean array representing initially unlocked accessories
     * @return
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
     *
     * @return
     */
    public static Horse getTopHorse() {
        Horse topHorse = allHorses.get(0);
        for (Horse horse : allHorses) {
            if (horse.totalWins > topHorse.totalWins) topHorse = horse;
        }
        return topHorse;
    }
}
