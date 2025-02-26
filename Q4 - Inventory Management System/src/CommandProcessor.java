import java.io.*;
import java.util.List;

/**
 * CommandProcessor class to handle inventory commands.
 */
public class CommandProcessor {
    private Inventory<Item> inventory;

    /**
     * Constructor to initialize the CommandProcessor with an inventory.
     * @param inventory The inventory to be managed.
     */
    public CommandProcessor(Inventory<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Processes a single command and writes the result to the writer.
     * @param line The command line to process.
     * @param writer The writer to write the output.
     * @throws IOException If an I/O error occurs.
     */
    public void processCommand(String line, BufferedWriter writer) throws IOException {
        String[] parts = line.split("\t");
        String command = parts[0];

        switch (command) {
            case "ADD":
                addItem(parts);
                break;
            case "REMOVE":
                removeItem(Integer.parseInt(parts[1]), writer);
                break;
            case "SEARCHBYBARCODE":
                searchByBarcode(Integer.parseInt(parts[1]), writer);
                break;
            case "SEARCHBYNAME":
                searchByName(parts[1], writer);
                break;
            case "DISPLAY":
                displayItems(writer);
                break;
        }
    }

    /**
     * Adds an item to the inventory.
     * @param parts The parts of the command line specifying the item details.
     */
    private void addItem(String[] parts) {
        String itemType = parts[1];
        String itemName = parts[2];
        int itemBarcode = Integer.parseInt(parts[parts.length - 2]);
        double itemPrice = Double.parseDouble(parts[parts.length - 1]);

        Item item;
        switch (itemType) {
            case "Book":
                String author = parts[3];
                item = new Book(itemName, author, itemBarcode, itemPrice);
                break;
            case "Toy":
                String color = parts[3];
                item = new Toy(itemName, color, itemBarcode, itemPrice);
                break;
            case "Stationery":
                String kind = parts[3];
                item = new Stationery(itemName, kind, itemBarcode, itemPrice);
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
        inventory.addItem(item);
    }

    /**
     * Removes an item from the inventory by barcode.
     * @param itemBarcode The barcode of the item to remove.
     * @param writer The writer to write the output.
     * @throws IOException If an I/O error occurs.
     */
    private void removeItem(int itemBarcode, BufferedWriter writer) throws IOException {
        boolean removed = inventory.removeItem(itemBarcode);
        writer.write("REMOVE RESULTS:\n");
        writer.write(removed ? "Item is removed.\n" : "Item is not found.\n");
        writer.write("------------------------------\n");
    }

    /**
     * Searches for an item by barcode and writes the result to the writer.
     * @param itemBarcode The barcode of the item to search for.
     * @param writer The writer to write the output.
     * @throws IOException If an I/O error occurs.
     */
    private void searchByBarcode(int itemBarcode, BufferedWriter writer) throws IOException {
        Item item = inventory.searchByBarcode(itemBarcode);
        writer.write("SEARCH RESULTS:\n");
        if (item != null) {
            writer.write(item.getDescription() + "\n");
        } else {
            writer.write("Item is not found.\n");
        }
        writer.write("------------------------------\n");
    }

    /**
     * Searches for items by name and writes the results to the writer.
     * @param itemName The name of the item(s) to search for.
     * @param writer The writer to write the output.
     * @throws IOException If an I/O error occurs.
     */
    private void searchByName(String itemName, BufferedWriter writer) throws IOException {
        List<Item> items = inventory.searchByName(itemName);
        writer.write("SEARCH RESULTS:\n");
        if (items.isEmpty()) {
            writer.write("Item is not found.\n");
        } else {
            for (Item item : items) {
                writer.write(item.getDescription() + "\n");
            }
        }
        writer.write("------------------------------\n");
    }

    /**
     * Displays all items in the inventory and writes the results to the writer.
     * @param writer The writer to write the output.
     * @throws IOException If an I/O error occurs.
     */
    private void displayItems(BufferedWriter writer) throws IOException {
        writer.write("INVENTORY:\n");

        displayItemsByType(Book.class, writer);
        displayItemsByType(Toy.class, writer);
        displayItemsByType(Stationery.class, writer);

        writer.write("------------------------------\n");
    }

    /**
     * Displays items of a specific type and writes the results to the writer.
     * @param type The class type of the items to display.
     * @param writer The writer to write the output.
     * @param <S> The type parameter extending Item.
     * @throws IOException If an I/O error occurs.
     */
    private <S extends Item> void displayItemsByType(Class<S> type, BufferedWriter writer) throws IOException {
        List<S> items = inventory.getItemsByType(type);
        for (S item : items) {
            writer.write(item.getDescription() + "\n");
        }
    }
}
