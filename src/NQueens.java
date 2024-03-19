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
	private static StringBuilder fileContent = new StringBuilder();

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
				solveNQueensForwardChecking(0, new int[n]);
			} else if (choice == 2) {
				algorithm = "MAC";
				// Call function here to use Backtracking with Maintaining Arc Consistency (MAC)
				solveNQueensMAC(0);
			} else {
				System.out.println("Invalid choice. Please select 1 or 2.");
				return;
			}

			long endTime = System.currentTimeMillis();

			// Print all solutions and statistics
			fileContent.append(algorithm + "\n");
			fileContent.append("Solutions : " + solutionCount);
			if (solutionCount == maxSolutions) {
				// No more solutions are required to be found according to project description
				fileContent.append(" (max required for n of " + n + ")");
			}
			fileContent.append("\nTime taken : " + (endTime - startTime) + " milliseconds\n");
			fileContent.append("Backtracks : " + backtrackCount + "\n\n");
			for (int i = 0; i < solutions.size(); i++) {
				fileContent.append("#" + (i + 1) + "\n");
				fileContent.append(formatSolution(solutions.get(i)));
			}

			writeToFile(algorithm + "_" + n + ".txt", fileContent.toString());
			System.out.println(fileContent);
		}
	}

	// Backtracking with Forward Checking
	private static void solveNQueensForwardChecking(int row, int[] domain) {
		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (row == n) {
			solutions.add(Arrays.copyOf(queens, n));
			solutionCount++;
			return;
		}

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
				int[] newDomain = domain.clone(); // Clone domain for the current recursive call
				pruneDomainFC(row, col, newDomain); // Prune domain based on the newly placed queen

				// System.out.println("placing queen at row " + row + " col " + col);

				solveNQueensForwardChecking(row + 1, newDomain);
				queens[row] = 0; // Backtrack
				backtrackCount++;

				if (solutionCount >= maxSolutions) {
					return;
				}
			}
		}
	}

	// Prune forward-checking domain based on the queen placed at row and col
	private static void pruneDomainFC(int row, int col, int[] domain) {
		for (int i = row + 1; i < n; i++) {
			int diff = i - row;
			domain[col] = 0; // Vertical attack
			if (col - diff >= 0) {
				domain[col - diff] = 0; // Diagonal attack (up-left)
			}
			if (col + diff < n) {
				domain[col + diff] = 0; // Diagonal attack (up-right)
			}
		}
	}

	// Backtracking with Maintaining Arc Consistency (MAC)
	private static void solveNQueensMAC(int row) {
		if (solutionCount >= maxSolutions) {
			// System.out.println("Maximum solutions reached. Stopping search.");
			// Stop the search if the maximum number of solutions is reached
			return;
		}

		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (row == n) {
			solutions.add(Arrays.copyOf(queens, n));
			solutionCount++;
			// System.out.println("Solution found. Incrementing solution count."); //
			// Solution found
			return;
		}

		// Recursive case: Try placing a queen in each column of the current row
		for (int col = 0; col < n; col++) {
			queens[row] = col; // Place the queen
			// System.out.println("Placed queen at row " + row + ", column " + col); //
			// Queen placed at (row, col)

			// Apply MAC (Maintaining Arc Consistency)
			boolean consistent = applyMAC(row);
			if (consistent) {
				solveNQueensMAC(row + 1); // Move to the next row
			} else {
				queens[row] = 0; // Backtrack (remove the queen)
				backtrackCount++;
				// System.out.println("Backtracked to row " + row); // Backtracked
			}
		}
	}

	// Apply MAC (Maintaining Arc Consistency) after placing a queen in the current
	// row
	private static boolean applyMAC(int row) {
		for (int i = 0; i < row; i++) {
			int col1 = queens[i];
			int col2 = queens[row];
			int rowDiff = row - i;
			if (col1 == col2 || col1 + rowDiff == col2 || col1 - rowDiff == col2) {
				// System.out.println("Queens at row " + i + " and row " + row + " attack each
				// other.");
				return false;
			}
		}
		return true;
	}

	private static String formatSolution(int[] solution) {
		StringBuilder formatted = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				formatted.append(solution[i] == j ? "1 " : "0 "); // Add queen or empty square
			}
			formatted.append("\n");
		}
		formatted.append("\n");
		return formatted.toString();
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
