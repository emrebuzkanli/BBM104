import java.io.*;

/**
 * Main class to execute inventory operations from commands.txt and output results to output.txt.
 */
public class Main {
    private static Inventory<Item> inventory = new Inventory<>();

    /**
     * Main method to run the inventory management system.
     * @param args Command-line arguments, expects input and output file names.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main commands.txt output.txt");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        CommandProcessor processor = new CommandProcessor(inventory);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                processor.processCommand(line, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
