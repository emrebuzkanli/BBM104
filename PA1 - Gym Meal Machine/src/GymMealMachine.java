import java.util.ArrayList;
import java.util.List;

/**
 * Represents a machine for managing gym meal purchases.
 */
public class GymMealMachine {
    private final Slot[][] slots;
    private final List<String> infoMessages;
    private final List<String> errorMessages;

    /**
     * Constructs a GymMealMachine with the given list of products.
     * @param products The list of products to initialize the machine with.
     */
    public GymMealMachine(List<Product> products) {
        slots = new Slot[6][4];
        infoMessages = new ArrayList<>();
        errorMessages = new ArrayList<>();
        fill(products);
    }

    /**
     * Enumeration representing different choices for purchasing.
     */
    public enum Choice {
        PROTEIN, CARB, FAT, CALORIE, NUMBER
    }

    /**
     * Retrieves the list of informational messages.
     * @return The list of informational messages.
     */
    public List<String> getInfoMessages() {
        return infoMessages;
    }

    /**
     * Retrieves the list of error messages.
     * @return The list of error messages.
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Adds an informational message to the list.
     * @param message The informational message to add.
     */
    private void addInfoMessage(String message) {
        infoMessages.add(message);
    }

    /**
     * Adds an error message to the list.
     * @param message The error message to add.
     */
    private void addErrorMessage(String message) {
        errorMessages.add(message);
    }
    /**
     * Fills the slots with products.
     * @param products The list of products to fill the slots with.
     * @return The number of filled slots.
     */
    private int fill(List<Product> products) {
        int filledSlots = 0;
        int totalItems = 0;
        int invalidTry = 0;
        for (Product product : products) {
            boolean productAdded = false;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (totalItems == 240) {
                        productAdded = true;
                        break;
                    }

                    if (slots[i][j] == null || slots[i][j].isEmpty()) {
                        slots[i][j] = new Slot();
                        slots[i][j].setProduct(product);
                        filledSlots++;
                        totalItems++;
                        productAdded = true;
                        break;
                    } else if (slots[i][j].getProduct().getName().equals(product.getName())) {
                        if (slots[i][j].getQuantity() < 10) {
                            slots[i][j].changeNumberOfProducts(+1);
                            filledSlots++;
                            totalItems++;
                            productAdded = true;
                            break;
                        }
                    }
                }
                if (productAdded) break;
            }
            if (!productAdded) {
                String errorMessage = "INFO: There is no available place to put " + product.getName();
                addErrorMessage(errorMessage);
                invalidTry++;
            }
        }

        if (totalItems == 240 && (invalidTry != 0)) {
            String lastProductName = products.get(240 + invalidTry).getName();
            String errorMessage = "INFO: There is no available place to put " + lastProductName;
            addErrorMessage(errorMessage);
            errorMessage = "INFO: The machine is full!";
            addErrorMessage(errorMessage);
            return -1;
        } else {
            return filledSlots;
        }
    }
    /**
     * Finds the slot matching the specified choice and value.
     * @param choice The choice for purchasing.
     * @param value The value associated with the choice.
     * @return The matching slot, or null if not found.
     */
    private Slot findMatchingSlot(Choice choice, double value) {
        if (choice == Choice.NUMBER) {
            int targetSlotNumber = (int) value +1 ;

            int slotCount = 0;

            for (Slot[] row : slots) {
                for (Slot slot : row) {
                    slotCount++;
                    if (slotCount == targetSlotNumber) {
                        return slot;
                    }
                }
            }
        } else {
            for (Slot[] row : slots) {
                for (Slot slot : row) {
                    if (slot != null && !slot.isEmpty()) {
                        Product product = slot.getProduct();
                        switch (choice) {
                            case PROTEIN:
                                if (Math.abs(product.getProtein() - value) <= 5) {
                                    return slot;
                                }
                                break;
                            case CARB:
                                if (Math.abs(product.getCarbohydrate() - value) <= 5) {
                                    return slot;
                                }
                                break;
                            case FAT:
                                if (Math.abs(product.getFat() - value) <= 5) {
                                    return slot;
                                }
                                break;
                            case CALORIE:
                                if (Math.abs(product.getCalorie() - value) <= 5) {
                                    return slot;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        return null; // No matching slot found
    }
    /**
     * Checks if the given amount of money is acceptable.
     * @param money The amount of money to check.
     * @return True if the money is acceptable, false otherwise.
     */
    private boolean isAcceptableMoney(double money) {
        return money == 1 || money == 5 || money == 10 || money == 20 || money == 50 || money == 100 || money == 200;
    }
    /**
     * Processes a purchase request.
     * @param moneyList The list of money provided.
     * @param choice The choice for purchasing.
     * @param value The value associated with the choice.
     * @return The result of the purchase operation.
     */
    public int purchase(List<Double> moneyList, Choice choice, double value) {
        boolean allValidMoney = moneyList.stream().allMatch(this::isAcceptableMoney);
        String formattedInput = formatInput(moneyList, value, choice);
        infoMessages.add("INPUT: " + formattedInput);
        int totalMoney = (int) moneyList.stream().mapToDouble(Double::doubleValue).sum();
        Slot matchingSlot = findMatchingSlot(choice, value);
        if (!allValidMoney) {
            addInfoMessage("INFO: Only 1, 5, 10, 20, 50, 100, or 200 TL are accepted.");
            return -1;
        }
        if (choice == Choice.NUMBER) {
            if ((value < 0 || value > 23)) {
                addInfoMessage("INFO: Number cannot be accepted. Please try again with another number.");
                addInfoMessage("RETURN: Returning your change: " + totalMoney + " TL");
                return-1 ;
                }else if  (matchingSlot == null) {
                    addInfoMessage("INFO: This slot is empty, your money will be returned.");
                    addInfoMessage("RETURN: Returning your change: " + totalMoney + " TL");
                    return-1;
            }
        }
        if (matchingSlot == null) {
            addInfoMessage("INFO: Product not found, your money will be returned.");
            addInfoMessage("RETURN: Returning your change: " + totalMoney + " TL");
            return-1;
        } else if (matchingSlot.isEmpty()) {
            addInfoMessage("INFO: This slot is empty, your money will be returned.");
            addInfoMessage("RETURN: Returning your change: " + totalMoney + " TL");
            return-1;
        }

        // Purchase successful
        Product product = matchingSlot.getProduct();
        if (product.getPrice() <= totalMoney) {
            addInfoMessage("PURCHASE: You have bought one " + product.getName());
            matchingSlot.changeNumberOfProducts(-1); // Decrease the number of products by one
            if (matchingSlot.getQuantity() == 0) {
                matchingSlot.setProduct(null); // Remove the product from the slot
            }
            int change = (int) (totalMoney - product.getPrice());
            if (change >= 0) {
                addInfoMessage("RETURN: Returning your change: " + change + " TL");
                return 1;
            }
        } else {
            addInfoMessage("INFO: Insufficient money, try again with more money.");
            addInfoMessage("RETURN: Returning your change: " + totalMoney + " TL");
            return-1 ;
        }
    return 0;
    }
    /**
     * Formats the input for the purchase transaction.
     * @param moneyList The list of money used for the purchase.
     * @param value The value associated with the choice.
     * @param choice The choice of product.
     * @return The formatted input string.
     */

    private String formatInput(List<Double> moneyList, double value, Choice choice) {
        StringBuilder inputBuilder = new StringBuilder("CASH\t");
        for (int i = 0; i < moneyList.size(); i++) {
            if (i > 0) {
                inputBuilder.append(" ");
            }
            inputBuilder.append((int) Math.round(moneyList.get(i)));
        }
        inputBuilder.append("\t").append(choice).append("\t").append((int) value);
        return inputBuilder.toString();
    }


    /**
     * Holds the output representing the state of the machine.
     * @return The formatted output string.
     */
    public String outputHolder() {
        StringBuilder output = new StringBuilder();
        output.append("-----Gym Meal Machine-----\n");


        for (Slot[] row : slots) {
            for (Slot slot : row) {
                if (slot == null || slot.isEmpty()) {
                    output.append("___(0, 0)");
                } else {
                    Product product = slot.getProduct();
                    output.append(product.getName()).append("(").append(Math.round(product.getCalorie())).append(", ").append(slot.getQuantity()).append(")");
                }
                output.append("___");
            }
            output.append("\n");
        }
        output.append("----------\n");

        return output.toString();
    }

}