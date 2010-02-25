package com.reversi.main;
public class Cell {
	
	public static final int BLACK = 1;
	public static final int WHITE = 2;
	public static final int VALID = 3;
	public static final int BLANK = 0;
	
	private int row = 0; 
	private int col = 0;  
	private int fill = Cell.BLANK;
	
	public Cell(int row, int col, int fill){
		this.row = row;
		this.col = col;
		this.fill = fill;
	}
	public Cell(){
		
	}
	
	public int getRow()	{
		return row;
	}
	public int getCol()	{
		return col;
	}
	
	public int getValue(){
		return fill;
	}
	
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setValue(int fill) {
		this.fill = fill;
	}

}