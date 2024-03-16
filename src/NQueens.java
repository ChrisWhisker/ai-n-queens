import java.util.ArrayList;

public class NQueens {

	public static void main(String[] args) {

		String inputFileName;
		String algorithm;

		if (args.length == 2) {
			inputFileName = args[0];
			algorithm = args[1].toUpperCase();
		} else {
			// For testing:
//			inputFileName = "src/Mazes/BFS/Maze1.txt";
//			algorithm = "DFS";
//			System.err.println("Two arguments are required: <fileName.txt> <BFS/DFS/astar>");
//			return;
			solve(5);
		}
	}

	private static void solve(int queens) {
		// Array of queen placements for each column in order, i.e. [0,2,4,1,3]
		int[] solution = new int[queens];

		// Initialize full domains of each column
		ArrayList<ArrayList<Integer>> domains = new ArrayList<>();
		for (int i = 0; i < queens; i++) {
			ArrayList<Integer> column = new ArrayList<>();
			for (int j = 0; j < queens; j++) {
				column.add(j);
			}
			domains.add(column);
		}

		// Populate each column in solution
		for (int i = 0; i < queens; i++) {
			// Just assign the first possible solution in domain
			solution[i] = domains.get(i).get(0);
			System.out.println("\nAssigned " + domains.get(i).get(0) + " to column " + i);

			// Remove any collisions with proceeding columns' domains
			forwardCheck(new Pair<Integer>(i, solution[i]), domains);
//			System.out.println(domains);
		}
		printSolution(solution);
	}

	private static void forwardCheck(Pair<Integer> queenLoc, ArrayList<ArrayList<Integer>> doms) {
		for (int i = queenLoc.a + 1; i < doms.get(0).size(); i++) {
			System.out.println("Forward checking column " + i);
//			System.out.println("before removal: domain of col " + i + ": " + doms.get(i));
			// Remove horizontal collisions
			doms.get(i).remove(queenLoc.b);

			// Remove diagonal collisions
			Integer columnDiff = i - queenLoc.a;
//			System.out.println("\tDiff between fc column and assignment column: " + columnDiff);
//			System.out.println("\tWill remove " + Integer.valueOf(queenLoc.b - columnDiff) + " and "
//					+ Integer.valueOf(queenLoc.b - columnDiff));
			doms.get(i).remove(Integer.valueOf(queenLoc.b + columnDiff));
			doms.get(i).remove(Integer.valueOf(queenLoc.b - columnDiff));
//			System.out.println("\tafter removal: domain of col " + i + ": " + doms.get(i));

		}
	}

	private static void printSolution(int[] sol) {
		System.out.println();
		for (int x = 0; x < sol.length; x++) {
			for (int y = 0; y < sol.length; y++) {
				if (sol[y] == x) {
					System.out.print("1 ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
	}

}
