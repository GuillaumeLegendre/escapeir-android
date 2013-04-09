package fr.umlv.escape.front;

import android.content.Context;
import android.view.SurfaceView;

/** This class manage if a {@link Drawable} object should still be drawn at the screen or not.
 * {@link Drawable} are not directly drawn by the the DrawableMonitor but through the {@link DrawerThread}.
 */
public class DrawableMonitor extends SurfaceView{

	public DrawableMonitor(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
}
