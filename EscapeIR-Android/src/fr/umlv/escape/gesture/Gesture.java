package fr.umlv.escape.gesture;

import java.util.ArrayList;
import android.graphics.Point;

public interface Gesture {
	public boolean isRecognized(ArrayList<Point> p);
	public void apply();
}
