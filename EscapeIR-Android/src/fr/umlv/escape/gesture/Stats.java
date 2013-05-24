package fr.umlv.escape.gesture;

import java.util.ArrayList;

import android.graphics.Point;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.ship.Ship;


public class Stats implements Gesture {
	private final int MARGIN_ERROR = 30;		// margin error for the back off gesture
	private final int MIN_NUMBER_POINT = 5;

	@Override
	public boolean isRecognized(ArrayList<Point> pointList) {
		Point previous;
		int stateOfDetection=0;
		
		previous=pointList.get(0);
		Point firsPoint=previous;
		int numOfPoint=1;
		int badPoint=0;
		Point tmp;
	
		for(int i=1;i<pointList.size();++i){
			tmp=pointList.get(i);
			switch (stateOfDetection){
			case 0:  // detect straight line to the right
				if(numOfPoint<5){
					if(tmp.y>firsPoint.y + MARGIN_ERROR){
						return false;
					}
				}
				if((tmp.x<previous.x)||(tmp.y<firsPoint.y - MARGIN_ERROR)){
					badPoint++;
					if(badPoint>10){
						return false;
					}
				}
				if(tmp.y>firsPoint.y + MARGIN_ERROR){
					stateOfDetection++;
					firsPoint=previous;
					numOfPoint=0;
				}
				break;
			case 1:  // detect back off
				if(numOfPoint<5){
					if(tmp.x<(firsPoint.x-MARGIN_ERROR)){
						return false;
					}
				}
				if(tmp.y<previous.y || tmp.x>(firsPoint.x+MARGIN_ERROR)){
					badPoint++;
					if(badPoint>10){
						return false;
					}
				}
				if(tmp.x<(firsPoint.x-MARGIN_ERROR)){
					stateOfDetection++;
					numOfPoint=0;
					firsPoint=previous;
				}
				break;
			case 2:  // detect straight line to the left
				if((tmp.x>previous.x)||(tmp.y<firsPoint.y - MARGIN_ERROR)||(tmp.y>firsPoint.y + MARGIN_ERROR)){
					badPoint++;
					if(badPoint>10){
						return false;
					}
				}
				break;
			default:
				return false;
			}
			previous=tmp;
			numOfPoint++;
		}
		if(stateOfDetection==2 && numOfPoint>MIN_NUMBER_POINT){
			return true;
		}
		return false;
	}

	@Override
	public void apply(Ship ship) {
		Game.getTheGame().getFrontApplication().getBattleField().changeStats();
	}

}
