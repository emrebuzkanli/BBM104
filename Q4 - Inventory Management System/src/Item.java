/**
 * Abstract class representing a generic item in the inventory.
 */
public abstract class Item {
    protected String name;
    protected int barcode;
    protected double price;

    /**
     * Constructor for an Item.
     * @param name The name of the item.
     * @param barcode The barcode of the item.
     * @param price The price of the item.
     */
    public Item(String name, int barcode, double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    /**
     * Gets the barcode of the item.
     * @return The barcode.
     */
    public int getBarcode() {
        return barcode;
    }

    /**
     * Gets the name of the item.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the item.
     * @return The description.
     */
    public abstract String getDescription();
}
