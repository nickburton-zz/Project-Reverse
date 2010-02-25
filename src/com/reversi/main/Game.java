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
		for(int row=0; row<8; row++)
		{
			for (int col=0; col<8; col++)
				this.board[row][col] = new Cell(row,col,Cell.BLANK);
		}
		//New Game default starting grid
		this.board[3][3].setValue(Cell.WHITE);
		this.board[3][4].setValue(Cell.BLACK);
		this.board[4][3].setValue(Cell.BLACK);
		this.board[4][4].setValue(Cell.WHITE);
	}
	/**
	 * Function to be called after a new game is started to show the starting setup.
	 * @return Cell array.
	 */
	public Cell[] getInitialCells()
	{
		Cell[] start = new Cell[4];
		start[0] = this.board[3][3];
		start[1] = this.board[3][4];
		start[2] = this.board[4][3];
		start[3] = this.board[4][4];
		return start;
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
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkUp(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID)  && row>1)
		{
			if(this.board[col][row-1].getValue()==this.enemyColour())
			{
				count++;
				for(int i=row-2;i>-1;i--)
				{
					if(this.board[col][i].getValue()==this.playerColour())
					{
						valid = true; // valid move
						i=-2; 		//exit loop
					}
					else if(this.board[col][i].getValue()==Cell.BLANK)
					{
						valid = false;
						i=-2; 		//exit loop
					}
					else if(this.board[col][i].getValue()==Cell.VALID)
					{
						valid = false;
						i=-2; 		//exit loop
					}
					else if(this.board[col][i].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * upwards direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the selected cell.
	 * @param row y-coordinate (row) of the selected cell.
	 * @return an array of Cells which have been changed.
	 */
	private Cell[] moveUp(int col, int row)
	{
		Cell[] change = new Cell[this.checkUp(col, row)];
		int count = 0;
		if(this.checkUp(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move above the cell
		{
			for(int i=row-1;i>-1;i--) //for each cell above selected
			{
				this.board[col][i].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[col][i-1].getValue()==this.playerColour()) //until you hit the boundary
					i=-2; //exit loop
				else
				{
					change[count] = this.board[col][i];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cells below the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkDown(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID)  && row<6)
		{
			if(this.board[col][row+1].getValue()==this.enemyColour())
			{
				count++;
				for(int i=row+2;i<8;i++)
				{
					if(this.board[col][i].getValue()==this.playerColour())
					{
						valid = true;
						i=9; 		//exit loop
					}
					else if(this.board[col][i].getValue()==Cell.BLANK)
					{
						valid = false;
						i=9; 		//exit loop
					}
					else if(this.board[col][i].getValue()==Cell.VALID)
					{
						valid = false;
						i=9; 		//exit loop
					}
					else if(this.board[col][i].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * downwards direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveDown(int col, int row)
	{
		Cell[] change = new Cell[this.checkDown(col, row)];
		int count = 0;
		if(this.checkDown(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move below the cell
		{
			for(int i=row+1;i<8;i++) //for each cell above selected
			{
				this.board[col][i].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[col][i+1].getValue()==this.playerColour()) //until you hit the boundary
					i=9; //exit loop
				else
				{
					change[count] = this.board[col][i];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cells to the left of the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkLeft(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID) && col>1)
		{
			if(this.board[col-1][row].getValue()==this.enemyColour())
			{
				count++;
				for(int i=col-2;i>-1;i--)
				{
					if(this.board[i][row].getValue()==this.playerColour())
					{
						valid = true;
						i=-2; 		//exit loop
					}
					else if(this.board[i][row].getValue()==Cell.BLANK)
					{
						valid = false;
						i=-2; 		//exit loop
					}
					else if(this.board[i][row].getValue()==Cell.VALID)
					{
						valid = false;
						i=-2; 		//exit loop
					}
					else if(this.board[i][row].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;	
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * left direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveLeft(int col, int row)
	{
		Cell[] change = new Cell[this.checkLeft(col, row)];
		int count = 0;
		if(this.checkLeft(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move left of the cell
		{
			for(int i=col-1;i>-1;i--) //for each cell above selected
			{
				this.board[i][row].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[i-1][row].getValue()==this.playerColour()) //until you hit the boundary
					i=-2; //exit loop
				else
				{
					change[count] = this.board[i][row];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cell to the right of the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkRight(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID) && col<6)
		{
			if(this.board[col+1][row].getValue()==this.enemyColour())
			{
				count++;
				for(int i=col+2;i<8;i++)
				{
					if(this.board[i][row].getValue()==this.playerColour())
					{
						valid = true;
						i=9; 		//exit loop
					}
					else if(this.board[i][row].getValue()==Cell.BLANK)
					{
						valid = false;
						i=9; 		//exit loop
					}
					else if(this.board[i][row].getValue()==Cell.VALID)
					{
						valid = false;
						i=9; 		//exit loop
					}
					else if(this.board[i][row].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * right direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveRight(int col, int row)
	{
		Cell[] change = new Cell[this.checkRight(col, row)];
		int count = 0;
		if(this.checkRight(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move left of the cell
		{
			for(int i=col+1;i<8;i++) //for each cell above selected
			{
				this.board[i][row].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[i+1][row].getValue()==this.playerColour()) //until you hit the boundary
					i=9; //exit loop
				else
				{
					change[count] = this.board[i][row];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cell diagonally left+down of the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkLeftDown(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID) && (col>1 && row<6))
		{
			if(this.board[col-1][row+1].getValue()==this.enemyColour())
			{
				count++;
				for(int i=col-2,j=row+2;i>-1 && j<8;i--,j++)
				{
					if(this.board[i][j].getValue()==this.playerColour())
					{
						valid = true;
						i=-2; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.BLANK)
					{
						valid = false;
						i=-2; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.VALID)
					{
						valid = false;
						i=-2; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;	
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal left+down direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveLeftDown(int col, int row)
	{
		Cell[] change = new Cell[this.checkLeftDown(col, row)];
		int count = 0;
		if(this.checkLeftDown(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move left of the cell
		{
			for(int i=col-1,j=row+1;i>-1 && j<8;i--,j++) //for each cell above selected
			{
				this.board[i][j].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[i-1][j+1].getValue()==this.playerColour()) //until you hit the boundary
				{
					i=-2; 		//exit loop
					j=9;		//exit loop
				}
				else
				{
					change[count] = this.board[i][j];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cell diagonally right+down of the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkRightDown(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID) && (col<6 && row<6))
		{
			if(this.board[col+1][row+1].getValue()==this.enemyColour())
			{
				count++;
				for(int i=col+2,j=row+2;i<8 && j<8;i++,j++)
				{
					if(this.board[i][j].getValue()==this.playerColour())
					{
						valid = true;
						i=9; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.BLANK)
					{
						valid = false;
						i=9; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.VALID)
					{
						valid = false;
						i=9; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal right+down direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveRightDown(int col, int row)
	{
		Cell[] change = new Cell[this.checkRightDown(col, row)];
		int count = 0;
		if(this.checkRightDown(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move left of the cell
		{
			for(int i=col+1,j=row+1;i<8 && j<8;i++,j++) //for each cell above selected
			{
				this.board[i][j].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[i+1][j+1].getValue()==this.playerColour()) //until you hit the boundary
				{
					i=9; 		//exit loop
					j=9;		//exit loop
				}
				else
				{
					change[count] = this.board[i][j];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cell diagonally left+up of the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkLeftUp(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID) && (col>1 && row>1))
		{
			if(this.board[col-1][row-1].getValue()==this.enemyColour())
			{
				count++;
				for(int i=col-2,j=row-2;i>-1 && j>-1;i--,j--)
				{
					if(this.board[i][j].getValue()==this.playerColour())
					{
						valid = true;
						i=-2; 		//exit loop
						j=-2;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.BLANK)
					{
						valid = false;
						i=-2; 		//exit loop
						j=-2;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.VALID)
					{
						valid = false;
						i=-2; 		//exit loop
						j=-2;		//exit loop
					}
					else if(this.board[i][j].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal left+up direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveLeftUp(int col, int row)
	{
		Cell[] change = new Cell[this.checkLeftUp(col, row)];
		int count = 0;
		if(this.checkLeftUp(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move left of the cell
		{
			for(int i=col-1,j=row-1;i>-1 && j>-1;i--,j--) //for each cell above selected
			{
				this.board[i][j].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[i-1][j-1].getValue()==this.playerColour()) //until you hit the boundary
				{
					i=-2; 		//exit loop
					j=-2;		//exit loop
				}
				else
				{
					change[count] = this.board[i][j];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * Checks if the cell diagonally right+up of the selected cell will result in a valid move.
	 * @param col x-coordinate (column) of the cell
	 * @param row y-coordinate (row) of the cell
	 * @return an integer of the number of cells that need to be changed.
	 */
	private int checkRightUp(int col, int row)
	{
		int count=0;
		boolean valid = false;
		if((this.board[col][row].getValue()==Cell.BLANK || this.board[col][row].getValue()==Cell.VALID) && (col<6 && row>1))
		{
			if(this.board[col+1][row-1].getValue()==this.enemyColour())
			{
				count++;
				for(int i=col+2,j=row-2;i<8 && j>-1;i++,j--)
				{
					if(this.board[i][j].getValue()==this.playerColour())
					{
						valid = true;
						i=9; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.BLANK)
					{
						valid = false;
						i=9; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==Cell.VALID)
					{
						valid = false;
						i=9; 		//exit loop
						j=9;		//exit loop
					}
					else if(this.board[i][j].getValue()==this.enemyColour())
						count++;	//add to the count of cells that need changing
				}
			}
		}
		if(valid==true)
			return count;
		else
			return 0;
	}
	/**
	 * This function will change the cell values corresponding to a valid move in the
	 * diagonal right+up direction. When moved over to android most likely will output the cell
	 * co-ords that need to be changed to the GUI as well as changing the representation
	 * in the data structure.
	 * @param col x-coordinate (column) of the cell.
	 * @param row y-coordinate (row) of the cell.
	 * @return an array of Cells which have been changed.
	 */
	public Cell[] moveRightUp(int col, int row)
	{
		Cell[] change = new Cell[this.checkRightUp(col, row)];
		int count = 0;
		if(this.checkRightUp(col, row)!=0 && this.board[col][row].getValue()==Cell.VALID) //if there is a valid move left of the cell
		{
			for(int i=col+1,j=row-1;i<8 && j>-1;i++,j--) //for each cell above selected
			{
				this.board[i][j].setValue(this.playerColour()); //change the value to the players colour
				if(this.board[i+1][j-1].getValue()==this.playerColour()) //until you hit the boundary
				{
					i=9; 		//exit loop
					j=9;		//exit loop
				}
				else
				{
					change[count] = this.board[i][j];
					count++;
				}
			}
		}
		return change;
	}
	/**
	 * This function will determine the current players colour.
	 * @return an integer.
	 */
	public int playerColour()
	{
		if(this.blacksTurn==true)
			return Cell.BLACK;
		else
			return Cell.WHITE;
	}
	/**
	 * This function will determine the opponents colour.
	 * @return an integer 
	 */
	public int enemyColour()
	{
		if(this.blacksTurn==true)
			return Cell.WHITE;
		else
			return Cell.BLACK;
	}
	/**
	 * A function to determine if the game is still in progress.
	 * @return true or false.
	 */
	public boolean gameOver()
	{
		if((this.p1Score+this.p2Score==64)|| this.p1Score==0 || this.p2Score==0)
			return true;
		else
			return false;
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
	public void move(int col, int row)
	{
		int totalCellsChanged = 
		this.moveUp(col, row).length +
		this.moveDown(col, row).length +
		this.moveLeft(col, row).length +
		this.moveRight(col, row).length +
		this.moveLeftDown(col, row).length +
		this.moveRightDown(col, row).length +
		this.moveLeftUp(col, row).length +
		this.moveRightUp(col, row).length;
		
		if (totalCellsChanged > 0){
			this.board[col][row].setValue(this.playerColour());
			this.clearValidCells();
			this.blacksTurn=!this.blacksTurn; 	//change players turn
			if(this.checkCells()==false && this.gameOver()==false){ //if there are no moves and the game is not over
				this.blacksTurn=!this.blacksTurn; //go back to the other player
				this.checkCells(); //mark the cells
			}
		}
	}
	/**
	 * This function should be run after each move. This will highlight to the current
	 * player which cells are the only options to select for a valid move.
	 * Also Calculates score, each time function is run
	 * @return true if there is a cell marked as a valid move.
	 */
	public boolean checkCells()
	{
		boolean anyValidCells = false;
		this.p1Score = 0;
		this.p2Score = 0;
		for(int col=0; col<8; col++)
		{
			for (int row=0; row<8; row++)
			{
				if (	this.checkUp(col,row)>0 ||
						this.checkDown(col,row)>0 ||
						this.checkLeft(col,row)>0 ||
						this.checkRight(col,row)>0 ||
						this.checkLeftDown(col,row)>0 ||
						this.checkRightDown(col,row)>0 ||
						this.checkLeftUp(col,row)>0 ||
						this.checkRightUp(col,row)>0){
							this.board[col][row].setValue(Cell.VALID);
								anyValidCells = true;
							}
				if(this.board[col][row].getValue()==Cell.BLACK)
					this.p1Score++;
				if(this.board[col][row].getValue()==Cell.WHITE)
					this.p2Score++;
			}
		}
		return anyValidCells;
	}
	/**
	 * This will clear all cells marked as valid. 
	 */
	public void clearValidCells()
	{
		for(int x=0; x<8; x++)
		{
			for (int y=0; y<8; y++)
			{
				if(this.board[x][y].getValue()==Cell.VALID)
					this.board[x][y].setValue(Cell.BLANK);
			}
		}
	}
	/**
	 * This will print to the console a graphical representation of the current game.
	 */
	public void display(){
		System.out.println("	  0   1   2   3   4   5   6   7  ");
		System.out.println("	+ - + - + - + - + - + - + - + - +");
		for(int y=0;y<8;y++)
		{
			System.out.print(y + "       |");
			for(int x=0;x<8;x++)
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