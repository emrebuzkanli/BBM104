/**
 * Represents a classroom that can be decorated.
 * This is an abstract class.
 * Specific types of classrooms will extend this class to provide more details.
 */
abstract class Classroom {
    protected String name;
    protected double height;
    protected StringBuilder decorationDetails = new StringBuilder();
    protected double decorationCost = 0.0;
    /**
     * Creates a Classroom with a name and height.
     * @param name The classroom's name.
     * @param height The height of the classroom's walls.
     */
    public Classroom(String name, double height) {
        this.name = name;
        this.height = height;
    }
    /**
     * Calculates the total area of the classroom walls.
     * @return The total wall area in square meters.
     */
    public abstract double calculateWallArea();
    /**
     * Calculates the total area of the classroom floor.
     * @return The total floor area in square meters.
     */
    public abstract double calculateFloorArea();
    /**
     * Applies decorations to the walls and floor of the classroom, calculating the total cost.
     * @param wallDecoration The decoration for the walls.
     * @param floorDecoration The decoration for the floor.
     */
    public void decorate(Decoration wallDecoration, Decoration floorDecoration) {
        double wallArea = calculateWallArea();
        double floorArea = calculateFloorArea();

        String wallDescription = wallDecoration.getDescription(wallArea);
        double wallCost = wallDecoration.calculateCost(wallArea);

        String floorDescription = floorDecoration.getDescription(floorArea);
        double floorCost = floorDecoration.calculateCost(floorArea);

        decorationDetails.setLength(0); // Resets any previous decoration details.
        decorationDetails.append(String.format("Classroom %s used %s for walls and used %s for flooring, ", name, wallDescription, floorDescription));

        decorationCost = wallCost + floorCost;
    }
    /**
     * Gets the detailed description of the decorations applied to the classroom.
     * @return A description of the decorations and their arrangement.
     */
    public String getDecorationDetails() {
        return decorationDetails.append(String.format("these costed %.0fTL.", decorationCost)).toString();
    }
    /**
     * Gets the total cost of the decorations applied to the classroom.
     * @return The total decoration cost.
     */
    public double getDecorationCost() {
        return decorationCost;
    }
}
/**
 * Represents a classroom with a circular shape. This class calculates the area of the walls and floor differently because of its shape.
 */
class CircularClassroom extends Classroom {
    private final double radius;
    /**
     * Constructs a new circular classroom with specified dimensions.
     * @param name The name of the classroom.
     * @param height The height of the classroom walls.
     * @param diameter The total diameter of the classroom, used to calculate the radius.
     */
    public CircularClassroom(String name, double height, double diameter) {
        super(name, height);
        this.radius = diameter / 2;
    }

    @Override
    public double calculateWallArea() {
        // Calculates the wall area using the formula for the circumference of a circle times wall height.
        return 2 * Math.PI * radius * height;
    }

    @Override
    public double calculateFloorArea() {
        // Calculates the floor area using the formula for the area of a circle.
        return Math.PI * radius * radius;
    }
}
/**
 * Represents a classroom with a rectangular shape. It calculates the area of the walls and the floor based on its length and width.
 */
class RectangularClassroom extends Classroom {
    private final double width;
    private final double length;
    /**
     * Constructs a new rectangular classroom with specified dimensions.
     * @param name The name of the classroom.
     * @param height The height of the classroom walls.
     * @param width The width of the classroom.
     * @param length The length of the classroom.
     */
    public RectangularClassroom(String name, double height, double width, double length) {
        super(name, height);
        this.width = width;
        this.length = length;
    }

    @Override
    public double calculateWallArea() {
        // Calculates the wall area using the perimeter of the rectangle times wall height.
        return 2 * height * (width + length);

    }

    @Override
    public double calculateFloorArea() {
        // Calculates the floor area using the formula for the area of a rectangle.
        return width * length;
    }
}