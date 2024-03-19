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
				// Use Backtracking with Forward Checking
				solveNQueensForwardChecking(0);
			} else if (choice == 2) {
				algorithm = "MAC";
				// Call function here to use Backtracking with Maintaining Arc Consistency (MAC)
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

		int[] domain = new int[n]; // Domain for the current row
		Arrays.fill(domain, 1); // Initially, all columns are potential candidates

		for (int i = 0; i < row; i++) {
			int col = queens[i]; // Column of the queen in row i
			domain[col] = 0; // Eliminate the column from the domain
			int diff = row - i; // Diagonal distance
			if (col + diff < n) {
				domain[col + diff] = 0; // Eliminate diagonal (up-right)
			}
			if (col - diff >= 0) {
				domain[col - diff] = 0; // Eliminate diagonal (up-left)
			}
		}

		for (int col = 0; col < n; col++) {
			if (domain[col] == 1) { // Check if column is still in the domain
				queens[row] = col; // Place the queen
				solveNQueensForwardChecking(row + 1);
				queens[row] = 0; // Backtrack
				backtrackCount++;

				if (solutionCount >= maxSolutions) {
					return;
				}
			}
		}
	}

	@SuppressWarnings("unused")
	@Deprecated
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
