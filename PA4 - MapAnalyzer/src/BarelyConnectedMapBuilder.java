import java.util.*;

/**
 * Builds a barely connected map.
 */
public class BarelyConnectedMapBuilder {
    private RoadNetwork roadNetwork;

    public BarelyConnectedMapBuilder(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    /**
     * Builds the barely connected map.
     *
     * @return A RoadNetwork representing the barely connected map.
     */
    public RoadNetwork buildBarelyConnectedMap() {
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Road::getDistance)
                .thenComparingInt(Road::getId));
        Set<String> connectedCities = new HashSet<>();
        RoadNetwork barelyConnectedMap = new RoadNetwork();

        String startCity = roadNetwork.getCities().iterator().next().getName();
        connectedCities.add(startCity);
        priorityQueue.addAll(roadNetwork.getCity(startCity).getRoads());

        while (connectedCities.size() < roadNetwork.getCities().size()) {
            Road minRoad = priorityQueue.poll();
            String city1 = minRoad.getCity1();
            String city2 = minRoad.getCity2();
            if (connectedCities.contains(city1) && connectedCities.contains(city2)) continue;

            barelyConnectedMap.addRoad(city1, city2, minRoad.getDistance(), minRoad.getId());
            String newCity = connectedCities.contains(city1) ? city2 : city1;
            connectedCities.add(newCity);
            for (Road road : roadNetwork.getCity(newCity).getRoads()) {
                if (!connectedCities.contains(road.getCity1()) || !connectedCities.contains(road.getCity2())) {
                    priorityQueue.add(road);
                }
            }
        }
        return barelyConnectedMap;
    }
}
