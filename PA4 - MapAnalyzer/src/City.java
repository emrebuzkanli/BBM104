import java.util.*;

/**
 * Represents a city in the road network.
 */
public class City {
    private final String name;
    private final List<Road> roads;

    public City(String name) {
        this.name = name;
        this.roads = new ArrayList<>();
    }

    /**
     * Adds a road to the city.
     *
     * @param road The road to be added.
     */
    public void addRoad(Road road) {
        roads.add(road);
        roads.sort(Comparator.comparingInt(Road::getDistance).thenComparingInt(Road::getId));
    }

    /**
     * Gets the name of the city.
     *
     * @return The name of the city.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of roads connected to the city.
     *
     * @return A list of roads.
     */
    public List<Road> getRoads() {
        return roads;
    }
}
