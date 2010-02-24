package com.reversi.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;


public class GameBoard extends Activity {
    
	static final String TAG = "GameBoard";
	private Context context;
	private FrameLayout layout;
	private Canvas canvas;
	
	/**
	 * Width and height of board.
	 */
	private int numCells = 8; 
	private float width = 0;
	private int xOffset = 0;
	private int yOffset = 0;
	
	private float cellWidth = 0;
	
	private TileView[][] tileView = new TileView[8][8];
	
	private Game game;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new FrameLayout(this); 
        setContentView(layout);
        context = getApplicationContext();
        this.calcScreenProperties();
        layout = this.initBoard();
        setContentView(layout);
        this.setFirstCells();
    }
    
    public void setFirstCells(){
    	game = new Game("Scat","Tina");
        Cell[] cells = game.getInitialCells();
        int row = 0;
        int col = 0;
        for(int i = 0; i < cells.length; i++){
        	row = cells[i].getRow();
        	col = cells[i].getCol();
        	tileView[row][col].setCellPos(row, col);
        }
    }
    
    
    public void calcScreenProperties(){
    	Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int dispWidth = display.getWidth();
        double scale = 0.95;
        width = (int) (dispWidth * scale);
        xOffset = (int) (dispWidth *(1-scale) / 2);
        int height = display.getHeight();
        int buffer = (int) ((height-width)/2);
        Log.d(TAG,"xOffset: "+xOffset);
        Log.d(TAG,"width: "+width);
//        Log.d(TAG,"height: "+height);
        Log.d(TAG,"buffer: "+buffer);
        int combined = (int) width + (buffer *2);
        Log.d(TAG,"Combined: "+combined);
        yOffset = buffer;
        cellWidth = width / numCells;
        Log.d(TAG,"cellWidth:"+cellWidth);
    }
    
    public FrameLayout initBoard(){
    	FrameLayout frame = new FrameLayout(context);
    	int left = (int) xOffset;
    	int top = (int) yOffset;
    	int color = 0;
    	for(int i = 0; i < tileView.length; i++){
    		for(int j = 0; j < tileView.length; j++){
    			if(((i+j)%2)==0){
        			color = Color.GRAY;
        		} else {
        			color = Color.DKGRAY;
        		}
    			tileView[i][j] = new TileView(left,top,color);
    			tileView[i][j].setCellPos(i, j);
    			frame.addView(tileView[i][j]);
    			tileView[i][j].invalidate();
    			left += cellWidth;
    		}
    		top+=cellWidth;
    		left = xOffset;
    	}
    	return frame;
    }
    
    private class Tile {
    	private int row = 0;
    	private int col = 0;
    	
    	public void setPos(int row, int col){
    		this.row = row;
    		this.col = col;
    	}
    }
    
    public class TileView extends View {
    	
    	private String TAG = "GameBoard";
    	private Tile tile;
    	private ShapeDrawable mDrawable;
    	private int left;
    	private int top;
    	private int right;
    	private int bottom;
    	private int color;
    	
    	public TileView(int left, int top, int color){
    		super(context);
    	    tile = new Tile();
            mDrawable = new ShapeDrawable(new RectShape());
            mDrawable.getPaint().setColor(color);
            
            this.top = top;
            this.left = left;
            right = left + (int) cellWidth;
            bottom = top + (int) cellWidth;
            mDrawable.setBounds(left, top, right, bottom);
            Log.d(TAG,"Left:"+this.left+",Top:"+this.top+",Right:"+right+",Bottom:"+bottom);
//            setOnTouchListener(new TileViewListener());
        }

    	@Override
        protected void onDraw(Canvas canvas) {
            mDrawable.draw(canvas);
        }
        
        public void setCellPos(int row, int col){
        	tile.setPos(row, col);
        	CircleView circle = new CircleView(row,col);
        	layout.addView(circle);
        }
        
        public Tile getCell(){
        	return tile;
        }
    }
    
    public void checkBounds(float xCoord, float yCoord){
    	
    	int x = (int) xCoord;
    	int y = (int) yCoord;
    	int left = 0;
    	int right = 0;
    	int top = 0;
    	int bottom = 0;
    	boolean found = false;
    	
    	for(int i = 0; i < tileView.length; i++){
    		for(int j = 0; j < tileView.length; j++){
    			if(!found){
    				left = tileView[i][j].left; 
        			right = tileView[i][j].right;
        			top = tileView[i][j].top;
        			bottom = tileView[i][j].bottom;
        			
        			if(x >= left && x <= right){
        				if(y >= top && y <= bottom){
        					int drawX = left + (int) cellWidth/2;
        					int drawY = top + (int) cellWidth/2;
        					CircleView circle = new CircleView(drawX,drawY);
//        					CircleView circle = new CircleView(left,top);
                			Log.d(TAG,"Left: "+left+", Top:"+top);
        					Log.d(TAG,"DrawX: "+drawX+", DrawY:"+drawY);
        					Log.d(TAG,"Row: "+tileView[i][j].getCell().row
        							+", Col"+tileView[i][j].getCell().col);
        					layout.addView(circle);
        					found = true;
        				}
        			}
    			}
    		}
    	}
    	if(!found){
    		Toast.makeText(this, "Clicked outside of Board!",
            		Toast.LENGTH_SHORT).show();
    	}
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me) {
    	
    	int action = me.getAction();
    	switch(action){
    	case MotionEvent.ACTION_DOWN:
    		float x = me.getX();
        	float y = me.getY();
        	this.checkBounds(x, y-cellWidth);
        	Log.d(TAG,"Received onTouchEvent()");
    		break;
    	}
    	return false;
    }
    
    private class CircleView extends View {

		private float xCircle = 0;
		private float yCircle = 0;
		private Paint paint;
    	
    	public CircleView(float xCircle, float yCircle) {
			super(context);
			this.xCircle = xCircle;
			this.yCircle = yCircle;
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.WHITE);
		}
    	
    	@Override
		protected void onDraw(Canvas c) {
			super.onDraw(c);
			canvas = c;
			float circRadius = (float) (cellWidth * 0.76 / 2);
			canvas.drawCircle(xCircle, yCircle, circRadius, paint); 
		}	
    }    
}


