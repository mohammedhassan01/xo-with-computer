package game.engine;

public class Board {

	public static final int ROWS = 3;
	public static final int COLS = 3;

	
	Cell[][] cells; // a board composes rows and columns 
	int currentRow, currentCol; // the current seed's row and column

	/** Constructor to initialize the game board */
	public Board() {
		cells = new Cell[ROWS][COLS]; 
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col] = new Cell(row, col); 
			}
		}
	}

	/** Initialize the contents of the game board */
	public void init() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col].clear(); // clear the cell content
			}
		}
	}

	/** Return true if it is a draw */
	public boolean isDraw() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				if (cells[row][col].content == Seed.EMPTY) {
					return false; // an empty seed found, not a draw, exit
				}
			}
		}
		return true; // no empty cell, it's a draw
	}

	public Cell findEmptyCell(String rowOrCol, int index) {
		if (rowOrCol == "row") {
			for (int i = 0; i < ROWS; i++) {
				if (cells[index][i].content == Seed.EMPTY) {
					return cells[index][i];
				}
			}
			return null;
		} else if (rowOrCol == "col") {
			for (int i = 0; i < COLS; i++) {
				if (cells[i][index].content == Seed.EMPTY) {
					return cells[i][index];
				}
			}
			return null;
		} else if (rowOrCol == "mD") {
			for (int i = 0; i < 3; i++) {
				if (cells[i][i].content == Seed.EMPTY) {
					return cells[i][i];
				}
			}
		} else if (rowOrCol == "rD") {
			for (int i = 0; i < 3; i++) {
				if (cells[i][2 - i].content == Seed.EMPTY) {
					return cells[i][2 - i];
				}
			}
		}
		return null;
	}
	
	public Cell getValidCorner(Seed TheSeed) {
		int xCorner[] = { 0, 0, 2, 2 };
		int yCorner[] = { 0, 2, 0, 2 };
		for(int i = 0 ; i < 3 ; i ++)
		{
			int row = xCorner[i] ;
			int col = yCorner[i] ;
			if(cells[row][col].content == Seed.EMPTY)
			{
				return cells[row][col] ;
			}
		}
		return null ;
	}
	
	public Cell getAnEmptyCell()
	{
		for(int row = 0; row < ROWS ; row++)
		{
			for(int col =  0 ; col < COLS ; col++)
			{
				if(cells[row][col].content == Seed.EMPTY)
				{
					return cells[row][col] ;
				}
			}
		}
		return null ;
	}

	public Cell bestMove(Seed theSeed) {
		
		// check the center and play on the corner  
		Cell centerCell = cells[1][1];
		if (centerCell.content == Seed.EMPTY)
		{
			return centerCell ;
		}
		else if(centerCell.content == Seed.nextPlayer(theSeed)) {
			return getValidCorner(theSeed);
		}
		else if(centerCell.content == theSeed)
		{
			if(cells[centerCell.row][centerCell.col-1].content == Seed.EMPTY && cells[centerCell.row][centerCell.col+1].content == Seed.EMPTY )
			{
				return cells[centerCell.row][centerCell.col-1] ;
			}
			if(cells[centerCell.row -1][centerCell.col].content == Seed.EMPTY && cells[centerCell.row +1 ][centerCell.col].content == Seed.EMPTY )
			{
				return cells[centerCell.row -1][centerCell.col] ;
			}
		}
		return null;
	}

	public Cell diagonal(Seed theSeed) {
		
		
		// main diagonal 
		int count = 0;
		for (int i = 0; i < 3; i++) {
			if (cells[i][i].content == theSeed) {
				count++;
			} else if (cells[i][i].content == Seed.nextPlayer(theSeed)) {
				count--;
			}
		}
		if (count == 2) {
			return findEmptyCell("mD", -1);
		}
		
		// reverse diagonal 
		count = 0;
		for (int i = 0; i < 3; i++) {
			if (cells[i][2 - i].content == theSeed) {
				count++;
			} else if (cells[i][2 - i].content == Seed.nextPlayer(theSeed)) {

			}
		}
		if (count == 2) {
			return findEmptyCell("rD", -1);
		}

		return null;
	}
	
	/*
	 * find an empty cell must to fill Vs the opponent
	 * */  
	public Cell toFill(Seed theSeed) {
		for (int i = 0; i < ROWS; i++) {
			int count = 0;
			for (int j = 0; j < COLS; j++) {
				if (cells[i][j].content == theSeed) {
					count++;
				} else if (cells[i][j].content == Seed.nextPlayer(theSeed)) {
					count--;
				}
			}
			if (count == 2) {
				return findEmptyCell("row", i);
			}
		}
		for (int i = 0; i < COLS; i++) {
			int count = 0;
			for (int j = 0; j < ROWS; j++) {
				if (cells[j][i].content == theSeed) {
					count++;
				} else if (cells[j][i].content == Seed.nextPlayer(theSeed)) {
					count--;
				}
			}
			if (count == 2) {
				return findEmptyCell("col", i);
			}
		}
		return diagonal(theSeed);
	}

	/**
	 * Return true if the player with "theSeed" has won after placing at
	 * (currentRow, currentCol)
	 */
	public boolean hasWon(Seed theSeed) {
		return (cells[currentRow][0].content == theSeed // 3-in-the-row
				&& cells[currentRow][1].content == theSeed
				&& cells[currentRow][2].content == theSeed
				|| cells[0][currentCol].content == theSeed // 3-in-the-column
				&& cells[1][currentCol].content == theSeed
				&& cells[2][currentCol].content == theSeed
				|| currentRow == currentCol // diagonal
				&& cells[0][0].content == theSeed
				&& cells[1][1].content == theSeed
				&& cells[2][2].content == theSeed || currentRow + currentCol == 2 // reverse diagonal
				&& cells[0][2].content == theSeed
				&& cells[1][1].content == theSeed
				&& cells[2][0].content == theSeed);
	}

	
	public void paint() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				cells[row][col].paint(); 
				if (col < COLS - 1)
					System.out.print("|");
			}
			System.out.println();
			if (row < ROWS - 1) {
				System.out.println("-----------");
			}
		}
	}

}
