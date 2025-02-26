/**
 * Represents a product in the gym meal machine.
 */
public class Product {
    private final String name;
    private final double price;
    private final double protein;
    private final double carbohydrate;
    private final double fat;
    private final long calorie;

    /**
     * Constructs a Product object with the specified attributes.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param protein The protein content of the product.
     * @param carbohydrate The carbohydrate content of the product.
     * @param fat The fat content of the product.
     */
    public Product(String name, double price, double protein, double carbohydrate, double fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.calorie = calculateCalorie(); // Round calorie here
    }

    /**
     * Calculates the calorie content of the product.
     * @return The rounded calorie value.
     */
    private long calculateCalorie() {
        double rawCalorie = 4 * protein + 4 * carbohydrate + 9 * fat;
        return Math.round(rawCalorie); // Round the calorie value to the nearest whole number
    }

    // Getters

    /**
     * Gets the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the product.
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the protein content of the product.
     * @return The protein content of the product.
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Gets the carbohydrate content of the product.
     * @return The carbohydrate content of the product.
     */
    public double getCarbohydrate() {
        return carbohydrate;
    }

    /**
     * Gets the fat content of the product.
     * @return The fat content of the product.
     */
    public double getFat() {
        return fat;
    }

    /**
     * Gets the calorie content of the product.
     * @return The calorie content of the product.
     */
    public long getCalorie() {
        return calorie;
    }
}
