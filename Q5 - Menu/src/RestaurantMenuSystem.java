import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The RestaurantMenuSystem class manages the menu and table orders for a restaurant.
 * It provides functionality to load menu items and orders from files,
 * display the menu, and summarize orders for each table.
 */
public class RestaurantMenuSystem {
    private List<MenuItem> menuItems;
    private List<Table> tables;

    /**
     * Constructor for the RestaurantMenuSystem class.
     * Initializes the menu items and tables lists.
     */
    public RestaurantMenuSystem() {
        this.menuItems = new ArrayList<>();
        this.tables = new ArrayList<>();
    }

    /**
     * Loads menu items from a file.
     * Each line in the file must follow the format:
     * <type>,<code>,<name>,<price>,<calorie>
     * @param filename The path to the menu file.
     * @throws IOException If there is an error reading the file.
     */
    public void loadMenu(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String type = parts[0].trim();
            String code = parts[1].trim();
            String name = parts[2].trim();
            double price = Double.parseDouble(parts[3].trim());
            double calorie = Double.parseDouble(parts[4].trim());

            switch (type) {
                case "Soup":
                    menuItems.add(new Soup(code, name, price, calorie));
                    break;
                case "Salad":
                    menuItems.add(new Salad(code, name, price, calorie));
                    break;
                case "MainCourse":
                    menuItems.add(new MainCourse(code, name, price, calorie));
                    break;
                case "Dessert":
                case "Desert": // Handle both spellings
                    menuItems.add(new Dessert(code, name, price, calorie));
                    break;
                case "Beverage":
                    menuItems.add(new Beverage(code, name, price, calorie));
                    break;
                default:
                    System.out.println("Unknown menu type: " + type);
            }
        }
        reader.close();
    }

    /**
     * Loads table orders from a file.
     * Each line in the file must follow the format:
     * <tableName> <code_1>,<code_2>,...,<code_n>
     *
     * @param filename The path to the orders file.
     * @throws IOException If there is an error reading the file.
     */
    public void loadOrders(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {

            String[] parts = line.split("\\s+");
            StringBuilder tableNameBuilder = new StringBuilder();
            for (int i = 0; i < parts.length - 1; i++) {
                if (i > 0) {
                    tableNameBuilder.append(" ");
                }
                tableNameBuilder.append(parts[i]);
            }
            String tableName = tableNameBuilder.toString().trim();


            int orderCodesIndex = parts.length - 1;
            String[] orderCodes = parts[orderCodesIndex].split(",");

            Table table = new Table(tableName);
            for (int i = 0; i < orderCodes.length; i++) {
                String code = orderCodes[i].trim();
                MenuItem item = findMenuItemByCode(code);
                if (item != null) {
                    table.addOrder(item);
                }
            }

            tables.add(table);
        }
        reader.close();
    }

    /**
     * Displays all menu items.
     * Each menu item is displayed using its `displayDetails` method.
     */
    public void displayMenu() {
        System.out.println("Menu Items:");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            item.displayDetails();
        }
    }

    /**
     * Displays summaries for all tables.
     * Each table's summary includes its orders and calculated totals.
     */
    public void displayTableSummaries() {
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            table.displayTableSummary();
        }
    }

    /**
     * Finds a menu item by its code.
     *
     * @param code The unique code of the menu item.
     * @return The MenuItem object with the matching code, or null if not found.
     */
    private MenuItem findMenuItemByCode(String code) {
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
