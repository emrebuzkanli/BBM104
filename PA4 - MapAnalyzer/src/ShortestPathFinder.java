import java.util.*;

/**
 * Finds the shortest path between cities in the road network.
 */
public class ShortestPathFinder {
    private final RoadNetwork roadNetwork;

    public ShortestPathFinder(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    /**
     * Finds the fastest route between the start city and the end city.
     *
     * @param startCity The starting city.
     * @param endCity   The ending city.
     * @return A RoadNetwork representing the fastest route.
     */
    public RoadNetwork findFastestRoute(String startCity, String endCity) {
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(Road::getDistance)
                        .thenComparingInt(Road::getId)
        );
        Map<String, Integer> distances = new HashMap<>();
        Map<String, List<Road>> previousRoads = new HashMap<>();
        Set<String> visitedCities = new HashSet<>();

        for (String city : roadNetwork.getCities().stream().map(City::getName).toArray(String[]::new)) {
            distances.put(city, Integer.MAX_VALUE);
            previousRoads.put(city, new ArrayList<>());
        }
        distances.put(startCity, 0);

        priorityQueue.add(new Road(startCity, startCity, 0, -1)); // Dummy road to start with

        while (!priorityQueue.isEmpty()) {
            Road currentRoad = priorityQueue.poll();
            String currentCity = currentRoad.getCity2();
            if (!visitedCities.add(currentCity)) continue;

            City city = roadNetwork.getCity(currentCity);
            for (Road road : city.getRoads()) {
                String neighborCity = road.getCity1().equals(currentCity) ? road.getCity2() : road.getCity1();
                if (!visitedCities.contains(neighborCity)) {
                    int newDistance = distances.get(currentCity) + road.getDistance();

                    if (newDistance < distances.get(neighborCity)) {
                        distances.put(neighborCity, newDistance);
                        List<Road> newPath = new ArrayList<>(previousRoads.get(currentCity));
                        newPath.add(road);
                        previousRoads.put(neighborCity, newPath);
                        priorityQueue.add(new Road(currentCity, neighborCity, newDistance, road.getId()));
                    }
                }
            }
        }

        RoadNetwork fastestRouteNetwork = new RoadNetwork();
        List<Road> path = previousRoads.get(endCity);
        for (Road road : path) {
            fastestRouteNetwork.addRoad(road.getCity1(), road.getCity2(), road.getDistance(), road.getId());
        }
        return fastestRouteNetwork;
    }
}
