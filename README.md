**---Horse Race Simulator---**

Welcome to the Horse Race Simulator! This Java-based application simulates dynamic horse races where horses compete over various lengths and tracks. Each horse has customisable attributes such as names, cosmetics and confidence levels that directly influence their performance. The confidence of a horse changes based on their race results, adding a layer of realism and strategy to the simulation.

**Features**

Variable Race Lengths: Simulate races on different track lengths.
Customisable Horses: Choose names and accessories for each horse.
Dynamic Confidence: Horses' confidence levels adjust based on race outcomes, influencing future performances.
Statistics: Track various metrics for each race and eac horse.
Gambling: Bet money on horses during races.
Recordings: View past races that have been saved.
User Interface: This version has both the textual and the GUI interfaces.

**Prerequisites**

Ensure you have Java installed on your machine. You can check your Java version by running:
java -version

**Installation**

Download the source code from the repository

**Running the Program**

Navigate to the project directory:
cd HorseRaceSimulator/Part2

To run the program, execute the following command from the project root directory:
java -cp out/production/Part2 Main

This command sets the classpath to the compiled classes in the out/production/Part2 directory and runs the Main class.

Alternatively, you could navigate to the output directory and run the code from there:
cd HorseRaceSimulator/Part1/out/production/Part2
java Main

If you are using an IDE, simply open Main.java and run the main() method.

**Usage**

Upon starting the simulator, you will be asked to choose between textual and GUI version of the program. To start a race, from the main menu choose "Race", enter race length and number of lanes, then choose desired horses. You will be given an option to gamble, after which the race will start. You can customise horses in "Horses Stables" menu. Additionally, you can view recordigns of past races in the "Satistics" menu.

**Note**

Default horse symbols can not be read by some terminals.




Thank you for checking the Horse Race Simulator!
