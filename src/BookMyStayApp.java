class Reservation {
    private String guestName;
    private String roomId;
    private String roomType;

    public Reservation(String guestName, String roomId, String roomType) {
        this.guestName = guestName;
        this.roomId = roomId;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public void increaseRoom(String type) {
        availability.put(type, availability.getOrDefault(type, 0) + 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.getRoomId(), r);
    }

    public Reservation getReservation(String roomId) {
        return bookings.get(roomId);
    }

    public void removeReservation(String roomId) {
        bookings.remove(roomId);
    }
}

// Cancellation Service (UC10)
class CancellationService {

    private Inventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(Inventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String roomId) {
        System.out.println("\nProcessing Cancellation: " + roomId);

        // Validate existence
        Reservation r = history.getReservation(roomId);

        if (r == null) {
            System.out.println("Error: Booking not found or already cancelled.");
            return;
        }

        // Push to rollback stack
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increaseRoom(r.getRoomType());

        // Remove booking
        history.removeReservation(roomId);

        System.out.println("Cancellation Successful for " + r.getGuestName()
                + " | Room Released: " + roomId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Cancellations): " + rollbackStack);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 0);
        inventory.addRoom("Suite", 0);

        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings
        history.addReservation(new Reservation("Alice", "Single-1", "Single"));
        history.addReservation(new Reservation("Bob", "Suite-2", "Suite"));

        CancellationService service = new CancellationService(inventory, history);

        // Perform cancellations
        service.cancelBooking("Single-1");
        service.cancelBooking("Suite-2");
        service.cancelBooking("Single-1"); // invalid

        service.showRollbackStack();
    }
}