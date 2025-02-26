/**
 * Class representing a book item in the inventory.
 */
public class Book extends Item {
    private String author;

    /**
     * Constructor for a Book.
     * @param name The name of the book.
     * @param author The author of the book.
     * @param barcode The barcode of the book.
     * @param price The price of the book.
     */
    public Book(String name, String author, int barcode, double price) {
        super(name, barcode, price);
        this.author = author;
    }

    @Override
    public String getDescription() {
        return "Author of the " + name + " is " + author + ". Its barcode is " + barcode + " and its price is " + price;
    }
}
