package fr.umlv.escape.front;

import java.util.ArrayList;

import fr.umlv.escape.ship.Ship;
import android.graphics.Bitmap;

public class BattleField {
	Bitmap Backgound; //TODO Change to backgroundScroller
	final ArrayList<Ship> shipList;
	
	public BattleField(Bitmap background) {
		this.Backgound = background;
		this.shipList = new ArrayList<Ship>();
	}
}
