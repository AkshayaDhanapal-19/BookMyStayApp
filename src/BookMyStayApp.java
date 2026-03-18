class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, -1);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int count = getAvailability(type);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        availability.put(type, count - 1);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }
}

// Validator
class BookingValidator {

    public static void validate(String guestName, String roomType, Inventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Suite", 0);

        // Test cases
        String[][] requests = {
                {"Alice", "Single"},     // valid
                {"", "Single"},          // invalid name
                {"Bob", "Double"},       // invalid type
                {"Charlie", "Suite"}     // no availability
        };

        for (String[] req : requests) {
            String name = req[0];
            String type = req[1];

            try {
                System.out.println("\nProcessing: " + name + " → " + type);

                // Validation (Fail-Fast)
                BookingValidator.validate(name, type, inventory);

                // Safe update
                inventory.reduceRoom(type);

                System.out.println("Booking Successful for " + name);

            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}