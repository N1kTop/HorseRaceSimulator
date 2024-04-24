
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


    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     * @param horseSymbol
     * @param horseName
     * @param horseConfidence
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
    }

    /**
     * Constructor for Horse without argument confidence, confidence is set to default 0.5
     * @param horseSymbol
     * @param horseName
     */
    public Horse(char horseSymbol, String horseName)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = 0.5;
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
    }

    /**
     * increases confidence of teh horse
     * if confidence is higher than 0.9, it becomes 1.0 (upper limit)
     */
    public void win()
    {
        if (confidence < 0.9) confidence += 0.1;
        else confidence = 1.0;
    }

    // accessor methods:
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

    public void moveForward() {distance++;}

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;
    }

    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }

}
