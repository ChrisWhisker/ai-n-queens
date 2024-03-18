import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NQueens {
	private static int n; // Size of the chess board (n x n)
	private static int[] queens; // Array to store column positions of queens
	private static List<int[]> solutions; // List to store all solutions found
	private static int solutionCount = 0; // Counter for the number of solutions found
	private static int backtrackCount = 0; // Counter for the total number of backtracks made
	private static int maxSolutions;
	private static String fileContent = "";

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			// Prompt the user to enter the size of the chess board
			System.out.print("Enter the size of the chessboard (N): ");
			n = scanner.nextInt();
			scanner.nextLine(); // Consume newline
			maxSolutions = n * 2;

			// Prompt the user to choose the solving algorithm
			System.out.print("Choose the solving algorithm (1: Forward Checking, 2: Maintaining Arc Consistency): ");
			int choice = scanner.nextInt();

			queens = new int[n]; // Initialize the array to store queen positions
			solutions = new ArrayList<>(); // Initialize the list to store solutions
			String algorithm;
			long startTime = System.currentTimeMillis();

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
			fileContent += algorithm + "\n";
			fileContent += "Solutions : " + solutionCount;
			if (solutionCount == maxSolutions) {
				// No more solutions are required to be found according to project description
				fileContent += " (max required for n of " + n + ")";
			}
			fileContent += "\nTime taken : " + (endTime - startTime) + " milliseconds\n";
			fileContent += "Backtracks : " + backtrackCount + "\n\n";
			for (int i = 0; i < solutions.size(); i++) {
				fileContent += "#" + (i + 1) + "\n";
				fileContent += formatSolution(solutions.get(i));
			}

			writeToFile(algorithm + "_" + n + ".txt", fileContent);
			System.out.println(fileContent);
		}
	}

	// Backtracking with Forward Checking
	private static void solveNQueensForwardChecking(int row) {
		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (row == n) {
			solutions.add(Arrays.copyOf(queens, n));
			solutionCount++;
			return;
		}

		// Recursive case: Try placing a queen in each column of the current row
		for (int col = 0; col < n; col++) {
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
		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (row == n) {
			solutions.add(Arrays.copyOf(queens, n));
			solutionCount++;
			return;
		}

		// Recursive case: Try placing a queen in each column of the current row
		for (int col = 0; col < n; col++) {
			queens[row] = col; // Place the queen

			// Apply MAC (Maintaining Arc Consistency)
			boolean consistent = applyMAC(row);
			if (consistent) {
				solveNQueensMAC(row + 1); // Move to the next row
			}

			queens[row] = 0; // Backtrack (remove the queen)
			backtrackCount++;

			if (solutionCount >= maxSolutions) {
				return; // Stop the search if the maximum number of solutions is reached
			}
		}
	}

	// Apply MAC (Maintaining Arc Consistency) after placing a queen in the current
	// row
	private static boolean applyMAC(int row) {
		for (int i = 0; i < row; i++) {
			if (!revise(row, i)) {
				return false; // Inconsistent assignment, backtrack
			}
		}
		return true; // Assignment consistent so far
	}

	// Revise the domain of the variable at row 'row' with respect to the constraint
	// imposed by the variable at row 'i'
	private static boolean revise(int row, int i) {
		int col1 = queens[row];
		int col2 = queens[i];

		// Check if the two queens attack each other diagonally
		return Math.abs(col1 - col2) != Math.abs(row - i); // Consistent if not attacking each other diagonally
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

	// Print the chess board with queens placed according to the solution
	private static void printSolution(int[] solution) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(solution[i] == j ? "1 " : "0 "); // Print queen or empty square
			}
			System.out.println();
		}
		System.out.println();
	}

	private static String formatSolution(int[] solution) {
		String formatted = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				formatted += solution[i] == j ? "1 " : "0 "; // Add queen or empty square
			}
			formatted += "\n";
		}
		formatted += "\n";
		return formatted;
	}

	private static void writeToFile(String filePath, String content) {
		try {
			// Create a FileWriter object
			FileWriter writer = new FileWriter(filePath);

			// Write the content to the file
			writer.write(content);

			// Close the FileWriter object
			writer.close();

			System.out.println("Successfully wrote to the file: " + filePath);
		} catch (IOException e) {
			// Print the error message if an exception occurs
			System.out.println("An error occurred while writing to the file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
