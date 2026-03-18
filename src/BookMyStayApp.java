class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // dequeue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Room Allocation Service (UC6)
class RoomAllocationService {
    private Inventory inventory;

    // Track allocated room IDs (prevent duplicates)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → assigned room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 1;

    public RoomAllocationService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void processRequests(BookingRequestQueue queue) {
        System.out.println("\n--- Processing Booking Requests ---");

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = type + "-" + roomCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {
                    allocatedRoomIds.add(roomId);

                    // Map room type → IDs
                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    // Update inventory
                    inventory.reduceRoom(type);

                    System.out.println("Booking Confirmed: " + r.getGuestName()
                            + " | Room: " + roomId);
                }

            } else {
                System.out.println("Booking Failed (No Availability): "
                        + r.getGuestName() + " | " + type);
            }
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        // Inventory setup
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Suite", 1);

        // Queue (UC5)
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // should fail
        queue.addRequest(new Reservation("David", "Suite"));

        // Allocation (UC6)
        RoomAllocationService service = new RoomAllocationService(inventory);
        service.processRequests(queue);
    }
}




