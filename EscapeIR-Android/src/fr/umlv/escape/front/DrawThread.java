package fr.umlv.escape.front;

import java.util.ArrayList;

import fr.umlv.escape.bonus.Bonus;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Bullet;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/** Draw a list of {@link Sprite} at a fixed period.
 */
public class DrawThread extends Thread{
	private SurfaceHolder holder;
	private final BattleField battleField;
	private final boolean drawUserInterface;
	private final boolean autoScroll;
	
	public DrawThread(SurfaceHolder holder, BattleField battleField, boolean drawUserInterface, boolean autoScroll) {
		this.battleField = battleField;
		this.holder = holder;
		this.drawUserInterface=drawUserInterface;
		this.autoScroll = autoScroll;
	}
	
	@Override
	public void run() {
		long begin;
		long elapsed;
		int fps=0;

		/*while(!Game.getTheGame().isStarted){
			synchronized (battleField) {
				try {
					battleField.wait();
				} catch (InterruptedException e) {
				}	
			}
		}*/
		
		while(!Thread.currentThread().isInterrupted()){
			begin = System.currentTimeMillis();
			
			Canvas canvas = holder.lockCanvas(null); // TODO Optim ?????
			if(canvas == null){
				continue;
				// TODO Gerer exception
			}
			battleField.backgoundScroller.onDrawBackground(canvas);
			synchronized (battleField.shipLock) {
				ArrayList<Ship> listShip = battleField.shipList;
				for(int i = 0; i < listShip.size(); i++){
					listShip.get(i).onDrawSprite(canvas);
				}
			}
			synchronized (battleField.bulletLock) {
				ArrayList<Bullet> listBullet = battleField.bulletList;
				for(int i = 0; i < listBullet.size(); i++){
					listBullet.get(i).onDrawSprite(canvas);
				}
			}
			synchronized (battleField.bonusLock) {
				ArrayList<Bonus> listBonus = battleField.bonusList;
				for(int i = 0; i < listBonus.size(); i++){
					listBonus.get(i).onDrawSprite(canvas);
				}
			}
			synchronized (battleField.animationLock) {
				ArrayList<SpriteAnimation> listAnimation = battleField.animationList;
				for(int i = 0; i < listAnimation.size(); i++){
					listAnimation.get(i).onDrawSprite(canvas);
				}
			}
			
			if(drawUserInterface){
				UserInterface.drawUIScoresAndLife(canvas);
				UserInterface.drawWeaponsIcons(canvas);
			}
			holder.unlockCanvasAndPost(canvas);
			if(autoScroll){
				battleField.backgoundScroller.verticalScroll(battleField.scrollingSpeed);
			}
						
			try {
				elapsed = System.currentTimeMillis() - begin;
				fps = (int)(1000/elapsed);
				if(elapsed>15) elapsed = 15;
				
				Thread.sleep(15-elapsed);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if(battleField.stats){
				UserInterface.drawPerformanceMenu(canvas, fps, battleField);
			}

		}
	}
}