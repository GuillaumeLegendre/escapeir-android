package fr.umlv.escape.gesture;

import java.util.ArrayList;

import fr.umlv.escape.ship.Ship;

import android.graphics.Point;

public interface Gesture {
	public boolean isRecognized(ArrayList<Point> p);
	public void apply(Ship ship);
}
