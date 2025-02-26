import java.util.ArrayList;
import java.util.List;
/*
 * Represents a game where players roll dice.
 */
public class PlayDiceGame {
    private final List<Player> allPlayers;
    private final List<Player> activePlayers;
    /*
     * Constructs a new game with the provided players.
     */
    public PlayDiceGame(List<Player> players) {
        this.allPlayers = new ArrayList<>(players);
        this.activePlayers = new ArrayList<>(players);
    }
/**
 * Plays the game with the given sequence of dice throws.
*/
public List<String> playGame(List<String> diceThrows) {
    List<String> output = new ArrayList<>(); // Stores game messages
    int throwIndex = 0; // Index to keep track of which dice is being processed

    while (activePlayers.size() > 1) {
        // Iterate over each player
        for (Player currentPlayer : allPlayers) {
            // Check if the player is still active
            if (activePlayers.contains(currentPlayer)) {
                // Process the turn for the current player
                output.add(playTurn(currentPlayer, diceThrows.get(throwIndex++)));

                // Check if only one active player remains after this turn
                if (activePlayers.size() == 1) {
                    // Pick the winner of the game
                    Player winner = activePlayers.get(0);
                    output.add(winner.getPlayersName() + " is the winner of the game with the score of " + winner.getPlayersScore() + ". Congratulations " + winner.getPlayersName() + "!");
                    return output; // Exit the method and end the game
                }
            }
        }
    }
    return output; // Return the list of game messages
    }

    private String playTurn(Player currentPlayer, String diceThrow) {
        String result = ""; // Stores the result of the turn
        // Check if the player skipped the turn
        if (diceThrow.equals("0-0")) {
            result = currentPlayer.getPlayersName() + " skipped the turn and " + currentPlayer.getPlayersName() + "’s score is " + currentPlayer.getPlayersScore() + ".";
            return result;
        }
        // Split the dice throw string to get  dice values separately
        String[] dices = diceThrow.split("-");
        int firstDice = Integer.parseInt(dices[0]);
        int secondDice = Integer.parseInt(dices[1]);
        // Build the result message based on the dice values and player actions
        result = currentPlayer.getPlayersName() + " threw " + firstDice + "-" + secondDice;
        if (firstDice == 1 && secondDice == 1) {
            // Player rolled double ones, reset their score and remove them from active players
            currentPlayer.resetScore();
            activePlayers.remove(currentPlayer);
            result += ". Game over " + currentPlayer.getPlayersName() + "!";
        } else if (firstDice == 1 || secondDice == 1) {
            // Player rolled at least one one, keep their current score
            result += " and " + currentPlayer.getPlayersName() + "’s score is " + currentPlayer.getPlayersScore() + ".";
        } else {
            // Player rolled neither one, add the sum of dice to their score
            currentPlayer.addScore(firstDice + secondDice);
            result += " and " + currentPlayer.getPlayersName() + "’s score is " + currentPlayer.getPlayersScore() + ".";
        }
        return result;// Return the result of the turn
    }

}