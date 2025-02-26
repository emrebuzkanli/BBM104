import java.io.*;
/**
 * This class represents a Backus-Naur Form (BNF) parser and generator.
 * It reads a structured BNF grammar from an input file, stores it, and
 * then recursively generates all possible strings that can be derived
 * from the grammar.
 */
public class BNF {

    /**
     * The main method of the program.
     * It reads the input file name and output file name from the command line arguments,
     * processes the BNF grammar, generates strings, and writes the result to the output file.
     * @param args Command line arguments: input file name and output file name.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("this project needs 2 argument to start");
            System.exit(1);
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

        try {
            BNFProcessor processor = new BNFProcessor();
            processor.grammarReader(inputFileName);
            String generatedString = processor.stringGenerator('S'); // Start symbol

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
            writer.write(generatedString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}