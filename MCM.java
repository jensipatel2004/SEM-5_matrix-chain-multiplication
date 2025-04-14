import java.util.Scanner;

public class MCM {

    // Function to perform matrix chain multiplication and return the order and number of operations
    public static void matrixChainOrderWithParentheses(int[] dims, int n) {
        // Initialize DP tables for storing minimum multiplications (dp) and the split points (s)
        int[][] dp = new int[n][n];
        int[][] s = new int[n][n]; // s[i][j] stores the index of the matrix where the optimal split occurs

        // Initialize the diagonal values to 0 (base case: multiplying a single matrix requires 0 operations)
        for (int i = 1; i < n; i++) {
            dp[i][i] = 0;
        }

        // Loop over chain lengths (starting from length 2 to the full chain)
        for (int chainLength = 2; chainLength < n; chainLength++) {
            // Loop to calculate the minimum cost of multiplying matrices i through j
            for (int i = 1; i < n - chainLength + 1; i++) {
                int j = i + chainLength - 1;
                dp[i][j] = Integer.MAX_VALUE; // Initialize to a large number for comparison
                
                // Try all possible places to split the chain between i and j
                for (int k = i; k < j; k++) {
                    int q = dp[i][k] + dp[k + 1][j] + dims[i - 1] * dims[k] * dims[j];
                    
                    // Update dp[i][j] if a lower cost is found
                    if (q < dp[i][j]) {
                        dp[i][j] = q;
                        s[i][j] = k; // Store the split point
                    }
                }
            }
        }

        // Print the minimum number of scalar multiplications needed
        System.out.println("Minimum number of multiplications is " + dp[1][n - 1]);
        
        // Print the optimal parenthesization of the matrices
        System.out.print("Optimal parenthesization is: ");
        printOptimalParentheses(s, 1, n - 1);
        System.out.println();  // Move to the next line after printing the parenthesization
        
        // Print the DP matrix for the minimum number of multiplications
        System.out.println("\nMatrix Multiplication DP Table:");
        printMatrix(dp, n);

        // Print the split table showing the optimal split points
        System.out.println("\nMatrix Split Table:");
        printMatrix(s, n);
    }

    // Function to print the optimal matrix multiplication order using the 's' array
    public static void printOptimalParentheses(int[][] s, int i, int j) {
        // Base case: If only one matrix, print its name (A1, A2, etc.)
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("("); // Opening parenthesis
            // Recursively print the optimal parenthesization for the two subchains
            printOptimalParentheses(s, i, s[i][j]);
            printOptimalParentheses(s, s[i][j] + 1, j);
            System.out.print(")"); // Closing parenthesis
        }
    }

    // Function to print the matrix (for debugging purposes)
    public static void printMatrix(int[][] matrix, int n) {
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Function to initialize the matrix dimensions based on user input
    public static int[] initializeDims(Scanner scanner) {
        System.out.print("Enter the number of matrices: ");
        int n = scanner.nextInt();
        int[] dims = new int[n + 1];  // We nevscode-webview://0fqb9t0omdfdrhpr1gsiu7has1juckdrpokfhu5foa6prnf2esc9/workspace/448666d6-c943-4ad7-8979-aff3bb99247c/request/42402285-ed559d23-da07-487f-be49-f6bd9960d3cfed n + 1 values to store matrix dimensions

        System.out.println("Enter the dimensions of the matrices (e.g., 30 35 for 30x35 matrix):");
        for (int i = 0; i < n + 1; i++) {
            dims[i] = scanner.nextInt();
        }

        return dims;
    }

    // Function to run the program with user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Menu for the user to choose an option
        int choice;
        do {
            System.out.println("\nMatrix Chain Multiplication Menu:");
            System.out.println("1. Compute the minimum number of multiplications, optimal parenthesization, DP table, and split table");
            System.out.println("2. Exit into the program");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: // Compute minimum multiplications, optimal parenthesization, DP table, and split table
                    int[] dims = initializeDims(scanner);
                    int n = dims.length;

                    // Call the function to compute the minimum number of multiplications
                    matrixChainOrderWithParentheses(dims, n);
                    break;

                case 2: // Exit
                    System.out.println("Exiting the program.");
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 2); // Repeat until the user chooses to exit

        scanner.close();
    }
}
