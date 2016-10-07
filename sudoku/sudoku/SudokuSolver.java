//package sudoku;

import com.sun.xml.internal.bind.v2.TODO;

/**
 * Place for your code.
 */
public class SudokuSolver {
	private static int DIMENSION = 9;
	private static int EMPTY = 0;

	private Domain[] cellDomains = new Domain[81];

	/**
	 * Information about possible numbers that can be assigned to a variable
 	 */
	private class Domain {
		boolean[] flags; // All values are default as true/available for assignment

		Domain() {
			flags = new boolean[9];

			for (boolean flag : flags) {
				flag = true;
			}
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
	 * @param row cell row
	 * @param column cell column
     * @return domain index
     */
	private int getDomainIndex(int row, int column) {
		return (row * DIMENSION) + (column);
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
		if (findEmptyCell(board) == null) {
			return board;
		}

		// Generate all possible moves
		// Prune illegal moves
		// for each possible solution (where the cell was filled in)
		// 		solve(board)


		return board;
	}

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
