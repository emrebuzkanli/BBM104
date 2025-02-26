import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * The Main class represents the entry point of the program.
 * It loads products and purchases from files, processes purchases using a GymMealMachine, and writes outputs to a file.
 */
public class Main {
    /*
     * The main method of the program.
     * It expects three command line arguments: product file path, purchase file path, and output file path.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        String productFilePath = args[0];
        String purchaseFilePath = args[1];
        String gmmOutputFilePath = args[2];

        // Load products from Product.txt file
        List<Product> products = loadProducts(productFilePath);

        // Load purchases from Purchase.txt file
        List<Purchase> purchases = loadPurchases(purchaseFilePath);

        // Create an instance of GymMealMachine
        GymMealMachine gmm = new GymMealMachine(products);
        writeToOutputFile(gmmOutputFilePath, formatPurchaseMessages(gmm.getErrorMessages()));
        // Write initial slot state to file
        writeToOutputFile(gmmOutputFilePath, gmm.outputHolder());

        // Process purchases
        processPurchases(gmm, purchases);

        // Write final slot state and purchase messages to file
        writeToOutputFile(gmmOutputFilePath, formatPurchaseMessages(gmm.getInfoMessages()));
        writeToOutputFile(gmmOutputFilePath, gmm.outputHolder());
    }

    /*
     * Formats the purchase messages into a single string.
     * @param infoMessages The list of purchase messages.
     * @return The formatted purchase messages as a string.
     */
    private static String formatPurchaseMessages(List<String> infoMessages) {
        StringBuilder formattedMessages = new StringBuilder();
        for (String message : infoMessages) {
            formattedMessages.append(message).append("\n");
        }
        return formattedMessages.toString();
    }

    /*
     * Processes the list of purchases using the given GymMealMachine.
     * @param gmm       The GymMealMachine instance to process purchases.
     * @param purchases The list of purchases to process.
     */
    private static void processPurchases(GymMealMachine gmm, List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            gmm.purchase(purchase.getMoneyList(), purchase.getChoice(), purchase.getValue());
        }
    }

    /*
     * Loads products from the specified file.
     * @param filePath The file path to load products from.
     * @return The list of loaded products.
     */
    private static List<Product> loadProducts(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                String[] nutrientParts = parts[2].split(" ");
                double protein = Double.parseDouble(nutrientParts[0]);
                double carbohydrate = Double.parseDouble(nutrientParts[1]);
                double fat = Double.parseDouble(nutrientParts[2]);

                products.add(new Product(name, price, protein, carbohydrate, fat));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    /*
     * Loads purchases from the specified file.
     * @param filePath The file path to load purchases from.
     * @return The list of loaded purchases.
     */
    private static List<Purchase> loadPurchases(String filePath) {
        List<Purchase> purchases = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String[] moneyParts = parts[1].split(" ");
                List<Double> moneyList = new ArrayList<>();
                for (String money : moneyParts) {
                    moneyList.add(Double.parseDouble(money));
                }
                // Parse the choice directly from the string value
                String choiceStr = parts[2]; // Assuming choice is in the third column
                GymMealMachine.Choice choice = null;
                try {
                    choice = GymMealMachine.Choice.valueOf(choiceStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid choice: " + choiceStr);
                }
                if (choice != null) {
                    double value = Double.parseDouble(parts[3]); // Assuming value is in the fourth column
                    purchases.add(new Purchase(moneyList, choice, value));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading purchases: " + e.getMessage());
        }
        return purchases;
    }

    /*
     * Writes content to the specified file.
     * @param filePath The file path to write the content to.
     * @param content  The content to write to the file.
     */
    private static void writeToOutputFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}
