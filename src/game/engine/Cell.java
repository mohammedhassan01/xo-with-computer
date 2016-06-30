package game.engine;

public class Cell {

	Seed content; // content of this cell of type Seed.
	int row, col; 

	/** Constructor to initialize this cell */
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		clear(); // clear content
	}

	/** Clear the cell content to EMPTY */
	public void clear() {
		content = Seed.EMPTY;
	}

	/** Paint itself */
	public void paint() {
		switch (content) {
		case CROSS:
			System.out.print(" X ");
			break;
		case NOUGHT:
			System.out.print(" O ");
			break;
		case EMPTY:
			System.out.print("   ");
			break;
		}
	}

}
