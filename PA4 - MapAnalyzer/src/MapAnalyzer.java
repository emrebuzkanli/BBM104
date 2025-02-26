import java.util.Locale;

/**
 * The main class that runs the map analysis.
 */
public class MapAnalyzer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        if (args.length != 2) {
            System.out.println("Usage: java MapAnalyzer <input file> <output file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        FileHandler fileHandler = new FileHandler();
        RoadNetwork roadNetwork = fileHandler.readInput(inputFile);

        // Find the fastest route
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(roadNetwork);
        RoadNetwork fastestRouteNetwork = shortestPathFinder.findFastestRoute(roadNetwork.getStartCity(), roadNetwork.getEndCity());

        // Find the barely connected map
        BarelyConnectedMapBuilder barelyConnectedMapBuilder = new BarelyConnectedMapBuilder(roadNetwork);
        RoadNetwork barelyConnectedMap = barelyConnectedMapBuilder.buildBarelyConnectedMap();

        // Write the output to the file
        fileHandler.writeOutput(outputFile, roadNetwork, fastestRouteNetwork, barelyConnectedMap);
    }
}
