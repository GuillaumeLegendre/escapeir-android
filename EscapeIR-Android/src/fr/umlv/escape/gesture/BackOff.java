package fr.umlv.escape.gesture;

import java.util.ArrayList;
import java.util.Iterator;

import org.jbox2d.common.Vec2;

import fr.umlv.escape.gesture.GestureDetector.GestureType;
import fr.umlv.escape.world.EscapeWorld;

import android.graphics.Point;

public class BackOff implements Gesture {
	private final int MARGIN_ERROR = 30;		// margin error for the back off gesture
	private final int MIN_NUMBER_POINT = 3;
	private Vec2 force;
	
	/** Detect if a point list represent a back off.
	 *  
	 * @param pointList the list of point used to detect the gesture.
	 * @return True if the gesture is recognized else false.
	 */
	@Override
	public boolean isRecognized(ArrayList<Point> pointList) {
		Iterator<Point> iterPoint=pointList.iterator();
		Point firstPoint;
		Point previous;
		
		if(!iterPoint.hasNext()){
			return false;
		}
		previous=iterPoint.next();
		firstPoint=previous;
		int numOfPoint=1;
		Point tmp;
		while(iterPoint.hasNext()){
			tmp=iterPoint.next();

			if(previous.y > tmp.y 				    ||
			   tmp.x<(firstPoint.x-(MARGIN_ERROR))	||
			   tmp.x>(firstPoint.x+MARGIN_ERROR)){
				return false;
			}
			previous=tmp;
			numOfPoint++;
		}
		if(numOfPoint > MIN_NUMBER_POINT){
			force.set(0, (previous.y-firstPoint.y)/EscapeWorld.SCALE);
			return true;
		}
		return false;
	}


	@Override
	public void apply() {
		if(playerShip.getCurrentWeapon().getLoadingBullet() == null){
			playerShip.move();
			playerSprite.setCurrentSprite(PlayerSprites.SpriteType.BASIC_IMAGE);
		}
	}
}
