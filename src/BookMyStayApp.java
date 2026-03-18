class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add services to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Service Added: " + service.getName()
                + " → Reservation: " + reservationId);
    }

    // Display services and total cost
    public void showServices(String reservationId) {
        System.out.println("\n--- Add-On Services for " + reservationId + " ---");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        double total = 0;

        for (AddOnService s : services) {
            System.out.println(s.getName() + " - ₹" + s.getCost());
            total += s.getCost();
        }

        System.out.println("Total Add-On Cost: ₹" + total);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example Reservation IDs (from UC6)
        String res1 = "Single-1";
        String res2 = "Suite-2";

        // Add services
        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("Airport Pickup", 1000));
        manager.addService(res2, new AddOnService("Spa", 1500));

        // Display services
        manager.showServices(res1);
        manager.showServices(res2);
    }
}





