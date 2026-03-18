class Reservation implements Serializable {
    private String guestName;
    private String roomId;
    private String roomType;

    public Reservation(String guestName, String roomId, String roomType) {
        this.guestName = guestName;
        this.roomId = roomId;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room ID: " + roomId +
                " | Type: " + roomType);
    }
}

// Inventory (Serializable)
class Inventory implements Serializable {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public Map<String, Integer> getAvailabilityMap() {
        return availability;
    }

    public void display() {
        System.out.println("Inventory: " + availability);
    }
}

// System State (Serializable)
class SystemState implements Serializable {
    List<Reservation> bookings;
    Inventory inventory;

    public SystemState(List<Reservation> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("State saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("State loaded successfully.");
            return state;

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // Try loading existing state
        SystemState state = service.load();

        if (state == null) {
            // Create new state
            Inventory inventory = new Inventory();
            inventory.addRoom("Single", 2);
            inventory.addRoom("Suite", 1);

            List<Reservation> bookings = new ArrayList<>();
            bookings.add(new Reservation("Alice", "Single-1", "Single"));
            bookings.add(new Reservation("Bob", "Suite-2", "Suite"));

            state = new SystemState(bookings, inventory);
        }

        // Display current state
        System.out.println("\n--- Current System State ---");
        state.inventory.display();

        for (Reservation r : state.bookings) {
            r.display();
        }

        // Save state before exit
        service.save(state);
    }
}