package fr.umlv.escape.front;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import fr.umlv.escape.ship.Ship;
import android.graphics.Bitmap;

public class BattleField {
	private Body[] boundPlayer; 
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
					//TODO delete all element mort et tuer les element en dehors de l'�cran
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
		//Creating the bounds of the screen
		/*boundPlayer[0]=Bodys.createBasicRectangle(0, height/3*2-50, width, 1,2); 	//wall 1/3 of the screen
		boundPlayer[1]=Bodys.createBasicRectangle(0, height, width, 1,2);	//wall bot
		boundPlayer[2]=Bodys.createBasicRectangle(0, 0, width, 1,2);		//wall top
		boundPlayer[3]=Bodys.createBasicRectangle(0, 0, 1, height,2);		//wall left
		boundPlayer[4]=Bodys.createBasicRectangle(width, 0, 1, height,2);	//wall right
		Filter filter=new Filter();
		filter.categoryBits=1;
		filter.maskBits=2;
		
		for(Body b : boundPlayer){
			b.getFixtureList().setFilterData(filter);
			b.getFixtureList().m_isSensor=false;
		}
		*/
		
	}
	
	public void launchBfCleaner(){
		this.bfCleaner.run();
	}
}
