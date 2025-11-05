//PRN : 123B1F067
//Sagar Najardhane
//delivery route optimization allpy the bracnk and sound algorithm to determine the least cost delivery route accross cities
import java.util.*;

class Node implements Comparable<Node> {
    int level;
    int pathCost;
    int reducedCost;
    int vertex;
    List<Integer> path;
    int[][] reducedMatrix;

    Node(int n) {
        reducedMatrix = new int[n][n];
        path = new ArrayList<>();
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.reducedCost, other.reducedCost);
    }
}

public class AssignmentNo8 {

    static final int INF = 9999999;

    static void copyMatrix(int[][] src, int[][] dest, int n) {
        for (int i = 0; i < n; i++)
            System.arraycopy(src[i], 0, dest[i], 0, n);
    }

    static int reduceMatrix(int[][] matrix, int n) {
        int reductionCost = 0;

        // Row reduction
        for (int i = 0; i < n; i++) {
            int rowMin = INF;
            for (int j = 0; j < n; j++)
                if (matrix[i][j] < rowMin)
                    rowMin = matrix[i][j];

            if (rowMin != INF && rowMin != 0) {
                reductionCost += rowMin;
                for (int j = 0; j < n; j++)
                    if (matrix[i][j] != INF)
                        matrix[i][j] -= rowMin;
            }
        }

        // Column reduction
        for (int j = 0; j < n; j++) {
            int colMin = INF;
            for (int i = 0; i < n; i++)
                if (matrix[i][j] < colMin)
                    colMin = matrix[i][j];

            if (colMin != INF && colMin != 0) {
                reductionCost += colMin;
                for (int i = 0; i < n; i++)
                    if (matrix[i][j] != INF)
                        matrix[i][j] -= colMin;
            }
        }

        return reductionCost;
    }

    static Node createNode(int[][] parentMatrix, List<Integer> path, int level, int i, int j, int n) {
        Node node = new Node(n);
        copyMatrix(parentMatrix, node.reducedMatrix, n);

        // Block outgoing edges from i
        if (level != 0) {
            for (int k = 0; k < n; k++)
                node.reducedMatrix[i][k] = INF;
        }

        // Block incoming edges to j
        for (int k = 0; k < n; k++)
            node.reducedMatrix[k][j] = INF;

        // Prevent returning to start before completing tour
        if (level + 1 < n)
            node.reducedMatrix[j][0] = INF;

        node.path = new ArrayList<>(path);
        node.path.add(j);
        node.level = level;
        node.vertex = j;
        return node;
    }

    static void solveTSP(int[][] costMatrix, int n) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        List<Integer> path = new ArrayList<>();
        path.add(0);

        Node root = new Node(n);
        copyMatrix(costMatrix, root.reducedMatrix, n);

        root.path = path;
        root.level = 0;
        root.vertex = 0;
        root.pathCost = 0;
        root.reducedCost = reduceMatrix(root.reducedMatrix, n);

        pq.add(root);
        int minCost = INF;
        List<Integer> finalPath = new ArrayList<>();

        while (!pq.isEmpty()) {
            Node minNode = pq.poll();
            int i = minNode.vertex;

            // If all cities visited
            if (minNode.level == n - 1) {
                List<Integer> completePath = new ArrayList<>(minNode.path);
                completePath.add(0); // return to start
                int totalCost = minNode.pathCost + costMatrix[i][0];
                if (totalCost < minCost) {
                    minCost = totalCost;
                    finalPath = completePath;
                }
                continue;
            }

            for (int j = 0; j < n; j++) {
                if (minNode.reducedMatrix[i][j] != INF) {
                    Node child = createNode(minNode.reducedMatrix, minNode.path, minNode.level + 1, i, j, n);
                    child.pathCost = minNode.pathCost + costMatrix[i][j];
                    child.reducedCost = child.pathCost + reduceMatrix(child.reducedMatrix, n);
                    pq.add(child);
                }
            }
        }

        // Output
        System.out.print("\nOptimal Delivery Route (SwiftShip): ");
        for (int i = 0; i < finalPath.size(); i++) {
            System.out.print(finalPath.get(i));
            if (i < finalPath.size() - 1) System.out.print(" ");
        }
        System.out.println("\nMinimum Total Delivery Cost: " + minCost);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of cities: ");
        int n = sc.nextInt();

        int[][] costMatrix = new int[n][n];
        System.out.println("Enter cost matrix (use large number for no direct route):");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                costMatrix[i][j] = sc.nextInt();

        solveTSP(costMatrix, n);
        sc.close();
    }
}

