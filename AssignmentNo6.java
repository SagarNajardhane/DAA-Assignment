import java.util.*;

class CargoItem {
    int id;
    int weight;
    int utility;
    boolean perishable;

    CargoItem(int id, int weight, int utility, boolean perishable) {
        this.id = id;
        this.weight = weight;
        this.utility = utility;
        this.perishable = perishable;
    }
}

public class AssignmentNo6 {

    // Boost utility of perishable items
    static void boostPerishableUtility(List<CargoItem> items) {
        for (CargoItem it : items) {
            if (it.perishable) {
                it.utility = (int) (it.utility * 1.2); // 20% boost
            }
        }
    }

    // Display selected items
    static void displaySelectedItems(int[][] dp, List<CargoItem> items, int capacity) {
        int i = items.size();
        int w = capacity;
        int totalWeight = 0;

        System.out.println("\nItems loaded in the truck:");
        while (i > 0 && w > 0) {
            if (dp[i][w] != dp[i - 1][w]) {
                CargoItem it = items.get(i - 1);
                System.out.println("✅ Item " + it.id +
                        " | Weight: " + it.weight +
                        " | Utility: " + it.utility +
                        " | Perishable: " + (it.perishable ? "Yes" : "No"));
                totalWeight += it.weight;
                w -= it.weight;
            }
            i--;
        }
        System.out.println("Total Weight Loaded: " + totalWeight + " kg");
    }

    // Dynamic Programming - 0/1 Knapsack
    static int knapsackDP(List<CargoItem> items, int capacity) {
        int n = items.size();
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                CargoItem it = items.get(i - 1);
                if (it.weight <= w) {
                    dp[i][w] = Math.max(it.utility + dp[i - 1][w - it.weight], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        displaySelectedItems(dp, items, capacity);
        return dp[n][capacity];
    }

    public static void main(String[] args) {
        System.out.println("🚚 SwiftCargo - Truck Loading Optimization (Knapsack with Perishables)");

        List<CargoItem> items = new ArrayList<>();
        items.add(new CargoItem(1, 10, 60, true));
        items.add(new CargoItem(2, 20, 100, false));
        items.add(new CargoItem(3, 30, 120, true));
        items.add(new CargoItem(4, 25, 90, false));
        items.add(new CargoItem(5, 15, 75, true));

        int truckCapacity = 50;

        boostPerishableUtility(items);

        int maxUtility = knapsackDP(items, truckCapacity);
        System.out.println("\n📦 Maximum Total Utility (using DP): " + maxUtility);
    }
}
