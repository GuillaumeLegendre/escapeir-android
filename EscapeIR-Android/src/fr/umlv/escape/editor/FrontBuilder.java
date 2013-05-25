//package fr.umlv.escape.editor;
//
//import fr.umlv.escape.front.BattleField;
//import fr.umlv.escape.front.DrawThread;
//import fr.umlv.escape.game.Wave;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Point;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.SurfaceHolder.Callback;
//
//public class FrontBuilder extends SurfaceView{
//	private SurfaceHolder holder;
//	private Thread drawThread;
//	private BattleField battleField;
//	private Point point;
//	int HEIGHT;
//	int WIDTH;
//	
//	public FrontBuilder(Context context) {
//		super(context);
//		this.holder = getHolder();
//		battleField = new BattleField(480,800,false);
//		callback();
//	}
//	
//	private void callback(){    	  
//		getHolder().addCallback(new Callback() {
//			@Override
//			public void surfaceDestroyed(SurfaceHolder holder) {
//			}
//
//			@Override
//			public void surfaceCreated(SurfaceHolder arg0) {
//				HEIGHT=getHeight();
//				WIDTH=getWidth();
//				battleField.updateSreenSize((WIDTH/4),(HEIGHT/4),3*(WIDTH/4),3*(HEIGHT/4));				
//				drawThread = new DrawThread(holder, battleField,false,false);
//				drawThread.start();
//				battleField.launchBfCleaner();
//			}
//
//			@Override
//			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){    				
//			}
//		});
//	}
//	
//	public void buildWave(Bitmap map, Wave wave){
//		battleField.setBackground(map);
//		
//		this.invalidate();
//	}
//	
//	@Override
//	public boolean onTouchEvent(MotionEvent arg1) {
//		switch (arg1.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			point=new Point((int)arg1.getX(),(int)arg1.getY());
//			break;
//		case MotionEvent.ACTION_MOVE:
//			int scroll = point.y-(int)arg1.getY();
//			battleField.getBackgoundScroller().verticalScroll(scroll);
//			break;
//		default: break;
//		}
//		System.out.println(point);
//		point=new Point((int)arg1.getX(),(int)arg1.getY());
//		return true;
//	}
//}
