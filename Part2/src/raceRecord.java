public class raceRecord {
    private int raceLength;
    private char[] horseSymbols;
    private boolean[] horseActions;
    private String winner;
    private char fallenSymbol;
    private char fenceSymbol;


    public raceRecord(int distance, char[] horses, boolean[] actions, String w, char fallen, char fence) {
        raceLength = distance;
        horseSymbols = horses;
        horseActions = actions;
        winner = w;
        fallenSymbol = fallen;
        fenceSymbol = fence;
    }
}
