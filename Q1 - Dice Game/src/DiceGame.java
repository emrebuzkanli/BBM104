import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a simple dice game application.
 * Reads player information and dice rolls from an input file.
 * Plays the dice game, and writes the game results to an output file.
 */
public class DiceGame {

    public static void main(String[] args) {


        // Take input and output file paths from command-line arguments
        String inputFile = args[0];
        String outputFile = args[1];

        try (
                // Open input and output file
                BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile))
        ) {
            // Read the number of players and their names from the input file
            int numOfPlayers = Integer.parseInt(fileReader.readLine());
            String[] playerNames = fileReader.readLine().split(",");
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < numOfPlayers; i++) {
                players.add(new Player(playerNames[i]));
            }

            // Read dice rolls from the input file
            List<String> diceRolls = new ArrayList<>();
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    diceRolls.add(line);
                } else {
                    break;
                }
            }

            // Create a PlayDiceGame object with the list of players
            PlayDiceGame game = new PlayDiceGame(players);

            // Play the dice game and get the game results
            List<String> output = game.playGame(diceRolls);

            // Write the game results to the output file
            for (int i = 0; i < output.size(); i++) {
                fileWriter.write(output.get(i));
                if (i == output.size() - 1) {
                    break;
                } else {
                    fileWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
