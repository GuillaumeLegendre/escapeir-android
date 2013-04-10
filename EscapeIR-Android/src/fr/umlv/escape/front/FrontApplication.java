package fr.umlv.escape.front;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import fr.umlv.escape.R;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.world.Bodys;
import fr.umlv.escape.world.EscapeWorld;

/** This class manage if a {@link Drawable} object should still be drawn at the screen or not.
 * {@link Drawable} are not directly drawn by the the DrawableMonitor but through the {@link DrawThread}.
 */
public class FrontApplication extends SurfaceView{
	private SurfaceHolder holder;
	BattleField battleField;

	public FrontApplication(Context context) {
		super(context);
		this.battleField = new BattleField(BitmapFactory.decodeResource(getResources(), R.drawable.level1));
//		Body body = Bodys.createBasicRectangle(200, 200, 200, 200, 1);
//		Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.default_ship_player); 
//		battleField.shipList.add(new Ship("default_ship_player", 100, body, image));
		this.holder = getHolder();
		callback(battleField);
	}

	private void callback(final BattleField battleField){    	  
		getHolder().addCallback(new Callback() {
			private Thread drawThread;

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				drawThread.interrupt();
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
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