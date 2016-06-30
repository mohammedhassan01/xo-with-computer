package game.engine;

import java.util.Scanner;

public class GameMain {
	private Board board; // the game board
	private GameState currentState; // the current state of the game (of enum
									// GameState)
	private Seed currentPlayer; // the current player (of enum Seed)

	private static Scanner in = new Scanner(System.in); // input Scanner

	/** Constructor to setup the game */
	public GameMain() {
		board = new Board(); 
		initGame();
		do {
			playerMove(currentPlayer); // update the content, currentRow and currentCol
			board.paint();
			updateGame(currentPlayer); // update currentState
			// Print message if game-over
			if (currentState == GameState.X_WON) {
				System.out.println("'X' won! Bye!");
			} else if (currentState == GameState.O_WON) {
				System.out.println("'O' won! Bye!");
			} else if (currentState == GameState.DRAW) {
				System.out.println("It's Draw! Bye!");
			}
			// Switch player
			currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT
					: Seed.CROSS;
		} while (currentState == GameState.PLAYING); // repeat until game-over
	}

	/** Initialize the game-board contents and the current states and the first player  */
	public void initGame() {
		board.init(); // clear the board contents
		currentPlayer = Seed.CROSS; // CROSS plays first
		currentState = GameState.PLAYING; // ready to play
	}
	public void playerMove(Seed theSeed) {

		boolean validInput = false; // for validating input
		if (theSeed == Seed.CROSS) {
			do {
				System.out
						.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
				int row = in.nextInt() - 1;
				int col = in.nextInt() - 1;
				if (row >= 0 && row < Board.ROWS && col >= 0
						&& col < Board.COLS
						&& board.cells[row][col].content == Seed.EMPTY) {
					board.cells[row][col].content = theSeed;
					board.currentRow = row;
					board.currentCol = col;
					validInput = true; // input okay, exit loop
				} else {
					System.out.println("This move at (" + (row + 1) + ","
							+ (col + 1) + ") is not valid. Try again...");
				}
			} while (!validInput); // repeat until input is valid

		} else {

			// computer move
			Cell fillCell = board.toFill(Seed.nextPlayer(theSeed));
			Cell winCell = board.toFill(theSeed);
			Cell bestMove = board.bestMove(theSeed);
			Cell emptyCell = board.getAnEmptyCell();
			if (winCell != null) {
				winCell.content = theSeed;
				board.currentRow = winCell.row;
				board.currentCol = winCell.col;
			}
			else if (fillCell != null) {
				fillCell.content = theSeed;
				board.currentRow = fillCell.row;
				board.currentCol = fillCell.col;

			} else if (bestMove != null) {
				bestMove.content = theSeed;
				board.currentRow = bestMove.row;
				board.currentCol = bestMove.col;
			} else if (emptyCell != null) {
				emptyCell.content = theSeed;
				board.currentRow = emptyCell.row;
				board.currentCol = emptyCell.col;
			}
			validInput = true;
			System.out.println();
		}

	}

	/** Update the currentState after the player with "theSeed" has moved */
	public void updateGame(Seed theSeed) {
		if (board.hasWon(theSeed)) { // check for win
			currentState = (theSeed == Seed.CROSS) ? GameState.X_WON
					: GameState.O_WON;
		} else if (board.isDraw()) { // check for draw
			currentState = GameState.DRAW;
		}
	}

	
	public static void main(String[] args) {
		
		System.out.println("Please not that the Player O represent the Computer");
		new GameMain(); 
	}

}
