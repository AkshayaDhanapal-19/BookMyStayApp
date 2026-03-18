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

// Inventory (Thread-Safe)
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public synchronized void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public synchronized boolean allocateRoom(String type) {
        int count = availability.getOrDefault(type, 0);

        if (count > 0) {
            availability.put(type, count - 1);
            return true;
        }
        return false;
    }

    public synchronized int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Shared Booking Queue (Thread-Safe)
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private Inventory inventory;

    public BookingProcessor(BookingQueue queue, Inventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getRequest();
            }

            if (r != null) {
                boolean success = inventory.allocateRoom(r.getRoomType());

                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " → Booking Confirmed: " + r.getGuestName()
                            + " (" + r.getRoomType() + ")");
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " → Booking Failed: " + r.getGuestName()
                            + " (" + r.getRoomType() + ")");
                }
            }
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 2);

        BookingQueue queue = new BookingQueue();

        // Simulate multiple guest requests
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // may fail

        // Multiple threads (concurrent processing)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}