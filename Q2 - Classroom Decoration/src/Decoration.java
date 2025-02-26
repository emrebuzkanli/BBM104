/**
 *  This interface provides two main actions: describing the decoration and calculating its cost.
 */
interface Decoration {
    /**
     * Describes what is the decoration and how much area it covers.
     * @param area The size of the area that the decoration covers.
     * @return A text description of the decoration.
     */
    String getDescription(double area);

    /**
     * Figures out how much the decoration will cost for a certain area.
     * @param area The size of the area to be decorated.
     * @return The total cost of the decoration.
     */
    double calculateCost(double area);
}
/**
 * Represents paint used to decorate the walls of a classroom. It tells you how much paint you need and how much it will cost.
 */
class Paint implements Decoration {
    private final double costPerSquareMeter; // The cost of the paint per square meter.

    /**
     * Sets up the paint with its cost.
     * @param costPerSquareMeter How much the paint costs for every square meter.
     */
    public Paint(double costPerSquareMeter) {
        this.costPerSquareMeter = costPerSquareMeter;
    }

    @Override
    public String getDescription(double area) {
        return String.format("%.0fm2 of Paint", Math.ceil(area)); // Shows how much area the paint covers.
    }

    @Override
    public double calculateCost(double area) {
        return Math.ceil(area * costPerSquareMeter); // Calculates the total cost based on the area.
    }
}
/**
 * Represents tiles . It helps figure out how many tiles you need and the total cost for covering a  area.
 */
class Tile implements Decoration {
    private final double costPerTile; // Cost of one tile.
    private final double areaPerTile; // Area covered by one tile.

    /**
     * Sets up tiles with their cost and the area they cover.
     * @param costPerTile Cost of each tile.
     * @param areaPerTile Area covered by one tile.
     */
    public Tile(double costPerTile, double areaPerTile) {
        this.costPerTile = costPerTile;
        this.areaPerTile = areaPerTile;
    }

    @Override
    public String getDescription(double area) {
        int numberOfTiles = (int) Math.ceil(area / areaPerTile); // Calculates how many tiles are needed.
        return String.format("%d Tiles", numberOfTiles); // Describes the number of tiles needed.
    }

    @Override
    public double calculateCost(double area) {
        return Math.ceil(area / areaPerTile) * costPerTile; // Calculates the total cost for the tiles.
    }
}
/**
 * Represents wallpaper to decorate walls. This class tells you how much wallpaper you'll need and how much it will cost.
 */
class Wallpaper implements Decoration {
    private final double costPerSquareMeter; // Cost of the wallpaper per square meter.

    /**
     * Sets up the wallpaper with its cost per square meter.
     * @param costPerSquareMeter Cost per square meter of the wallpaper.
     */
    public Wallpaper(double costPerSquareMeter) {
        this.costPerSquareMeter = costPerSquareMeter;
    }

    @Override
    public String getDescription(double area) {
        return String.format("%.0fm2 of Wallpaper", Math.ceil(area)); // Shows the area the wallpaper covers.
    }

    @Override
    public double calculateCost(double area) {
        return Math.ceil(area * costPerSquareMeter); // Calculates the total cost based on the area.
    }
}
