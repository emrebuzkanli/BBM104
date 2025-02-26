import java.io.*;
import java.util.*;

/**
 * Handles file input and output for the road network.
 */
public class FileHandler {

    /**
     * Reads the input file and creates a RoadNetwork.
     *
     * @param inputFile The input file path.
     * @return A RoadNetwork object.
     */
    public RoadNetwork readInput(String inputFile) {
        RoadNetwork roadNetwork = new RoadNetwork();
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            String startCity = scanner.next();
            String endCity = scanner.next();
            roadNetwork.setStartCity(startCity);
            roadNetwork.setEndCity(endCity);

            while (scanner.hasNext()) {
                String city1 = scanner.next();
                String city2 = scanner.next();
                int distance = scanner.nextInt();
                int id = scanner.nextInt();
                roadNetwork.addRoad(city1, city2, distance, id);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return roadNetwork;
    }

    /**
     * Writes the output to the file.
     *
     * @param outputFile             The output file path.
     * @param originalNetwork        The original RoadNetwork.
     * @param fastestRouteNetwork    The fastest route RoadNetwork.
     * @param barelyConnectedMap     The barely connected RoadNetwork.
     */
    public void writeOutput(String outputFile, RoadNetwork originalNetwork, RoadNetwork fastestRouteNetwork, RoadNetwork barelyConnectedMap) {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.println("Fastest Route from " + originalNetwork.getStartCity() + " to " + originalNetwork.getEndCity() +
                    " (" + fastestRouteNetwork.getRoads().stream().mapToInt(Road::getDistance).sum() + " KM):");
            fastestRouteNetwork.getRoads().forEach(writer::println);

            writer.println("Roads of Barely Connected Map is:");
            barelyConnectedMap.getRoads().stream().sorted(Comparator.comparingInt(Road::getDistance)
                    .thenComparingInt(Road::getId)).forEach(writer::println);

            RoadNetwork mstFastestRouteNetwork = new ShortestPathFinder(barelyConnectedMap).findFastestRoute(originalNetwork.getStartCity(), originalNetwork.getEndCity());
            writer.println("Fastest Route from " + originalNetwork.getStartCity() + " to " + originalNetwork.getEndCity() +
                    " on Barely Connected Map (" + mstFastestRouteNetwork.getRoads().stream().mapToInt(Road::getDistance).sum() + " KM):");
            mstFastestRouteNetwork.getRoads().forEach(writer::println);

            writer.println("Analysis:");
            int originalTotalDistance = originalNetwork.getRoads().stream().mapToInt(Road::getDistance).sum();
            int barelyConnectedTotalDistance = barelyConnectedMap.getRoads().stream().mapToInt(Road::getDistance).sum();

            double materialUsageRatio = (double) barelyConnectedTotalDistance / originalTotalDistance;
            double fastestRouteRatio = (double) mstFastestRouteNetwork.getRoads().stream().mapToInt(Road::getDistance).sum() / fastestRouteNetwork.getRoads().stream().mapToInt(Road::getDistance).sum();

            writer.printf("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n", materialUsageRatio);
            writer.printf("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", fastestRouteRatio);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
