import java.util.*;

/**
 * Represents a network of roads between cities.
 */
public class RoadNetwork {
    private final Map<String, City> cityMap;
    private final List<Road> roadList;
    private String startCity;
    private String endCity;

    public RoadNetwork() {
        cityMap = new HashMap<>();
        roadList = new ArrayList<>();
    }

    /**
     * Adds a road between two cities.
     *
     * @param city1    The first city.
     * @param city2    The second city.
     * @param distance The distance of the road.
     * @param id       The unique ID of the road.
     */
    public void addRoad(String city1, String city2, int distance, int id) {
        cityMap.putIfAbsent(city1, new City(city1));
        cityMap.putIfAbsent(city2, new City(city2));
        Road road = new Road(city1, city2, distance, id);
        cityMap.get(city1).addRoad(road);
        cityMap.get(city2).addRoad(road);
        roadList.add(road);
    }

    /**
     * Gets a city by its name.
     * @param name The name of the city.
     * @return The city object.
     */
    public City getCity(String name) {
        return cityMap.get(name);
    }

    /**
     * Gets all the cities in the network.
     * @return A collection of cities.
     */
    public Collection<City> getCities() {
        return cityMap.values();
    }

    /**
     * Gets all the roads in the network.
     * @return A list of roads.
     */
    public List<Road> getRoads() {
        return roadList;
    }

    /**
     * Sets the starting city.
     * @param startCity The starting city.
     */
    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    /**
     * Sets the ending city.
     * @param endCity The ending city.
     */
    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    /**
     * Gets the starting city.
     * @return The starting city.
     */
    public String getStartCity() {
        return startCity;
    }

    /**
     * Gets the ending city.
     * @return The ending city.
     */
    public String getEndCity() {
        return endCity;
    }
}
