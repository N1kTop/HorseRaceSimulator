
/**
 * Horse class, stores information about the horse,
 * such as name, symbol to display during the race and confidence factor.
 *
 * @author Nikita Topolskis
 * @version v2.0
 */
public class Horse
{
    //Fields of class Horse:
    private String name;

    //symbol: character that represents the horse during a race
    private char symbol;

    //confidence: value between 0.0 and 1.0 that increases chances of moving forward and falling
    private double confidence;

    //distance: distance travelled during current race
    private int distance = 0;

    //fallen: if horse has fallen during current race
    private boolean fallen = false;


    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse.
     * Can be initialised with or without a set confidence.
     * If confidence not specified, it is set to default 0.5.
     *
     * @param horseSymbol symbol that represents the horse during a race
     * @param horseName name of the horse
     * @param horseConfidence confidence factor influence horse speed and falling chances
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

    /**
     * if the horse is not fallen, reduce its confidence by 0.1 and set it to fallen state
     * if confidence is smaller than 0.2, it becomes 0.1 (lowest limit)
     */
    public void fall()
    {
        if (fallen) return;
        if (confidence > 0.2) confidence -= 0.1;
        else confidence = 0.1;
        fallen = true;
    }

    /**
     * increases confidence of the horse
     * if confidence is higher than 0.9, it becomes 1.0 (upper limit)
     */
    public void win()
    {
        if (confidence < 0.9) confidence += 0.1;
        else confidence = 1.0;
    }

    /**
     * sets the horse to not fallen state with 0 distance travelled
     */
    public void goBackToStart()
    {
        fallen = false;
        distance = 0;
    }

    // accessor methods:
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

    public boolean hasFallen() {return fallen;}

    public void moveForward() {distance++;}

}
