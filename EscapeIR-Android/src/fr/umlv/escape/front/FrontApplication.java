package fr.umlv.escape.front;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import fr.umlv.escape.R;

/** This class manage if a {@link Drawable} object should still be drawn at the screen or not.
 * {@link Drawable} are not directly drawn by the the DrawableMonitor but through the {@link DrawThread}.
 */
public class FrontApplication extends SurfaceView{
	private SurfaceHolder holder;
	BattleField battleField;

	public FrontApplication(Context context) {
		super(context);
		this.battleField = new BattleField(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
		callback(battleField);
	}

	private void callback(final BattleField battleField){    	  
		getHolder().addCallback(new Callback() {
			private Thread drawThread;

			private void backgroundDrawing(){

				Canvas canvas = holder.lockCanvas(null);
				if(canvas == null){
					// TODO Gerer exception
				}
				//canvas.drawColor(Color.BLACK); TODO Draw background
				holder.unlockCanvasAndPost(canvas);
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				drawThread.interrupt();
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				backgroundDrawing(); //TODO voir pour enlever
				drawThread = new DrawThread(getHolder(), battleField);
				drawThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){    				
				// TODO enelever
			}
		});
	}
}