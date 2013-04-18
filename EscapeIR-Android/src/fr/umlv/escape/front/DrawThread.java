package fr.umlv.escape.front;

import java.util.ArrayList;
import fr.umlv.escape.ship.Ship;
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
			battleField.backgoundScroller.onDrawBackground(canvas);
			ArrayList<Ship> listShip = battleField.shipList;
			for(int i = 0; i < listShip.size(); i++){
				listShip.get(i).onDrawSprite(canvas);
			}
			holder.unlockCanvasAndPost(canvas);
			battleField.backgoundScroller.verticalScroll();
			try {
				Thread.sleep(50); //TODO D�duire le temps pass� dans la boucle;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}