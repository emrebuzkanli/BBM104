import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class represents a processor for parsing BNF grammar and generating strings.
 */
class BNFProcessor {
    // Map to store BNF grammar
    private Map<Character, List<String>> grammar = new HashMap<>();

    /**
     * Reads the structured BNF grammar from the input file and stores it.
     * @param inputFileName The name of the input file containing the BNF grammar.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void grammarReader(String inputFileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("->");
            char nonTerminal = parts[0].charAt(0);
            String[] productions = parts[1].split("\\|");
            grammar.put(nonTerminal, Arrays.asList(productions));
        }
        reader.close();
    }

    /**
     * Recursively generates strings from the BNF grammar starting from the specified symbol.
     * @param symbol The non-terminal symbol to start the generation process.
     * @return The string generated from the BNF grammar.
     */
    public String stringGenerator(char symbol) {
        StringBuilder result = new StringBuilder();
        if (!grammar.containsKey(symbol)) {
            result.append(symbol); // Terminal symbol
        } else {
            result.append("(");
            boolean first = true;
            for (String production : grammar.get(symbol)) {
                if (!first) {
                    result.append("|");
                }
                first = false;
                for (char c : production.toCharArray()) {
                    result.append(stringGenerator(c));
                }
            }
            result.append(")");
        }
        return result.toString();
    }
}