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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request Added: " + reservation.getGuestName());
    }

    // View all requests (read-only)
    public void showRequests() {
        System.out.println("\n--- Booking Request Queue (FIFO) ---");

        for (Reservation r : queue) {
            r.display();
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Guest requests (arrival order preserved)
        requestQueue.addRequest(new Reservation("Alice", "Single"));
        requestQueue.addRequest(new Reservation("Bob", "Suite"));
        requestQueue.addRequest(new Reservation("Charlie", "Single"));

        // Display queue (FIFO order)
        requestQueue.showRequests();
    }
}


