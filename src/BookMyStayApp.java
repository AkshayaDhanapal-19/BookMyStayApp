public class BookMyStayApp {
    // Centralized room inventory
    static HashMap<String, Integer> roomInventory = new HashMap<>();

    public static void main(String[] args) {

        // UC3 - Initialize inventory
        initializeInventory();

        // Display inventory
        displayInventory();

        // Example: Update availability
        updateAvailability("Single Room", -1);  // One single room booked
        updateAvailability("Suite Room", -2);   // Two suites booked

        System.out.println("\nAfter booking updates:");
        displayInventory();
    }

    // Initialize room availability
    static void initializeInventory() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
    }

    // Display inventory
    static void displayInventory() {
        System.out.println("\n--- Room Inventory ---");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }

    // Update availability
    static void updateAvailability(String roomType, int change) {
        if (roomInventory.containsKey(roomType)) {
            int current = roomInventory.get(roomType);
            roomInventory.put(roomType, current + change);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }
}
