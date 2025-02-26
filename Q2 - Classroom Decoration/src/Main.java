import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * The Main class  reading classroom and decoration information from files,applies the decorations to the classrooms, and writes the results to an output file.
 * It uses the ClassroomBuilder and DecorationBuilder to create instances based on file input.
 */
public class Main {
    private static final Map<String, Classroom> classrooms = new LinkedHashMap<>();
    private static final Map<String, Decoration> decorations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        // Read classroom and decoration specifications from files.
        String itemsFilePath = args[0];
        String decorationFilePath = args[1];
        // Output the detailed decoration results to the specified file.
        String outputFilePath = args[2];

        readItems(itemsFilePath);
        decorateClassrooms(decorationFilePath);
        writeOutput(outputFilePath);
    }
    /**
     * Reads classroom specifications from the provided file path, constructing and storing
     * Classroom objects in the 'classrooms' map for later decoration.
     * @param itemsFilePath The file path containing classroom specifications.
     */
    private static void readItems(String itemsFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if ("CLASSROOM".equals(parts[0])) {
                    ClassroomBuilder builder = new ClassroomBuilder()
                            .setName(parts[1])
                            .setRoomHeight(Double.parseDouble(parts[5]));

                    if ("Circle".equals(parts[2])) {
                        builder.setRoomDiameter(Double.parseDouble(parts[3]));
                    } else {
                        builder.setRoomWidth(Double.parseDouble(parts[3]))
                                .setRoomLength(Double.parseDouble(parts[4]));
                    }
                    classrooms.put(parts[1], builder.build());
                } else if ("DECORATION".equals(parts[0])) {
                    Decoration decoration = new DecorationBuilder()
                            .setType(parts[2])
                            .setPrice(Double.parseDouble(parts[3]))
                            .setAreaPerTile(parts.length > 4 ? Double.parseDouble(parts[4]) : 0)
                            .build();
                    decorations.put(parts[1], decoration);
                }
            }
        }
    }
    /**
     * Applies decorations to classrooms based on specifications read from the provided file .
     * Decorations are matched to classrooms based on entries in the 'decorations' map.
     * @param decorationFilePath The file path containing decoration specifications.
     */
    private static void decorateClassrooms(String decorationFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(decorationFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                Classroom classroom = classrooms.get(parts[0]);
                Decoration wallDecoration = decorations.get(parts[1]);
                Decoration floorDecoration = decorations.get(parts[2]);
                classroom.decorate(wallDecoration, floorDecoration);
            }
        }
    }
    /**
     * Writes out the decoration details and total costs for each classroom to the specified output file.
     * @param outputFilePath The file path where the output will be written.
     */
    private static void writeOutput(String outputFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            double totalCost = 0;
            for (Classroom classroom : classrooms.values()) {
                String details = classroom.getDecorationDetails();
                writer.write(details);
                writer.newLine();
                totalCost += classroom.getDecorationCost();
            }
            writer.write("Total price is: " + Math.round(totalCost) + "TL.");
        }
    }
}