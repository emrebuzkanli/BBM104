/**
 * Represents a slot in the gym meal machine.
 */
public class Slot {
    private Product product;
    private int quantity;

    /**
     * Constructs a Slot object with no product initially.
     */
    public Slot() {
        this.product = null;
        this.quantity = 0;
    }

    /**
     * Gets the product stored in the slot.
     * @return The product stored in the slot.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product in the slot and initializes the quantity to 1.
     * @param product The product to be stored in the slot.
     */
    public void setProduct(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    /**
     * Gets the quantity of the product stored in the slot.
     * @return The quantity of the product stored in the slot.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Changes the number of products in the slot by the specified amount.
     * @param change The amount by which to change the number of products.
     */
    public void changeNumberOfProducts(int change) {
        this.quantity += change;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

    /**
     * Checks if the slot is empty.
     * @return true if the slot is empty, false otherwise.
     */
    public boolean isEmpty() {
        return product == null;
    }
}
