public class SudokuGame {
	int sudokuBoard[][] = {
			{ 9, 0, 0, 1, 0, 0, 0, 0, 5 },
			{ 0, 0, 5, 0, 9, 0, 2, 0, 1 },
			{ 8, 0, 0, 0, 4, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 8, 0, 0, 0, 0 },
			{ 0, 0, 0, 7, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 2, 6, 0, 0, 9 },
			{ 2, 0, 0, 3, 0, 0, 0, 0, 6 },
			{ 0, 0, 0, 2, 0, 0, 9, 0, 0 },
			{ 0, 0, 1, 9, 0, 4, 5, 7, 0 },
	};

	public static final int EMPTY_CELL = 0;
	public static final int SUDOKU_SIZE = 9;

	private boolean checkRow(int row, int number) {
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			if (sudokuBoard[row][i] == number)
				return true;
		}
		return false;
	}

	private boolean checkColumn(int col, int number) {
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			if (sudokuBoard[i][col] == number) {
				return true;
			}
		}
		return false;
	}

	private boolean checksquare(int row, int col, int number) {
		int r = row - row % 3;
		int c = col - col % 3;

		for (int i = r; i < r + 3; i++)
			for (int j = c; j < c + 3; j++)
				if (sudokuBoard[i][j] == number)
					return true;

		return false;
	}

	private boolean CheckValid(int row, int col, int number) {
		return !checkRow(row, number) && !checkColumn(col, number) && !checksquare(row, col, number);

	}

	public boolean solveSudoku() {
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
				if (sudokuBoard[row][col] == EMPTY_CELL) {
					for (int number = 1; number <= SUDOKU_SIZE; number++) {
						if (CheckValid(row, col, number)) {
							sudokuBoard[row][col] = number;
							if (solveSudoku()) {
								return true;
							} else {
								sudokuBoard[row][col] = EMPTY_CELL;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public void printSUDOKU() {
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				System.out.print(" " + sudokuBoard[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		SudokuGame sudokuGame = new SudokuGame();
		System.out.println("Given SUdoku");
		sudokuGame.printSUDOKU();

		if (sudokuGame.solveSudoku()) {
			System.out.println("Solved Sudoku");
			sudokuGame.printSUDOKU();
		} else {
			System.out.println("Sudoku not solvable");
		}
	}

}
