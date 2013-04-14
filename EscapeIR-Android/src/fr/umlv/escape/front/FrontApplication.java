package fr.umlv.escape.front;

import org.jbox2d.dynamics.Body;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import fr.umlv.escape.R;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.world.Bodys;

/** This class manage if a {@link Drawable} object should still be drawn at the screen or not.
 * {@link Drawable} are not directly drawn by the the DrawableMonitor but through the {@link DrawThread}.
 */
public class FrontApplication extends SurfaceView{
	private SurfaceHolder holder;

	public FrontApplication(Context context) {
		super(context);
		this.holder = getHolder();
		callback();
	}

	private void callback(){    	  
		getHolder().addCallback(new Callback() {
			private Thread drawThread;
			BattleField battleField;
			int WIDTH;
			int HEIGHT;

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				drawThread.interrupt();
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				this.WIDTH = getWidth();
				this.HEIGHT = getHeight();
				battleField = new BattleField(WIDTH,HEIGHT,BitmapFactory.decodeResource(getResources(), R.drawable.level1));

				Body body = Bodys.createBasicRectangle(200, 200, 200, 200, 1);
				Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.default_ship_player); 
				battleField.shipList.add(new Ship("default_ship_player", 100, body, image));
				
				
				drawThread = new DrawThread(holder, battleField);
				drawThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){    				
				// TODO enelever
			}
		});
	}
}