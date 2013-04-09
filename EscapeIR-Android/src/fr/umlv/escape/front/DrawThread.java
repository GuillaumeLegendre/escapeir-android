package fr.umlv.escape.front;

import android.R;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/** Draw a list of {@link Drawable} at a fixed period.
 */
public class DrawThread extends Thread{
	private SurfaceHolder holder;
	private final BattleField battleField;

	public DrawThread(SurfaceHolder holder, BattleField battleField) {
		this.battleField = battleField;
		this.holder = holder;
	}
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			Canvas canvas = holder.lockCanvas(null); // TODO Optim ?????
			if(canvas == null){
				// TODO Gerer exception
			}
			canvas.drawBitmap(battleField.Backgound, 0, 0, null);
			//TODO appeler onDraw de tous les objet de battlefield
			holder.unlockCanvasAndPost(canvas);
		}
	}
}