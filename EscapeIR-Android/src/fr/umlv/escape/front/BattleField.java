package fr.umlv.escape.front;

import java.util.ArrayList;

import org.jbox2d.dynamics.Filter;

import fr.umlv.escape.bonus.Bonus;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Bullet;
import fr.umlv.escape.world.Bodys;
import fr.umlv.escape.world.EscapeWorld;
import android.graphics.Bitmap;

public class BattleField {
	private Sprite[] boundPlayer;
	final int WIDTH;
	final int HEIGHT;
	BackGroundScroller backgoundScroller;
	final ArrayList<Ship> shipList;
	final ArrayList<Bullet> bulletList;
	final ArrayList<Bonus> bonusList;
	private final BattleFieldCleaner bfCleaner;
	
	Object shipLock = new Object();
	Object bonusLock = new Object();
	Object bulletLock = new Object();
	
	/**
	 * Thread that remove all elements of the {@link Battlefield} that should not be
	 * drawn anymore at fixed rate
	 */
	private class BattleFieldCleaner implements Runnable{
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				synchronized (shipLock) {
					// Treating ships
					for(int i = 0; i < shipList.size(); i++){
						Ship tmp = shipList.get(i);
						if( tmp.getPosXCenter()< 0 		|| 
						    tmp.getPosXCenter()> WIDTH	||
						    tmp.getPosYCenter()< 0		||
						    tmp.getPosYCenter()> HEIGHT ){
							shipList.remove(i);
						}
					}
				}
				synchronized (bulletLock) {
					// Treating bullets
					for(int i = 0; i < bulletList.size(); i++){
						Bullet tmp = bulletList.get(i);
						if( tmp.getPosXCenter()< 0 		|| 
						    tmp.getPosXCenter()> WIDTH	||
						    tmp.getPosYCenter()< 0		||
						    tmp.getPosYCenter()> HEIGHT ){
							bulletList.remove(i);
						}
					}
				}
				synchronized (bonusLock) {
					// Treating bonus
					for(int i = 0; i < bonusList.size(); i++){
						Bonus tmp = bonusList.get(i);
						if( tmp.getPosXCenter()< 0 		|| 
						    tmp.getPosXCenter()> WIDTH	||
						    tmp.getPosYCenter()< 0		||
						    tmp.getPosYCenter()> HEIGHT ){
							bonusList.remove(i);
						}
					}
				} 
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}	
	}
	
	public BattleField(int width, int height, Bitmap background) {
		this.HEIGHT = height;
		this.WIDTH = width;
		this.backgoundScroller = new BackGroundScroller(width,height,background);
		this.shipList = new ArrayList<Ship>();
		this.bulletList = new ArrayList<Bullet>();
		this.bonusList = new ArrayList<Bonus>();
		this.bfCleaner = new BattleFieldCleaner();
		
		//Creating the bounds for the player
		boundPlayer[0]= new Sprite(Bodys.createBasicRectangle(0, height, width, 1,2),null);	//wall bot
		boundPlayer[1]= new Sprite(Bodys.createBasicRectangle(0, 0, width, 1,2),null);		//wall top
		boundPlayer[2]= new Sprite(Bodys.createBasicRectangle(0, 0, 1, height,2),null);		//wall left
		boundPlayer[3]= new Sprite(Bodys.createBasicRectangle(width, 0, 1, height,2),null);	//wall right
		
		Filter filter=new Filter();
		filter.categoryBits=EscapeWorld.CATEGORY_DECOR;
		filter.maskBits=EscapeWorld.CATEGORY_PLAYER;
		
		for(int i=0;i<boundPlayer.length;++i){
			boundPlayer[i].body.getFixtureList().setFilterData(filter);
			boundPlayer[i].body.getFixtureList().m_isSensor=false;
		}
	}
	
	public void launchBfCleaner(){
		this.bfCleaner.run();
	}
	
	/**
	 * Add a bullet to the battlefield. This method is ThreadSafe.
	 * @param bullet the bullet to add to the battlefield.
	 */
	public void addBullet(Bullet bullet){
		synchronized(bulletLock){
			bulletList.add(bullet);
		}
	}
	
	/**
	 * Add a ship to the battlefield. This method is ThreadSafe.
	 * @param ship the ship to add to the battlefield.
	 */
	public void addShip(Ship ship){
		synchronized(shipLock){
			shipList.add(ship);
		}
	}
	
	/**
	 * Add a bonus to the battlefield. This method is ThreadSafe.
	 * @param bonus the bonus to add to the battlefield.
	 */
	public void addBonus(Bonus bonus){
		synchronized(bonusLock){
			bonusList.add(bonus);
		}
	}
	
	/**
	 * Delete all sprite in the battlefield except the player sprite. This method is ThreadSafe.
	 */
	public void deleteAllBonus(){
		synchronized(bulletLock){
			bulletList.clear();
		}
		synchronized(shipLock){
			shipList.clear();
		}
		synchronized(bonusLock){
			bonusList.clear();
		}
	}	
}
