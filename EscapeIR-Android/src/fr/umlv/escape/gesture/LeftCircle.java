package fr.umlv.escape.gesture;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Point;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.ship.Ship;

public class LeftCircle implements Gesture{
	private final int MARGIN_ERROR = 40;		// margin error for the back off gesture
	private final int MIN_NUMBER_POINT = 3;

	@Override
	public boolean isRecognized(ArrayList<Point> pointList) {
		
		//Verify if it is on left direction
		if(pointList.size()<MIN_NUMBER_POINT){
			return false;
		}
		int previousX=pointList.get(0).x;

		int i;
		for(i=0;i<pointList.size() && i<10;++i){
			int tmp=pointList.get(i).x;

			if(previousX<tmp){
				return false;
			}
			previousX=tmp;
		}
		if(i>= pointList.size()){
			return false;
		}
		
		//Verify if it is a circle

		Point tmp=pointList.get(0);
		int radius;
		int minX=tmp.x;
		int maxX=minX;
		int minY=tmp.x;
		int maxY=minY;

		//find the min and max of x and y
		for(i = 1; i< pointList.size();++i){
			tmp=pointList.get(i);
			if(minX>tmp.x){
				minX=tmp.x;
			}
			if(maxX<tmp.x){
				maxX=tmp.x;
			}
			if(minY>tmp.y){
				minY=tmp.y;
			}
			if(maxY<tmp.y){
				maxY=tmp.y;
			}
		}
		int diameterX=maxX-minX;
		int diameterY=maxY-minY;
		
		if((diameterX)>200 ||
           (diameterX)<100 ||
           (diameterY)>200 ||
           (diameterY)<100){
			return false;
		}
		
		//calcul the radius of the circle
		radius=((diameterX)+(diameterY))/4;
		
		//creating the center of the circle
		Point center=new Point(maxX-((diameterX)/2),maxY-((diameterY)/2));

		int nbBadPoint=0;	//we allow 1/10 of the total point to be outside the circle 
		for(i = 0; i< pointList.size();++i){
			tmp=pointList.get(i);
			/*Precalculing the left and right part of the circle equation
			 *  (x-Cx)�+(y-Cy)�=R�
			 */
			double equaL=Math.pow((tmp.x-center.x),2)+
				         Math.pow((tmp.y-center.y),2);
			double equaR=radius*radius;
			double margeErrorPerCent=equaR*MARGIN_ERROR/100;

			if((equaL>(equaR+margeErrorPerCent)) || (equaL<(equaR-margeErrorPerCent))){
				nbBadPoint++;
				if(pointList.size()/10<nbBadPoint){
					return false;	
				}
			}	
		}
		return true;
	}

	@Override
	public void apply(Ship ship) {
		if(ship.getCurrentWeapon().getLoadingBullet() == null){
			ship.setImage(FrontApplication.frontImage.getImage("default_ship_player_vl1"));
		}
	}

}
