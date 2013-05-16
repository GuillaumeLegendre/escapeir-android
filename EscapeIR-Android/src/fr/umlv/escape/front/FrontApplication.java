package fr.umlv.escape.front;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import fr.umlv.escape.R;
import fr.umlv.escape.gesture.GestureDetector;

 public class FrontApplication extends SurfaceView{
	private SurfaceHolder holder;
	private GestureDetector gestureDetector;
	public static FrontImages frontImage;
	public static int HEIGHT; 
	public static int WIDTH;
	final BattleField battleField;

	public FrontApplication(Context context) {
		super(context);
		this.holder = getHolder();
		callback();
		this.battleField = new BattleField(getWidth(),getHeight(),BitmapFactory.decodeResource(getResources(), R.drawable.level1));
	}

	private void callback(){    	  
		getHolder().addCallback(new Callback() {
			private Thread drawThread;
			

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				drawThread.interrupt();
			}

			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				//HEIGHT = 
				frontImage = new FrontImages(getResources());
				battleField.HEIGHT = getHeight();
				battleField.WIDTH = getWidth();
				gestureDetector = new GestureDetector();
				
				drawThread = new DrawThread(holder, battleField);
				drawThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){    				
				// TODO enelever
			}

		});
	}
	
	public BattleField getBattleField() {
		return battleField;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg1) {
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:
			gestureDetector.clear();
			Point p = new Point((int)arg1.getX(), (int)arg1.getY());
			gestureDetector.addPoint(p);
			break;
		case MotionEvent.ACTION_MOVE:
			Point p2 = new Point((int)arg1.getX(), (int)arg1.getY());
			gestureDetector.addPoint(p2);
			break;
		case MotionEvent.ACTION_UP:
			if(gestureDetector.detect()){
	//			gestureDetector.
			}
		default:
			break;
		}
		return true;
	}
}