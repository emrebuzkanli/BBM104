import java.io.IOException;

/**
 * The Main class serves as the entry point for the restaurant menu system.
 * It loads menu and order data from files and displays the menu and table summaries.
 */
public class Main {

    /**
     * The main method of the application.
     *
     * @param args Command-line arguments. Needs two arguments:
     *             the path to the menu file and the path to the orders file.
     * @throws IOException If there is an error reading the input files.
     */
    public static void main(String[] args) throws IOException {
        String menuFile = args[0]; // Path to the menu file
        String ordersFile = args[1]; // Path to the orders file

        RestaurantMenuSystem orderSystem = new RestaurantMenuSystem(); // Initializes the system
        orderSystem.loadMenu(menuFile); // Loads the menu data
        orderSystem.loadOrders(ordersFile); // Loads the order data
        orderSystem.displayMenu(); // Displays all menu items
        orderSystem.displayTableSummaries(); // Displays summaries for all tables
    }
}
