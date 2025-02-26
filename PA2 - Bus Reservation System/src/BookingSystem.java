import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BookingSystem {
    /**
     * The main method of the BookingSystem program.
     * Takes two command line arguments: input file path and output file path.
     * Processes the input file, executes commands, and writes output to the output file.
     * Removes empty lines from the output file.
     *
     * @param args The array containing command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: This program works exactly with two command line arguments, the first one is the path to the input file whereas the second one is the path to the output file. Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];
        if (!Files.exists(Paths.get(inputFile))) {
            System.out.printf("ERROR: This program cannot read from the \"%s\", either this program does not have read permission to read that file or file does not exist. Program is going to terminate!%n", inputFile);
            return;
        }
        CommandProcessor processor = new CommandProcessor();
        processor.processFile(inputFile, outputFile);
        removeEmptyLine(outputFile, outputFile);
    }

    /**
     * Removes empty lines from the input file and writes the result to the output file.
     *
     * @param inputFile  The path to the input file.
     * @param outputFile The path to the output file where the result will be written.
     */

    private static void removeEmptyLine(String inputFile, String outputFile) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(inputFile));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                if (!allLines.isEmpty()) {
                    int lastIndex = allLines.size() - 1;
                    for (int i = 0; i < lastIndex; i++) {
                        writer.write(allLines.get(i));
                        writer.newLine();
                    }
                    writer.write(allLines.get(lastIndex));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
