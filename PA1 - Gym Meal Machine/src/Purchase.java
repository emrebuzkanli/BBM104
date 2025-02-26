import java.util.List;

/**
 * Represents a purchase made in the gym meal machine.
 */
public class Purchase {
    private final List<Double> moneyList;
    private final GymMealMachine.Choice choice;
    private final double value;

    /**
     * Constructs a Purchase object with the specified parameters.
     * @param moneyList The list of money used for the purchase.
     * @param choice The choice made for the purchase.
     * @param value The value associated with the choice.
     */
    public Purchase(List<Double> moneyList, GymMealMachine.Choice choice, double value) {
        this.moneyList = moneyList;
        this.choice = choice;
        this.value = value;
    }

    /**
     * Gets the list of money used for the purchase.
     * @return The list of money.
     */
    public List<Double> getMoneyList() {
        return moneyList;
    }

    /**
     * Gets the choice made for the purchase.
     * @return The choice made for the purchase.
     */
    public GymMealMachine.Choice getChoice() {
        return choice;
    }

    /**
     * Gets the value associated with the choice.
     * @return The value associated with the choice.
     */
    public double getValue() {
        return value;
    }
}
