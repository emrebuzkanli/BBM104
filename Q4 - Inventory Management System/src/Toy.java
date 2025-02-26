/**
 * Class representing a toy item in the inventory.
 */
public class Toy extends Item {
    private String color;

    /**
     * Constructor for a Toy.
     * @param name The name of the toy.
     * @param color The color of the toy.
     * @param barcode The barcode of the toy.
     * @param price The price of the toy.
     */
    public Toy(String name, String color, int barcode, double price) {
        super(name, barcode, price);
        this.color = color;
    }

    @Override
    public String getDescription() {
        return "Color of the " + name + " is " + color + ". Its barcode is " + barcode + " and its price is " + price;
    }
}
