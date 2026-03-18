class SearchService {
    private Inventory inventory;
    private Map<String, Room> rooms;

    public SearchService(Inventory inventory, Map<String, Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    // Read-only search
    public void searchRooms() {
        System.out.println("\n--- Available Rooms ---");

        for (String type : inventory.getRoomTypes()) {
            int available = inventory.getAvailability(type);

            if (available > 0 && rooms.containsKey(type)) {
                Room room = rooms.get(type);

                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        // Assuming you already have these initialized
        Inventory inventory = new Inventory();
        Map<String, Room> roomMap = new HashMap<>();

        // Example data (remove if already present in your code)
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 2);

        roomMap.put("Single", new Room("Single", 2000, Arrays.asList("WiFi", "AC")));
        roomMap.put("Double", new Room("Double", 3500, Arrays.asList("WiFi", "AC", "TV")));
        roomMap.put("Suite", new Room("Suite", 6000, Arrays.asList("WiFi", "AC", "TV", "Mini Bar")));

        // -------- UC4: Room Search --------
        SearchService searchService = new SearchService(inventory, roomMap);
        searchService.searchRooms();
    }

}
