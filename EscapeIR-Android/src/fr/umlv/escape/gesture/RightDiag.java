package fr.umlv.escape.gesture;

import java.util.ArrayList;
import java.util.Iterator;

import org.jbox2d.common.Vec2;

import android.graphics.Point;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.world.EscapeWorld;


public class RightDiag implements Gesture{
	private final int MARGIN_ERROR = 30;		// margin error for the back off gesture
	private final int MIN_NUMBER_POINT = 3;
	private Vec2 force;
	
	@Override
	public boolean isRecognized(ArrayList<Point> pointList) {
		Point previous;
		System.out.println("1");
		if(pointList.size()<MIN_NUMBER_POINT){
			return false;
		}
		previous=pointList.get(0);
		System.out.println("2");
		for(int i = 1; i<pointList.size();++i) {
			System.out.println("3");
			Point tmp=pointList.get(i);
			if((tmp.x<previous.x) || (tmp.y>previous.y)){
				System.out.println("4");
				return false;
			}			
			if((tmp.x-previous.x>previous.y-tmp.y+MARGIN_ERROR)){
				System.out.println("5");
				return false;
			}
			System.out.println("6");
			previous=tmp;
		}
		System.out.println("7");
		Point firstPoint=pointList.get(0);
		Point lastPoint=pointList.get(pointList.size()-1);

		force=new Vec2((lastPoint.x-firstPoint.x)/EscapeWorld.SCALE, (lastPoint.y-firstPoint.y)/EscapeWorld.SCALE);
		if((force.y/force.x)>2 || (force.y/force.x)<0.5){
			System.out.println("8");
			return false;
		}
		
		return true;
	}

	@Override
	public void apply(Ship ship) {
		if(ship.getCurrentWeapon().getLoadingBullet() == null){
			ship.move(force);
			ship.setImage(FrontApplication.frontImage.getImage("default_ship_player_rm1"));
		}
	}
}