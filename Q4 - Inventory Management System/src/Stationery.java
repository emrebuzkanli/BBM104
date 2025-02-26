/**
 * Class representing a stationery item in the inventory.
 */
public class Stationery extends Item {
    private String kind;

    /**
     * Constructor for a Stationery item.
     * @param name The name of the stationery item.
     * @param kind The kind of the stationery item.
     * @param barcode The barcode of the stationery item.
     * @param price The price of the stationery item.
     */
    public Stationery(String name, String kind, int barcode, double price) {
        super(name, barcode, price);
        this.kind = kind;
    }

    @Override
    public String getDescription() {
        return "Kind of the " + name + " is " + kind + ". Its barcode is " + barcode + " and its price is " + price;
    }
}
