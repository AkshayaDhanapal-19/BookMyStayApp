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

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room ID: " + roomId +
                " | Type: " + roomType);
    }
}

// Booking History (List → preserves order)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
        System.out.println("Added to History: " + r.getGuestName());
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(BookingHistory history) {
        System.out.println("\n--- Booking History ---");

        for (Reservation r : history.getAllReservations()) {
            r.display();
        }
    }

    // Summary Report (count by room type)
    public void generateSummary(BookingHistory history) {
        System.out.println("\n--- Booking Summary ---");

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings (from UC6)
        history.addReservation(new Reservation("Alice", "Single-1", "Single"));
        history.addReservation(new Reservation("Bob", "Single-2", "Single"));
        history.addReservation(new Reservation("David", "Suite-3", "Suite"));

        // Reporting
        BookingReportService report = new BookingReportService();

        report.showAllBookings(history);
        report.generateSummary(history);
    }
}






