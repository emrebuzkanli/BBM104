import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table in the restaurant.
 * Each table has a name and a list of orders (menu items).
 * Provides methods to add orders, calculate totals, and display a summary.
 */
public class Table {
    private final String name;
    private final List<MenuItem> orders;

    /**
     * Constructor for the Table class.
     * @param name The name of the table.
     */
    public Table(String name) {
        this.name = name;
        this.orders = new ArrayList<>();
    }

    /**
     * Gets the name of the table.
     * @return The name of the table.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a menu item to the table's list of orders.
     * @param item The MenuItem to add.
     */
    public void addOrder(MenuItem item) {
        orders.add(item);
    }

    /**
     * Calculates the total price of all orders at the table.
     * @return The total price of all orders.
     */
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < orders.size(); i++) {
            MenuItem item = orders.get(i);
            totalPrice = totalPrice + item.getPrice();
        }
        return totalPrice;
    }

    /**
     * Calculates the total calorie count of all orders at the table.
     * @return The total calorie count of all orders.
     */
    public double calculateTotalCalories() {
        double totalCalories = 0.0;
        for (int i = 0; i < orders.size(); i++) {
            MenuItem item = orders.get(i);
            totalCalories = totalCalories + item.getCalorie();
        }
        return totalCalories;
    }

    /**
     * Displays a summary of the table, including all orders,
     * total price, and total calorie count.
     */
    public void displayTableSummary() {
        System.out.println("==============================");
        System.out.println("Table Name: " + getName());
        System.out.println("------------------------------");
        System.out.println("Orders:");
        for (int i = 0; i < orders.size(); i++) {
            MenuItem item = orders.get(i);
            item.displayDetails();
        }
        System.out.println("------------------------------");
        System.out.printf("Total Price: %.2f$\n", calculateTotalPrice());
        System.out.printf("Total Calories: %.1f cal\n", calculateTotalCalories());
        System.out.println("==============================");
    }
}
