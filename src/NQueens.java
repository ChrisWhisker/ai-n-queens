import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NQueens {
	private static int boardSize; // Size of the chess board (n x n)
	private static int[] queenColumns; // Array to store column positions of queens
	private static List<int[]> solutionsFound; // List to store all solutions found
	private static int solutionCount = 0; // Counter for the number of solutions found
	private static int backtrackCount = 0; // Counter for the total number of backtracks made
	private static int maxSolutions;
	private static StringBuilder fileContent = new StringBuilder();

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			// Prompt the user to enter the size of the chess board
			System.out.print("Enter the size of the chessboard (N): ");
			boardSize = scanner.nextInt();
			scanner.nextLine(); // Consume newline
			maxSolutions = boardSize * 2;

			// Prompt the user to choose the solving algorithm
			System.out.print("Choose the solving algorithm (1: Forward Checking, 2: Maintaining Arc Consistency): ");
			int choice = scanner.nextInt();

			queenColumns = new int[boardSize]; // Initialize the array to store queen positions
			solutionsFound = new ArrayList<>(); // Initialize the list to store solutions
			String algorithm;
			long startTime = System.currentTimeMillis();

			// Choose the solving algorithm based on user input
			if (choice == 1) {
				algorithm = "FORWARD CHECKING";
				// Use Backtracking with Forward Checking
				solveNQueensForwardChecking(0, new int[boardSize]);
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
				fileContent.append(" (max required for n of " + boardSize + ")");
			}
			fileContent.append("\nTime taken : " + (endTime - startTime) + " milliseconds\n");
			fileContent.append("Backtracks : " + backtrackCount + "\n\n");
			for (int i = 0; i < solutionsFound.size(); i++) {
				fileContent.append("#" + (i + 1) + "\n");
				fileContent.append(formatSolution(solutionsFound.get(i)));
			}

			writeToFile(algorithm + "_" + boardSize + ".txt", fileContent.toString());
			System.out.println(fileContent);
		}
	}

	// Backtracking with Forward Checking
	private static void solveNQueensForwardChecking(int currentRow, int[] domain) {
		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (currentRow == boardSize) {
			solutionsFound.add(Arrays.copyOf(queenColumns, boardSize));
			solutionCount++;
			return;
		}

		Arrays.fill(domain, 1); // Initially, all columns are potential candidates

		// Eliminate potential columns and diagonals based on existing queen placements
		for (int i = 0; i < currentRow; i++) {
			int queenColumn = queenColumns[i]; // Column of the queen in row i
			domain[queenColumn] = 0; // Eliminate the column from the domain
			int distance = currentRow - i; // Diagonal distance
			if (queenColumn + distance < boardSize) {
				domain[queenColumn + distance] = 0; // Eliminate diagonal (up-right)
			}
			if (queenColumn - distance >= 0) {
				domain[queenColumn - distance] = 0; // Eliminate diagonal (up-left)
			}
		}

		// Try placing a queen in each potential column
		for (int column = 0; column < boardSize; column++) {
			if (domain[column] == 1) { // Check if column is still in the domain
				queenColumns[currentRow] = column; // Place the queen
				int[] newDomain = domain.clone(); // Clone domain for the current recursive call
				pruneDomainForwardChecking(currentRow, column, newDomain); // Prune domain based on the newly placed
																			// queen

				solveNQueensForwardChecking(currentRow + 1, newDomain); // Recur for the next row
				queenColumns[currentRow] = 0; // Backtrack
				backtrackCount++;

				if (solutionCount >= maxSolutions) {
					return;
				}
			}
		}
	}

	// Prune forward-checking domain based on the queen placed at row and col
	private static void pruneDomainForwardChecking(int row, int col, int[] domain) {
		for (int i = row + 1; i < boardSize; i++) {
			int distance = i - row;
			domain[col] = 0; // Vertical attack
			if (col - distance >= 0) {
				domain[col - distance] = 0; // Diagonal attack (up-left)
			}
			if (col + distance < boardSize) {
				domain[col + distance] = 0; // Diagonal attack (up-right)
			}
		}
	}

	// Backtracking with Maintaining Arc Consistency (MAC)
	private static void solveNQueensMAC(int currentRow) {
		if (solutionCount >= maxSolutions) {
			// Stop the search if the maximum number of solutions is reached
			return;
		}

		// Base case: If all queens are placed successfully, add the solution to the
		// list
		if (currentRow == boardSize) {
			solutionsFound.add(Arrays.copyOf(queenColumns, boardSize));
			solutionCount++;
			return;
		}

		// Recursive case: Try placing a queen in each column of the current row
		for (int column = 0; column < boardSize; column++) {
			queenColumns[currentRow] = column; // Place the queen

			// Apply MAC (Maintaining Arc Consistency)
			boolean consistent = applyMAC(currentRow);
			if (consistent) {
				solveNQueensMAC(currentRow + 1); // Move to the next row
			} else {
				queenColumns[currentRow] = 0; // Backtrack (remove the queen)
				backtrackCount++;
			}
		}
	}

	// Apply MAC (Maintaining Arc Consistency) after placing a queen in the current
	// row
	private static boolean applyMAC(int row) {
		for (int i = 0; i < row; i++) {
			int queenCol1 = queenColumns[i];
			int queenCol2 = queenColumns[row];
			int rowDifference = row - i;

			// Check if queens attack each other diagonally or in the same column
			if (queenCol1 == queenCol2 || queenCol1 + rowDifference == queenCol2
					|| queenCol1 - rowDifference == queenCol2) {
				return false;
			}
		}
		return true;
	}

	private static String formatSolution(int[] solution) {
		StringBuilder formatted = new StringBuilder();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
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
