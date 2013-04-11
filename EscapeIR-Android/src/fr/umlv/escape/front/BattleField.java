package fr.umlv.escape.front;

import java.util.ArrayList;

import fr.umlv.escape.ship.Ship;
import android.graphics.Bitmap;

public class BattleField {
	BackGroundScroller backgoundScroller;
	final ArrayList<Ship> shipList;
	
	public BattleField(int width, int height, Bitmap background) {
		this.backgoundScroller = new BackGroundScroller(width,height,background);
		this.shipList = new ArrayList<Ship>();
	}
}
