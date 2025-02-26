import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * Class for processing commands related to voyages.
 */
public class CommandProcessor {
    private final Map<Integer, Voyage> voyages = new HashMap<>();

    /**
     * Processes commands from an input file and writes results to an output file.
     * @param inputFile The path to the input file.
     * @param outputFile The path to the output file.
     */

    public void processFile(String inputFile, String outputFile) {
        String lastCommand = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile, false))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line.trim(), writer);
                if (!line.trim().isEmpty()) {
                    lastCommand = line.split("\\s+")[0]; //
                }
            }
            if (!"Z_REPORT".equals(lastCommand)) {
                printZReport(writer);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes a single line of input.
     * @param line The input line to process.
     * @param writer The PrintWriter object to write output.
     */

    private void processLine(String line, PrintWriter writer) {
        if (line.isEmpty()) return;
        String[] parts = line.split("\\t+");
        writer.println("COMMAND: " + line);
        switch (parts[0]) {
            case "INIT_VOYAGE":
                handleInitVoyage(parts, writer);
                break;
            case "SELL_TICKET":
                handleSellTicket(parts, writer);
                break;
            case "REFUND_TICKET":
                handleRefundTicket(parts, writer);
                break;
            case "CANCEL_VOYAGE":
                handleCancelVoyage(parts, writer);
                break;
            case "PRINT_VOYAGE":
                handlePrintVoyage(parts, writer);
                break;
            case "Z_REPORT":
                printZReport(parts,writer);
                break;
            default:
                writer.println("ERROR: There is no command namely " + parts[0] + ("!"));
                break;
        }
    }

    /**
     * Handles the initialization of a new voyage based on the provided command parts.
     * Prints errors if the command is used erroneously or if the provided parameters are invalid.
     *
     * @param parts  The array containing command parts.
     * @param writer The PrintWriter object to write error messages or initialization details.
     */

    private void handleInitVoyage(String[] parts, PrintWriter writer) {
        if (parts.length < 7) {
            writer.println("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
            return;
        }

        int id = Integer.parseInt(parts[2]);

        if (voyages.containsKey(id)) {
            writer.printf("ERROR: There is already a voyage with ID of %s!",id);
            writer.println();
            return;
        }

        String from = parts[3];
        String to = parts[4];
        int rows = Integer.parseInt(parts[5]);
        double price = Double.parseDouble(parts[6]);
        int refundCut = parts.length > 7 ? Integer.parseInt(parts[7]) : 0;
        int premiumFee = parts.length > 8 ? Integer.parseInt(parts[8]) : 0;
        Voyage voyage;
        if (id<=0){
            writer.printf(("ERROR: %d is not a positive integer, ID of a voyage must be a positive integer!"),id);
            writer.println();
            return;
        }
        if (rows<=0){
            writer.printf(("ERROR: %d is not a positive integer, number of seat rows of a voyage must be a positive integer!"),rows);
            writer.println();
            return;
        }
        if (price<=0){
            writer.printf(("ERROR: %.00f is not a positive number, price must be a positive number!"),price);
            writer.println();
            return;
        }
        if (!((0<=refundCut)&&(refundCut<=100))){
            writer.printf(("ERROR: %d is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!"),refundCut);
            writer.println();
            return;
        }
        if (premiumFee<0){
            writer.printf(("ERROR: %d is not a non-negative integer, premium fee must be a non-negative integer!"),premiumFee);
            writer.println();
            return;
        }
        switch (parts[1]) {
            case "Standard":
                voyage = new StandardVoyage(id, from, to, rows, price, refundCut);
                break;
            case "Premium":
                double premiumSeatPrice = price * (1 + premiumFee / 100.0);
                voyage = new PremiumVoyage(id, from, to, rows, price, refundCut, premiumSeatPrice);
                break;
            case "Minibus":
                voyage = new MinibusVoyage(id, from, to, rows, price);
                break;
            default:
                writer.println("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
                return;
        }

        voyages.put(id, voyage);
        voyage.printInitializationDetails(writer);
    }

    /**
     * Handles the selling of tickets for a voyage based on the provided command parts.
     * Prints errors if the command is used erroneously or if the provided parameters are invalid.
     *
     * @param parts  The array containing command parts.
     * @param writer The PrintWriter object to write error messages or successful sale details.
     */

    private void handleSellTicket(String[] parts, PrintWriter writer) {
        if (parts.length < 3) {
            writer.println("ERROR: Erroneous usage of \"SELL_TICKET\" command!");
            return;
        }

        int voyageId = Integer.parseInt(parts[1]);
        Voyage voyage = voyages.get(voyageId);
        if (voyage == null) {
            writer.printf("ERROR: There is no voyage with ID of %d!",voyageId);
            writer.println();
            return;
        }

        String[] seatNumbersStr = parts[2].split("_");

        for (String seatStr : seatNumbersStr) {
            int seatNumber = Integer.parseInt(seatStr);
            if (seatNumber <= 0) {
                writer.printf(("ERROR: %d is not a positive integer, seat number must be a positive integer!"),seatNumber);
                writer.println();
                return;
            }
            if(seatNumber > voyage.getSeatsCount()){
                writer.println("ERROR: There is no such a seat!");
                return;
            }
            if (voyage.ticketWasSold(seatNumber)) {
                writer.println("ERROR: One or more seats already sold!");
                return;
            }
        }


        double totalSalePrice = 0.0;
        for (String seatStr : seatNumbersStr) {
            int seatNumber = Integer.parseInt(seatStr);
            if (voyage.sellTicket(seatNumber)) {
                totalSalePrice += voyage.getSeatPrice(seatNumber);
            }
        }

        String seatsSold = String.join("-", seatNumbersStr);
        writer.printf("Seat %s of the Voyage %d from %s to %s was successfully sold for %.2f TL.%n",
                seatsSold, voyageId, voyage.getFrom(), voyage.getTo(), totalSalePrice);
    }

    /**
     * Handles the refunding of tickets for a voyage based on the provided command parts.
     * Prints errors if the command is used erroneously or if the provided parameters are invalid.
     *
     * @param parts  The array containing command parts.
     * @param writer The PrintWriter object to write error messages or successful refund details.
     */

    private void handleRefundTicket(String[] parts, PrintWriter writer) {
        if (parts.length < 3) {
            writer.println("ERROR: Erroneous usage of \"REFUND_TICKET\" command!");
            return;
        }

        int voyageId = Integer.parseInt(parts[1]);
        Voyage voyage = voyages.get(voyageId);
        if (voyage == null) {
            writer.printf("ERROR: There is no voyage with ID of %d!", voyageId);
            writer.println();
            return;
        }

        if (voyage instanceof MinibusVoyage) {
            writer.println("ERROR: Minibus tickets are not refundable!");
            return;
        }

        String[] seatNumbersStr = parts[2].split("_");
        double totalRefundAmount = 0.0;
        boolean allValid = true;


        for (String seatStr : seatNumbersStr) {
            int seatNumber = Integer.parseInt(seatStr);
            if (seatNumber <= 0) {
                writer.printf(("ERROR: %d is not a positive integer, seat number must be a positive integer!"),seatNumber);
                writer.println();
                allValid= false;
                break;
            }
            if(seatNumber > voyage.getSeatsCount()){
                writer.println("ERROR: There is no such a seat!");
                allValid= false;
                break;
            }
            if (!voyage.ticketWasSold(seatNumber)) {
                writer.println("ERROR: One or more seats are already empty!");
                allValid = false;
                break;
            }
            totalRefundAmount += voyage.getRefundAmount(seatNumber);
        }

        if (allValid) {
            for (String seatStr : seatNumbersStr) {
                int seatNumber = Integer.parseInt(seatStr);
                voyage.refundTicket(seatNumber);
            }


            writer.printf("Seat %s of the Voyage %d from %s to %s was successfully refunded for %.2f TL.",
                    String.join("-", seatNumbersStr), voyageId, voyage.getFrom(), voyage.getTo(), totalRefundAmount);
            writer.println();
        }
    }

    /**
     * Handles the cancellation of a voyage based on the provided command parts.
     * Prints errors if the command is used erroneously or if the provided parameters are invalid.
     * If the cancellation is successful, prints the cancellation details and removes the voyage from the list.
     *
     * @param parts  The array containing command parts.
     * @param writer The PrintWriter object to write error messages or successful cancellation details.
     */

    private void handleCancelVoyage(String[] parts, PrintWriter writer) {
        if (parts.length != 2) {
            writer.println("ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!");
            return;
        }

        int id = Integer.parseInt(parts[1]);
        if (id <= 0) {
            writer.printf(("ERROR: %d is not a positive integer, ID of a voyage must be a positive integer!"),id);
            writer.println();
            return;
        }
        Voyage voyage = voyages.get(id);
        if (voyage == null) {
            writer.printf(("ERROR: There is no voyage with ID of %d!"),id);
            writer.println();
            return;
        }
        writer.printf("Voyage %d was successfully cancelled!",id);
        writer.println();
        writer.println("Voyage details can be found below:");
        voyage.printVoyageDetails(writer);
        voyage.cancelVoyage();
        voyage.printRevenue(writer);
        voyages.remove(id);

    }

    /**
     * Handles the printing of voyage details and revenue based on the provided command parts.
     * Prints errors if the command is used erroneously or if the provided parameters are invalid.
     *
     * @param parts  The array containing command parts.
     * @param writer The PrintWriter object to write error messages or voyage details.
     */

    private void handlePrintVoyage(String[] parts, PrintWriter writer) {
        if (parts.length != 2) {
            writer.println("ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!");
            return;
        }
        try {
            int voyageId = Integer.parseInt(parts[1]);

            if (voyageId <= 0) {
                writer.printf("ERROR: %d is not a positive integer, ID of a voyage must be a positive integer!", voyageId);
                writer.println();
                return;
            }

            Voyage voyage = voyages.get(voyageId);
            if (voyage == null) {
                writer.printf("ERROR: There is no voyage with ID of %d!", voyageId);
                writer.println();
                return;
            }

            voyage.printVoyageDetails(writer);
            voyage.printRevenue(writer);
        } catch (NumberFormatException e) {
            writer.printf(("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!"),parts[1]);
            writer.println();
        }
    }

    /**
     * Prints the Z Report, including details of all voyages and their revenues.
     * If the command parts length is 1, it prints the Z Report; otherwise, it prints an error message.
     *
     * @param parts  The array containing command parts.
     * @param writer The PrintWriter object to write the Z Report or error messages.
     */

    public void printZReport(String[] parts,PrintWriter writer) {
        if (parts.length ==1){
            writer.println("Z Report:");
            writer.println("----------------");
            if (voyages.isEmpty()) {
                writer.println("No Voyages Available!");
                writer.println("----------------");
            } else {
                for (Map.Entry<Integer, Voyage> entry : voyages.entrySet()) {
                    Voyage voyage = entry.getValue();
                    voyage.printVoyageDetails(writer);
                    voyage.printRevenue(writer);
                    writer.println("----------------");
                }
            }
        } else{
            writer.println("ERROR: Erroneous usage of \"Z_REPORT\" command!");
        }

}

    /**
     * Overloaded method to print the Z Report when no command parts are provided.
     * Prints the Z Report including details of all voyages and their revenues.
     *
     * @param writer The PrintWriter object to write the Z Report.
     */

    private void printZReport(PrintWriter writer) { //Overload
        writer.println("Z Report:");
        writer.println("----------------");
        if (voyages.isEmpty()) {
            writer.println("No Voyages Available!");
            writer.println("----------------");
        } else {

            for (Map.Entry<Integer, Voyage> entry : voyages.entrySet()) {
                Voyage voyage = entry.getValue();
                voyage.printVoyageDetails(writer);
                voyage.printRevenue(writer);
                writer.println("----------------");
            }
        }
    }
}