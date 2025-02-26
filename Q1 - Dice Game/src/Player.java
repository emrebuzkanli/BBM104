/*
 * Represents Player class for the DiceGame
 */
public class Player {
    private final String playersName; // The name of the player
    private int playersScore; // The score of the player
    /*
     * Constructs a new player with the given name and initializes the score to 0.
     */
    public Player(String name) {
        this.playersName = name;
        this.playersScore = 0;
    }
    /*
     * Gets the name of the player.
     * Return the name of the player.
     */
    public String getPlayersName() {
        return playersName;
    }
    /*
     * Gets the score of the player.
     * Return the score of the player.
     */
    public int getPlayersScore() {
        return playersScore;
    }
    /*
     * Adds the specified score points to the player's score.
     */
    public void addScore(int scorePoint) {
        playersScore += scorePoint;
    }
    /*
     * Resets the player's score to 0.
     */
    public void resetScore() {
        playersScore = 0;
    }
}