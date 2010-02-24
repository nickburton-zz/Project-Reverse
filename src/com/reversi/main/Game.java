package com.reversi.main;

public class Game {
	
	private Cell board[][] = new Cell[8][8];
	private String player1;
	private String player2;
	private int p1Score;
	private int p2Score;
	private boolean blacksTurn;
	/**
	 * Constructor to initialise a game with player names and the default board setup.
	 * @param p1 player 1 name.
	 * @param p2 player 2 name.
	 */
	public Game(String p1, String p2)
	{
		this.setPlayer1(p1);
		this.setPlayer2(p2);
		this.setP1Score(2);
		this.setP2Score(2);
		this.setBlacksTurn(true);
		for(int x=0; x<8; x++)
		{
			for (int y=0; y<8; y++)
				this.board[x][y] = new Cell(x,y,Cell.BLANK);
		}
		//New Game default starting grid
		this.board[3][3].setValue(Cell.WHITE);
		this.board[3][4].setValue(Cell.BLACK);
		this.board[4][3].setValue(Cell.BLACK);
		this.board[4][4].setValue(Cell.WHITE);
	}
	
	public Cell[] getInitialCells(){
		return new Cell[2];
	}
	/**
	 * Save all game variable into the DB. Only 1 game can be saved at a time.
	 * @param none
	 * @return void
	 */
	public void saveGame()
	{
		//TO DO
	}
	/**
	 * Load the game which was saved to the DataBase.
	 * @param none
	 * @return True if successful. False if there was an exception.
	 */
	public boolean loadSaveGame()
	{
		return true;
	}
	/**
	 * Checks if the cells above the selected cell will result in a valid move.
	 * @param x x-coordinate of the cell
	 * @param y y-coordinate of the cell
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkUpValid(int x, int y)
	{
		boolean valid = false;
		if(this.board[x][y].getValue()==Cell.BLANK && x>=2)
		{
			if(this.blacksTurn==true && this.board[x-1][y].getValue()==Cell.WHITE)
			{
				for(int i=x-2;i>0;i--)
				{
					if(this.board[i][y].getValue()==Cell.BLACK)
					{
						valid=true;
						i=-1;
					}
					else if(this.board[i][y].getValue()==Cell.BLANK)
					{
						valid=false;
						i=-1;
					}
				}
			}
			else if(this.blacksTurn==false && this.board[x-1][y].getValue()==Cell.BLACK)
			{
				for(int i=x-2;i>0;i--)
				{
					if(this.board[i][y].getValue()==Cell.WHITE)
					{
						valid=true;
						i=-1;
					}
					else if(this.board[i][y].getValue()==Cell.BLANK)
					{
						valid=false;
						i=-1;
					}
				}
			}
		}
		return valid;	
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * upwards direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveUp(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cells below the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkDownValid(int x, int y)
	{
		boolean valid = false;
		if(this.board[x][y].getValue()==Cell.BLANK && x<=5)
		{
			if(this.blacksTurn==true && this.board[x+1][y].getValue()==Cell.WHITE)
			{
				for(int i=x+2;i<8;i++)
				{
					if(this.board[i][y].getValue()==Cell.BLACK)
					{
						valid=true;
						i=9;
					}
					else if(this.board[i][y].getValue()==Cell.BLANK)
					{
						valid=false;
						i=9;
					}
				}
			}
			else if(this.blacksTurn==false && this.board[x+1][y].getValue()==Cell.BLACK)
			{
				for(int i=x+2;i<8;i++)
				{
					if(this.board[i][y].getValue()==Cell.WHITE)
					{
						valid=true;
						i=9;
					}
					else if(this.board[i][y].getValue()==Cell.BLANK)
					{
						valid=false;
						i=9;
					}
				}
			}
		}
		return valid;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * downwards direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveDown(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cells to the left of the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkLeftValid(int x, int y)
	{
		boolean valid = false;
		if(this.board[x][y].getValue()==Cell.BLANK && y>=2)
		{
			if(this.blacksTurn==true && this.board[x][y-1].getValue()==Cell.WHITE)
			{
				for(int i=y-2;i>0;i--)
				{
					if(this.board[x][i].getValue()==Cell.BLACK)
					{
						valid=true;
						i=-1;
					}
					else if(this.board[x][i].getValue()==Cell.BLANK)
					{
						valid=false;
						i=-1;
					}
				}
			}
			else if(this.blacksTurn==false && this.board[x][y-1].getValue()==Cell.BLACK)
			{
				for(int i=y-2;i>0;i--)
				{
					if(this.board[x][i].getValue()==Cell.WHITE)
					{
						valid=true;
						i=-1;
					}
					else if(this.board[x][i].getValue()==Cell.BLANK)
					{
						valid=false;
						i=-1;
					}
				}
			}
		}
		return valid;	
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * left direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveLeft(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cell to the right of the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkRightValid(int x, int y)
	{
		return false;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * right direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveRight(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cell diagonally left+down of the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkLeftDownValid(int x, int y)
	{
		return false;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal left+down direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveLeftDown(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cell diagonally right+down of the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkRightDownValid(int x, int y)
	{
		return false;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal right+down direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveRightDown(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cell diagonally left+up of the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkLeftUpValid(int x, int y)
	{
		return false;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal left+up direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveLeftUp(int x, int y)
	{
		//TO DO
	}
	/**
	 * Checks if the cell diagonally right+up of the selected cell will result in a valid move.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 * @return true if there is a valid move found. False if not.
	 */
	private boolean checkRightUpValid(int x, int y)
	{
		return false;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal right+up direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void moveRightUp(int x, int y)
	{
		//TO DO
	}
	/**
	 * This function will check the cell the player wishes to select as a move.
	 * It will check if it is valid, then once validated it will go through each
	 * adjacent cell and find out if there is a valid move in that direction and 
	 * and perform it. Then it will determine if the next player has a move, if so then
	 * make it his/her move. If not the current player gets another turn. 
	 * Also will update the current game score and check if the game is over or not.
	 * 
	 * @param x x-coordinate of the selected cell.
	 * @param y y-coordinate of the selected cell.
	 */
	public void move(int x, int y)
	{
		//TO DO
	}
	/**
	 * This function should be run after each move. This will highlight to the current
	 * player which cells are the only options to select for a valid move.
	 */
	public boolean checkCells(int row, int col)
	{
		for(int x=0; x<8; x++)
		{
			for (int y=0; y<8; y++)
			{
				if ( this.checkUpValid(x,y)==true ||
				this.checkDownValid(x,y)==true ||
				this.checkLeftValid(x,y)==true ||
				this.checkRightValid(x,y)==true ||
				this.checkLeftDownValid(x,y)==true ||
				this.checkRightDownValid(x,y)==true ||
				this.checkLeftUpValid(x,y)==true ||
				this.checkRightUpValid(x,y)==true){
					this.board[x][y].setValue(Cell.VALID);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This will print to the console a graphical representation of the current game.
	 */
	public void display(){
		System.out.println("	  0   1   2   3   4   5   6   7  ");
		System.out.println("	+ - + - + - + - + - + - + - + - +");
		for(int x=0;x<8;x++)
		{
			System.out.print(x + "       |");
			for(int y=0;y<8;y++)
			{
				switch(this.board[x][y].getValue()){
					case Cell.BLACK: 
						System.out.print(" x |");
						break;
					case Cell.WHITE: 
						System.out.print(" o |");
						break;
					case Cell.BLANK: 
						System.out.print("   |");
						break;
					case Cell.VALID: 
						System.out.print(" . |");
						break;
				}
			}
			System.out.print("\n");
			System.out.println("	+ - + - + - + - + - + - + - + - +");
		}
	}
	/**
	 * Set the name of player 1.
	 * @param player1
	 */
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}
	/**
	 * Get the name of player 1.
	 * @return A string with the name.
	 */
	public String getPlayer1() {
		return player1;
	}
	/**
	 * Set the name of player 12. 
	 * @param player2
	 */
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	/**
	 * Get the name of player 2.
	 * @return A string with the name.
	 */
	public String getPlayer2() {
		return player2;
	}
	/**
	 * Set the score of player 1.
	 * @param p1 the int value of the score.
	 */
	public void setP1Score(int p1) {
		this.p1Score = p1;
	}
	/**
	 * Get the score of player 1.
	 * @return An int with the score.
	 */
	public int getP1Score() {
		return p1Score;
	}
	/**
	 * Set the score of player 2.
	 * @param p1 the int value of the score.
	 */
	public void setP2Score(int p2) {
		this.p2Score = p2;
	}
	/**
	 * Get the score of player 2.
	 * @return An int with the score.
	 */
	public int getP2Score() {
		return p2Score;
	}
	/**
	 * Set the variable to distinguish which players turn it is.
	 * @param blacksTurn True = player 1 (black), False = player 2 (white)
	 */
	public void setBlacksTurn(boolean blacksTurn) {
		this.blacksTurn = blacksTurn;
	}
	/**
	 * Find out who's turn it is.
	 * @return True = player 1 turn (black), False = player 2 turn (white)
	 */
	public boolean isBlacksTurn() {
		return blacksTurn;
	}
}
