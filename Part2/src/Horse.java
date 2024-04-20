import java.util.ArrayList;

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
    //Customisation:
    private String breed;
    private String coatColor;
    private String accessory;
    //Statistics:
    private int totalWins = 0;
    private int totalRaces = 0;
    private int totalFalls = 0;
    private int totalDistance = 0;
    private int totalTime = 0;
    private int raceDistance;
    private int raceTime;
    private ArrayList<Integer> finishingTimes;



    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
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
}
