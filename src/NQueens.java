import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NQueens {
	static int boardSize; // Size of the chess board (n x n)
	static int[] queenColumns; // Column positions of queens
	static List<int[]> solutionsFound; // All solutions found
	static int maxSolutions; // Max solutions required for this board size (equals size * 2)
	static int backtrackCount = 0; // Total number of backtracks made
	static StringBuilder fileContent = new StringBuilder(); // Contents of file to write to
	static String algorithm; // Algorithm used for solving
	static long executionTime; // Time from start to end of solving

	public static void main(String[] args) {
		int choice = 0;

		if (args.length == 0) { // For testing
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the size of the chessboard (N): ");
			boardSize = scanner.nextInt();
			scanner.nextLine();

			System.out.print("Choose the solving algorithm (1: Forward Checking, 2: Maintaining Arc Consistency): ");
			choice = scanner.nextInt();
			scanner.close();
		} else if (args.length >= 2) {
			switch (args[0]) {
			case "FOR":
				choice = 1;
				break;
			case "MAC":
				choice = 2;
				break;
			default:
				System.out.print("Invalid choice of algorithm. Must be FOR or MAC.");
				return;
			}
			boardSize = Integer.valueOf(args[1]);
		}

		maxSolutions = boardSize * 2;

		// Initialize arrays
		queenColumns = new int[boardSize];
		solutionsFound = new ArrayList<>();

		long startTime = System.currentTimeMillis();

		// Choose the solving algorithm based on user input
		if (choice == 1) {
			algorithm = "FORWARD CHECKING";
			// Use Forward Checking
			solveNQueensForwardChecking(0, new int[boardSize]);
		} else if (choice == 2) {
			algorithm = "MAC";
			// Use Maintaining Arc Consistency (MAC)
			solveNQueensMAC(0);
		} else {
			System.out.println("Invalid algorithm choice.");
			return;
		}

		long endTime = System.currentTimeMillis();
		executionTime = endTime - startTime;

		// Write stats and solutions to file
		if (args.length > 2) {
			writeResults(args[2]);
		} else {
			writeResults(algorithm + "_" + boardSize + ".txt");
		}
	}

	// Backtracking with Forward Checking
	static void solveNQueensForwardChecking(int currentRow, int[] domain) {
		// Base case: If all queens are placed successfully, save the solution
		if (currentRow == boardSize) {
			solutionsFound.add(Arrays.copyOf(queenColumns, boardSize));
			return;
		}

		Arrays.fill(domain, 1); // Initially, all columns are in the domain

		// Remove columns and diagonals from domain based on existing queen placements
		for (int i = 0; i < currentRow; i++) {
			int queenColumn = queenColumns[i]; // Column of the queen in row i
			domain[queenColumn] = 0; // Remove the column from the domain
			int distance = currentRow - i; // Diagonal distance
			if (queenColumn + distance < boardSize) {
				domain[queenColumn + distance] = 0; // Remove [/] diagonal
			}
			if (queenColumn - distance >= 0) {
				domain[queenColumn - distance] = 0; // Remove [\] diagonal
			}
		}

		// Try placing a queen in each potential column
		for (int column = 0; column < boardSize; column++) {
			if (domain[column] == 1) { // Check if column is in the domain
				queenColumns[currentRow] = column; // Place the queen
				int[] newDomain = domain.clone(); // Clone domain for the current recursive call
				// Prune domain based on the newly placed queen
				pruneDomainForwardChecking(currentRow, column, newDomain);

				solveNQueensForwardChecking(currentRow + 1, newDomain); // Recur for the next row
				queenColumns[currentRow] = 0; // Backtrack
				backtrackCount++;

				if (solutionsFound.size() >= maxSolutions) {
					return;
				}
			}
		}
	}

	// Prune forward-checking domain based on the queen placed at row, col
	static void pruneDomainForwardChecking(int row, int col, int[] domain) {
		for (int i = row + 1; i < boardSize; i++) {
			int distance = i - row;
			domain[col] = 0; // Remove the column from the domain
			if (col - distance >= 0) {
				domain[col - distance] = 0; // Remove [\] diagonal
			}
			if (col + distance < boardSize) {
				domain[col + distance] = 0; // Remove [\] diagonal
			}
		}
	}

	// Backtracking with Maintaining Arc Consistency (MAC)
	static void solveNQueensMAC(int currentRow) {
		if (solutionsFound.size() >= maxSolutions) {
			// Stop the search if the maximum number of solutions is reached
			return;
		}

		// Base case: If all queens are placed successfully, save the solution
		if (currentRow == boardSize) {
			solutionsFound.add(Arrays.copyOf(queenColumns, boardSize));
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

	// Apply MAC after placing a queen in the current row
	static boolean applyMAC(int row) {
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

	// Write the stats and solutions to a file at fileName
	static void writeResults(String fileName) {
		fileContent.append(algorithm + "\n");
		fileContent.append("Solutions : " + solutionsFound.size());
		if (solutionsFound.size() == maxSolutions) {
			// No more solutions are required to be found according to project description
			fileContent.append(" (max required for N of " + boardSize + ")");
		}
		fileContent.append("\nTime taken : " + executionTime + " milliseconds\n");
		fileContent.append("Backtracks : " + backtrackCount + "\n\n");
		for (int i = 0; i < solutionsFound.size(); i++) {
			fileContent.append("#" + (i + 1) + "\n");
			fileContent.append(formatSolution(solutionsFound.get(i)));
		}

		writeToFile(fileName, fileContent.toString());
//		System.out.println(fileContent);
	}

	// Get the string grid with 0s and 1s representing this solution
	static String formatSolution(int[] solution) {
		StringBuilder formatted = new StringBuilder();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				// Add queen or empty cell
				formatted.append(solution[i] == j ? "1 " : "0 ");
			}
			formatted.append("\n");
		}
		formatted.append("\n");
		return formatted.toString();
	}

	// File writing functionality abstracted away here
	static void writeToFile(String filePath, String content) {
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(content);
			writer.close();
			System.out.println("Successfully wrote to the file: " + filePath);
		} catch (IOException e) {
			// Print the error message if an exception occurs
			System.out.println("An error occurred while writing to the file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
