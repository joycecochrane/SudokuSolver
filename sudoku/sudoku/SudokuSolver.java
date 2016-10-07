import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Place for your code.
 */
public class SudokuSolver {
	private static int DIMENSION = 9;
	private static int BLOCK_DIMEN = 3;
	private static int EMPTY = 0;

	private Domain[] cellDomains = new Domain[81];

	/**
	 * Information about possible numbers that can be assigned to a variable
 	 */
	private class Domain {
		boolean[] flags; // All values are default as true/available for assignment
		int size;

		Domain() {
			flags = new boolean[9];

			for (int i = 0; i < DIMENSION; i++) {
				flags[i] = true;
				size = 9;
			}
		}

		/**
		 * Update the domain value flags
		 * @param unavailable
         * @return
         */
		public void updateFlags(Set<Integer> unavailable) {
			for (int i = 0; i < DIMENSION; i++) {
				if (unavailable.contains(i)) {
					flags[i] = false;
					size--;
				}
			}
		}

		/**
		 * Generate the next possible boards.
		 * @param board
		 * @param pos
         * @return
         */
		public List<int[][]> generateNextBoards(int[][] board, Position pos) {
			List<int[][]> boards = new ArrayList<>(size);
			for (int i = 0; i < DIMENSION; i++) {
				if (flags[i]) {
					board[pos.row][pos.column] = i;
					boards.add(board);
				}
			}
			return boards;
		}

	}

	private class Position {
		int row;
		int column;

		Position(int row, int column) {
			this.row = row;
			this.column = column;
		}
	}

	/**
	 * Get the corresponding index for the cell domains given the row and column of the cell.
	 * @param pos
     * @return domain index
     */
	private int getDomainIndex(Position pos) {
		return (pos.row * DIMENSION) + (pos.column);
	}

	/**
	 * @return names of the authors and their student IDs (1 per line).
	 */
	public String authors() {
		return "Author 1: Joyce Ngu - 16179146\n" +
				"Author 2: Alice Foster - 41250145";
	}

	/**
	 * Performs constraint satisfaction on the given Sudoku board using Arc Consistency
	 * and Domain Splitting.
	 * 
	 * @param board the 2d int array representing the Sudoku board. Zeros indicate unfilled cells.
	 * @return the solved Sudoku board
	 */
	public int[][] solve(int[][] board) {
		// base case
		Position firstEmpty = findEmptyCell(board);
		if (firstEmpty == null) {
			return board;
		}

		// Find the illegal domain values, and prune domain
		Set<Integer> illegalValues = getUnavailableValues(board, firstEmpty);
		Domain cellDomain = cellDomains[getDomainIndex(firstEmpty)];
		cellDomain.updateFlags(illegalValues);

		// Generate all possible moves
		List<int[][]> nextBoards = cellDomain.generateNextBoards(board, firstEmpty);
		for (int[][] next : nextBoards) {
			solve(next);
		}

		return null;
	}

	/**
	 * Return a set of values to remove from a cell domain.
	 * @param board
	 * @param pos
     * @return
     */
	private Set<Integer> getUnavailableValues(int[][] board, Position pos) {
		Set<Integer> unavailable = new HashSet<>(9);
		for (int i = 0; i < DIMENSION; i++) {
			if (board[i][pos.column] != EMPTY) {
				unavailable.add(board[i][pos.column]);
			}
			if (board[pos.row][i] != EMPTY) {
				unavailable.add(board[pos.row][i]);
			}
		}

		// Check block
		int col_start = (int) Math.floor(pos.column / 3) * 3;
		int row_start = (int) Math.floor(pos.row / 3) * 3;

		for (int i = row_start; i < row_start + BLOCK_DIMEN; i++) {
			for (int j = col_start; i < col_start + BLOCK_DIMEN; j++) {
				if (board[i][j] != EMPTY) {
					unavailable.add(board[i][j]);
				}
			}
		}
		return unavailable;
	}


	/**
	 * Return the first empty cell if it exists.
	 * @param board
	 * @return
     */
	private Position findEmptyCell(int[][] board) {
		for (int row = 0; row < DIMENSION - 1; row++) {
			for (int col = 0; col < DIMENSION - 1; col++) {
				if (board[row][col] == EMPTY) {
					return new Position(row, col);
				}
			}
		}
		return null;
	}
}
