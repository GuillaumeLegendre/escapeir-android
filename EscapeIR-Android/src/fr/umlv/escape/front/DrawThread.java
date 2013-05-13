package fr.umlv.escape.front;

import java.util.ArrayList;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Bullet;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/** Draw a list of {@link Sprite} at a fixed period.
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
		long begin;
		long elapsed;
		
		while(!Thread.currentThread().isInterrupted()){
			begin = System.currentTimeMillis();
			
			Canvas canvas = holder.lockCanvas(null); // TODO Optim ?????
			if(canvas == null){
				// TODO Gerer exception
			}
			battleField.backgoundScroller.onDrawBackground(canvas);
			synchronized (battleField.lock) {
				ArrayList<Ship> listShip = battleField.shipList;
				for(int i = 0; i < listShip.size(); i++){
					listShip.get(i).onDrawSprite(canvas);
				}
				
				ArrayList<Bullet> listBullet = battleField.bulletList;
				for(int i = 0; i < listBullet.size(); i++){
					listBullet.get(i).onDrawSprite(canvas);
				}
				
				ArrayList<Bullet> listBonus = battleField.bonusList;
				for(int i = 0; i < listBonus.size(); i++){
					listBonus.get(i).onDrawSprite(canvas);
				}
			}
			holder.unlockCanvasAndPost(canvas);
			battleField.backgoundScroller.verticalScroll();
			
			try {
				elapsed = System.currentTimeMillis() - begin;
				if(elapsed>50) elapsed = 50;
				
				Thread.sleep(50-elapsed);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}