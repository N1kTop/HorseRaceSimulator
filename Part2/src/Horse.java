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
    private int total_wins;
    private int total_races;
    private int total_distance;
    private int total_time;
    private ArrayList<Integer> finishing_times;



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

    public int getTotal_wins() {return total_wins;}
    public int getTotal_races() {return total_races;}
    public int getTotal_distance() {return total_distance;}
    public int getTotal_time() {return total_time;}
    public double getAverage_speed() {return (double) total_distance / (double) total_time;}

    public void setTotal_wins(int newWins) {total_wins = newWins;}
    public void setTotal_races(int newRaces) {total_races = newRaces;}
    public void incTotal_wins() {total_wins++;}
    public void incTotal_races() {total_races++;}
    public double getWinRate() {return (double) total_wins / (double) total_races;}
    public ArrayList<Integer> getFinishing_times() {return finishing_times;}
    public void addFinishingTime(int newTime) {finishing_times.add(newTime);}
}
