import java.util.ArrayList;
import java.util.List;

/**
 * Generic class representing an inventory that can hold items of any type extending Item.
 * @param <T> The type of items this inventory holds, which must extend Item.
 */
public class Inventory<T extends Item> {
    private List<T> items;

    /**
     * Constructor to initialize an empty inventory.
     */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     * @param item The item to add.
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * Removes an item from the inventory by barcode.
     * @param barcode The barcode of the item to remove.
     * @return True if the item was removed, false if not found.
     */
    public boolean removeItem(int barcode) {
        return items.removeIf(item -> item.getBarcode() == barcode);
    }

    /**
     * Searches for an item by barcode.
     * @param barcode The barcode of the item to search for.
     * @return The item if found, null otherwise.
     */
    public T searchByBarcode(int barcode) {
        for (T item : items) {
            if (item.getBarcode() == barcode) {
                return item;
            }
        }
        return null;
    }

    /**
     * Searches for items by name.
     * @param name The name of the item(s) to search for.
     * @return A list of items with the specified name.
     */
    public List<T> searchByName(String name) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Gets items of a specific type.
     * @param type The class type of the items to get.
     * @param <S> The type parameter extending T.
     * @return A list of items of the specified type.
     */
    public <S extends T> List<S> getItemsByType(Class<S> type) {
        List<S> result = new ArrayList<>();
        for (T item : items) {
            if (type.isInstance(item)) {
                result.add(type.cast(item));
            }
        }
        return result;
    }
}
