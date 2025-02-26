import java.util.*;

/**
 * The base class for  menu items.
 * Contains  properties such as type, code, name, price, and calorie count.
 */
public abstract class MenuItem {
    private String type;
    private String code;
    private String name;
    private double price;
    private double calorie;

    /**
     * Constructor for MenuItem.
     *
     * @param type    The type of the menu item .
     * @param code    The unique code for the menu item.
     * @param name    The name of the menu item.
     * @param price   The price of the menu item.
     * @param calorie The calorie count of the menu item.
     */
    public MenuItem(String type, String code, String name, double price, double calorie) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
    }

    /**
     * @return The type of the menu item.
     */
    public String getType() {
        return type;
    }

    /**
     * @return The unique code of the menu item.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return The name of the menu item.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The price of the menu item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return The calorie count of the menu item.
     */
    public double getCalorie() {
        return calorie;
    }

    /**
     * Abstract method for displaying the details of the menu item.
     * Subclasses must provide their implementation.
     */
    public abstract void displayDetails();
}

// Soup class
/**
 * Represents a Soup menu item.
 * Extends the MenuItem base class and provides a concrete implementation of `displayDetails`.
 */
class Soup extends MenuItem {
    public Soup(String code, String name, double price, double calorie) {
        super("Soup", code, name, price, calorie);
    }

    @Override
    public void displayDetails() {
        System.out.printf("%s: %s Price: %.2f$ Calorie: %.1f cal\n", getType(), getName(), getPrice(), getCalorie());
    }
}

// Salad class
/**
 * Represents a Salad menu item.
 * Extends the MenuItem base class and provides a concrete implementation of `displayDetails`.
 */
class Salad extends MenuItem {
    public Salad(String code, String name, double price, double calorie) {
        super("Salad", code, name, price, calorie);
    }

    @Override
    public void displayDetails() {
        System.out.printf("%s: %s Price: %.2f$ Calorie: %.1f cal\n", getType(), getName(), getPrice(), getCalorie());
    }
}

// MainCourse class
/**
 * Represents a Main Course menu item.
 * Extends the MenuItem base class and provides a concrete implementation of `displayDetails`.
 */
class MainCourse extends MenuItem {
    public MainCourse(String code, String name, double price, double calorie) {
        super("MainCourse", code, name, price, calorie);
    }

    @Override
    public void displayDetails() {
        System.out.printf("%s: %s Price: %.2f$ Calorie: %.1f cal\n", getType(), getName(), getPrice(), getCalorie());
    }
}

// Dessert class
/**
 * Represents a Dessert menu item.
 * Extends the MenuItem base class and provides a concrete implementation of `displayDetails`.
 */
class Dessert extends MenuItem {
    public Dessert(String code, String name, double price, double calorie) {
        super("Dessert", code, name, price, calorie);
    }

    @Override
    public void displayDetails() {
        System.out.printf("%s: %s Price: %.2f$ Calorie: %.1f cal\n", getType(), getName(), getPrice(), getCalorie());
    }
}

// Beverage class
/**
 * Represents a Beverage menu item.
 * Extends the MenuItem base class and provides a concrete implementation of `displayDetails`.
 */
class Beverage extends MenuItem {
    public Beverage(String code, String name, double price, double calorie) {
        super("Beverage", code, name, price, calorie);
    }

    @Override
    public void displayDetails() {
        System.out.printf("%s: %s Price: %.2f$ Calorie: %.1f cal\n", getType(), getName(), getPrice(), getCalorie());
    }
}
