/**
 * Represents a road between two cities.
 */
public class Road {
    private final String city1;
    private final String city2;
    private final int distance;
    private final int id;

    public Road(String city1, String city2, int distance, int id) {
        this.city1 = city1;
        this.city2 = city2;
        this.distance = distance;
        this.id = id;
    }

    /**
     * Gets the first city of the road.
     *
     * @return The first city.
     */
    public String getCity1() {
        return city1;
    }

    /**
     * Gets the second city of the road.
     *
     * @return The second city.
     */
    public String getCity2() {
        return city2;
    }

    /**
     * Gets the distance of the road.
     *
     * @return The distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Gets the unique ID of the road.
     *
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return city1 + "\t" + city2 + "\t" + distance + "\t" + id;
    }
}
