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
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

public class GameBoard2 {


	public class GameBoard3 extends Activity {
	    
		private Context context;
		private String TAG = "GameBoard";
		private OnTouchListener listener;
		private FrameLayout layout;
		private BoardView board;
		private Canvas canvas;
		
		private LineView lines;
		private float width = 0;
		private int numCells = 8;
		private int xOffset = 0;
		private int yOffset = 0;
		
		private float cellWidth = 0;
		
		private TileView[][] tileViews = new TileView[8][8];
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        layout = new FrameLayout(this); 
	        setContentView(layout);
	      
	        context = getApplicationContext();
	        
	        this.initBoard();
	         
	        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
	        int dispWidth = display.getWidth();
	        double scale = 0.95;
	        width = (int) (dispWidth * scale);
	        xOffset = (int) (dispWidth *(1-scale) / 2);
	        int height = display.getHeight();
	        int buffer = (int) ((height-width)/2);
	        Log.d(TAG,"xOffset: "+xOffset);
	        Log.d(TAG,"width: "+width);
//	        Log.d(TAG,"height: "+height);
	        Log.d(TAG,"buffer: "+buffer);
	        int combined = (int) width + (buffer *2);
	        Log.d(TAG,"Combined: "+combined);
	        yOffset = buffer;
	        
	      
	        board = new BoardView(context);
	        lines = new LineView(context);
//	        lines.setBackgroundColor(Color.GREEN);
	        
	        layout.addView(board);
	        layout.addView(lines);
	        /*
	        layout.setOnTouchListener(listener = new OnTouchListener(){
	        	@Override
	        	public boolean onTouch(View view, MotionEvent me) {
	        		float x = me.getX();
	        		float y = me.getY();
	        		canvas.drawCircle(x, y, 20, paint);
	        		
	        		Log.d(TAG,me.toString());
	        		return false;
	        	}
	        });
	        */
	    }
	    
	    public void initBoard(){
	    	 for(int i = 0; i < tileViews.length; i++){
	         	for(int j = 0; j < tileViews.length; j++){
	         		tileViews[i][j].setTilePos(i, j);
	         	}
	         }
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
	    	
	    	private String TAG = "TileView";
	    	private Tile tile;
	    	private ShapeDrawable mDrawable;
	    	private TileViewListener listener = new TileViewListener();
	    	public TileView(Context context,int left, int top, int width){
	    		super(context);
	    	    tile = new Tile();
	            mDrawable = new ShapeDrawable(new RectShape());
	            mDrawable.getPaint().setColor(Color.GREEN);
	            mDrawable.setBounds(left, top, left + width, top + width);
	            setOnTouchListener(listener);
	        }

	        protected void onDraw(Canvas canvas) {
	            mDrawable.draw(canvas);
	        }
	        
	        public void setTilePos(int row, int col){
	        	tile.setPos(row, col);
	        }
	        
	        public Tile getTile(){
	        	return tile;
	        }
	        
	        private class TileViewListener implements OnTouchListener {
	        	
	        	@Override
				public boolean onTouch(View v, MotionEvent event) {
					Log.d(TAG,"Tile: "+tile.row+"-"+tile.col);
					return false;
				}
	        }
	    }
	    
	    @Override
	    public boolean onTouchEvent(MotionEvent me) {
	    	
	    	if (me.getAction() == MotionEvent.ACTION_DOWN) {
	    		
	    		//TODO check valid move
	    		float x = me.getX();
	    		float y = me.getY();
	    		
//	    		layout = (FrameLayout) findViewById(R.id.main_view);
	    		layout = new FrameLayout(this);		
	    		setContentView(layout);
	    		layout.addView(new BoardView(this));
	    		layout.addView(new LineView(this));
	    		layout.addView(new CircleView(this,x,y));
	    		Log.d(TAG,me.toString());
			} 
	    	
//			return listener.onTouch(board, me);
	    	return true;
	    }
	    
	    private class CircleView extends View {

			private float xCircle = 0;
			private float yCircle = 0;
			private Paint paint;
	    	
	    	public CircleView(Context context, float xCircle, float yCircle) {
				super(context);
				this.xCircle = xCircle;
				this.yCircle = yCircle;
				paint = new Paint(Paint.ANTI_ALIAS_FLAG);
				paint.setColor(Color.WHITE);
			}
			
			protected void onDraw(Canvas c) {
				super.onDraw(c);
				canvas = c;
				float circRadius = (float) (cellWidth * 0.85 / 2);
				canvas.drawCircle(xCircle, yCircle, circRadius, paint); 
			}
	    	
	    }
	    
	    public class LineView extends View {
	    	
	        public LineView(Context context) {
	            super(context);
	            
//	            float[][] coords = new float[numCells][numCells];
	            cellWidth = width / numCells;
	            Log.d(TAG,"Width of cells: "+cellWidth + " px");
	        }

	        protected void onDraw(Canvas c) {
	        	canvas = c;
	        	Paint paint = new Paint();
	        	paint.setColor(Color.BLACK);
	        	float coord = cellWidth;
	            
	        	for(int i = 0; i < numCells+1; i++){
	        		coord = cellWidth * i;
	        		//Draw Horizontal Lines
	        		canvas.drawLine(xOffset, coord+yOffset, xOffset+width, coord+yOffset, paint);
	        		//Draw Vertical Lines
	        		canvas.drawLine(xOffset+coord,yOffset, xOffset+coord, width+yOffset, paint);
//	        		canvas.drawLine(xOffset, coord, xOffset+width, coord, paint);
//	        		canvas.drawLine(xOffset+coord,0, xOffset+coord, width, paint);
	            }
	        }
	    }
	    
	    public class BoardView extends View {
	        private ShapeDrawable mDrawable;

	        public BoardView(Context context) {
	            super(context);
	            
	            int intWidth = (int) width;
	            mDrawable = new ShapeDrawable(new RectShape());
	            mDrawable.getPaint().setColor(Color.GREEN);
	            mDrawable.setBounds(xOffset, yOffset, xOffset + intWidth, yOffset + intWidth);
//	            mDrawable.setBounds(xOffset, 0, xOffset + intWidth, 0 + intWidth);
	        }

	        protected void onDraw(Canvas canvas) {
	            mDrawable.draw(canvas);
	        }
	    }
	    
	    

		
	}	
	
}
