import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import org.junit.jupiter.api.DisplayName;

// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.ValueSource;

class GameLogicTest {
	final int ROW_COUNT = 6, COLUMN_COUNT = 7;

	Integer[][] matrix;

	@BeforeEach
	void initMatrixToDefaultBoard() {
		matrix = new Integer[ROW_COUNT][COLUMN_COUNT];
		for (int row = 0; row < ROW_COUNT; ++row) {
			for (int col = 0; col < COLUMN_COUNT; ++col) {
				matrix[row][col] = 0;
			}
		}
	}

	@Test
	void emptyState() {
		for (int row = 0; row < ROW_COUNT; ++row) {
			for (int col = 0; col < COLUMN_COUNT; ++col) {
				assertFalse(checkWinner.checkWinnerTest(matrix, col, row));
			}
		}
	}

	@Test
	void rowWinningSequences() {
		for (int row = 0; row < ROW_COUNT; ++row) {
			for (int col = 0; col < (COLUMN_COUNT - 3); ++col) {
				// Sets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row][col+c] = 1;
				}
				
				// Checks winning sequence at all positions on winning
				// 		sequence.
				for (int c = 0; c < 4; ++c) {
					assertTrue(checkWinner.checkWinnerTest(matrix, col+c, row));
				}

				// Resets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row][col+c] = 0;
				}
			}
		}
	}

	@Test
	void columnWinningSequences() {
		for (int row = 0; row < (ROW_COUNT-4); ++row) {
			for (int col = 0; col < COLUMN_COUNT; ++col) {
				// Sets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row+c][col] = 1;
				}

				// Checks winning sequence at all positions on winning
				// 		sequence.
				for (int c = 0; c < 4; ++c) {
					assertTrue(checkWinner.checkWinnerTest(matrix, col, row+c));
				}

				// Resets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row+c][col] = 0;
				}
			}
		}
	}

	@Test
	void diagonalWinningSequencesBackwardSlashShape() {
		for (int row = 3; row < ROW_COUNT; ++row) {
			for (int col = 0; col < 4; ++col) {
				// Sets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row-c][col+c] = 1;
				}

				// Checks winning sequence at all positions on winning
				// 		sequence.
				for (int c = 0; c < 4; ++c) {
					assertTrue(checkWinner.checkWinnerTest(matrix, col+c, row-c));
				}

				// Resets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row-c][col+c] = 0;
				}
			}
		}
	}

	@Test
	void diagonalWinningSequencesForwardSlashShape() {
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 4; ++col) {
				// Sets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row+c][col+c] = 1;
				}

				// Checks winning sequence at all positions on winning
				// 		sequence.
				for (int c = 0; c < 4; ++c) {
					assertTrue(checkWinner.checkWinnerTest(matrix, col+c, row+c));
				}

				// Resets pieces
				for (int c = 0; c < 4; ++c) {
					matrix[row+c][col+c] = 0;
				}
			}
		}
	}
}
