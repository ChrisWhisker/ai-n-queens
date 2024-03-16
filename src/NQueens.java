import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NQueens {
	private static int N; // Size of the chess board (N x N)
	private static int[] queens; // Array to store column positions of queens
	private static List<int[]> solutions; // List to store all solutions found
	private static int solutionCount = 0; // Counter for the number of solutions found
	private static int backtrackCount = 0; // Counter for the total number of backtracks made
	private static int maxSolutions;
	private static String algorithm;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		// Prompt the user to enter the size of the chess board
		System.out.print("Enter the size of the chessboard (N): ");
		N = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		maxSolutions = N * 2;

		// Prompt the user to choose the solving algorithm
		System.out.print("Choose the solving algorithm (1: Forward Checking, 2: Maintaining Arc Consistency): ");
		int choice = scanner.nextInt();
		scanner.close();

		long startTime = System.currentTimeMillis();

		queens = new int[N]; // Initialize the array to store queen positions
		solutions = new ArrayList<>(); // Initialize the list to store solutions

		// Choose the solving algorithm based on user input
		if (choice == 1) {
			algorithm = "FORWARD CHECKING";
			solveNQueensForwardChecking(0); // Use Backtracking with Forward Checking
		} else if (choice == 2) {
			algorithm = "MAC";
			solveNQueensMAC(0); // Use Backtracking with Maintaining Arc Consistency (MAC)
		} else {
			System.out.println("Invalid choice. Please select 1 or 2.");
			return;
		}

		long endTime = System.currentTimeMillis();

		// Print all solutions and statistics
		if (solutions.isEmpty()) {
			System.out.println("No solutions found.");
		} else {
			System.out.println(algorithm);
			System.out.println("Solutions : " + solutionCount);
			System.out.println("Time taken : " + (endTime - startTime) + " milliseconds");
			System.out.println("Backtracks : " + backtrackCount + "\n");
			for (int i = 0; i < solutions.size(); i++) {
				System.out.println("#" + (i + 1));
				printSolution(solutions.get(i)); // Print each solution
			}
		}
	}

	// Backtracking with Forward Checking
	private static void solveNQueensForwardChecking(int row) {
		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (row == N) {
			solutions.add(Arrays.copyOf(queens, N));
			solutionCount++;
			return;
		}

		// Recursive case: Try placing a queen in each column of the current row
		for (int col = 0; col < N; col++) {
			if (isSafe(row, col)) {
				queens[row] = col; // Place the queen
				solveNQueensForwardChecking(row + 1); // Move to the next row
				queens[row] = 0; // Backtrack (remove the queen)
				backtrackCount++;

				if (solutionCount >= maxSolutions) {
					return; // Stop the search if the maximum number of solutions is reached
				}
			}
		}
	}

	// Backtracking with Maintaining Arc Consistency (MAC)
	private static void solveNQueensMAC(int row) {
		// *** Implement backtracking with Maintaining Arc Consistency (MAC) here ***
	}

	// Check if placing a queen at (row, col) is safe using Forward Checking
	private static boolean isSafe(int row, int col) {
		// Check if no queens attack each other horizontally, vertically, or diagonally
		for (int i = 0; i < row; i++) {
			if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
				return false;
			}
		}
		return true;
	}

	// Print the chessboard with queens placed according to the solution
	private static void printSolution(int[] solution) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (solution[i] == j) {
					System.out.print("1 "); // Print queen
				} else {
					System.out.print("0 "); // Print empty square
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
