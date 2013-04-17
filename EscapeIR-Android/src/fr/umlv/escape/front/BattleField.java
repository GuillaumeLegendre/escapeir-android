package fr.umlv.escape.front;

import java.util.ArrayList;

import fr.umlv.escape.Ship;
import android.graphics.Bitmap;

public class BattleField {
	final int WIDTH;
	final int HEIGHT;
	BackGroundScroller backgoundScroller;
	final ArrayList<Ship> shipList;
//	final ArrayList<Bullet> bulletList;
//	final ArrayList<Bonus> bonusList;
	private final BattleFieldCleaner bfCleaner;
	
	/**
	 * Thread that remove all elements of the {@link Battlefield} that should not be
	 * exist anymore at fixed rate
	 */
	private class BattleFieldCleaner implements Runnable{
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				// Treating ships
				for(int i = 0; i < shipList.size(); i++){
					Ship tmp = shipList.get(i);
					//TODO delete all element mort et tuer les element en dehors de l'écran
				}
				
				// Treating bullets
				/*for(int i = 0; i < bulletList.size(); i++){
					Bullet tmp = bulletList.get(i);
					//TODO gerer bullet
				}*/
				
				// Treating bonus
				/*for(int i = 0; i < bonusList.size(); i++){
					Bonus tmp = bonusList.get(i);
					//TODO gerer bonus
				}*/
				
				
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
		this.bfCleaner = new BattleFieldCleaner();
	}
	
	public void launchBfCleaner(){
		this.bfCleaner.run();
	}
}
