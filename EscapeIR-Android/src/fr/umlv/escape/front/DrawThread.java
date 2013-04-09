package fr.umlv.escape.front;

import android.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/** Draw a list of {@link Drawable} at a fixed period.
 */
public class DrawThread extends Thread{
	private SurfaceHolder holder;
	private final BattleField battleField;
	private final Bitmap Background; // TODO change with BackgroundScroller
	
	public DrawThread(SurfaceHolder holder, Bitmap background, BattleField battleField) {
		this.battleField = battleField;
		this.holder = holder;
		this.Background = background;
	}
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			Canvas canvas = holder.lockCanvas(null); // TODO Optim ?????
			if(canvas == null){
				// TODO Gerer exception
			}
			//TODO appeler onDraw de tous les objet de battlefield
			holder.unlockCanvasAndPost(canvas);
		}
	}
}