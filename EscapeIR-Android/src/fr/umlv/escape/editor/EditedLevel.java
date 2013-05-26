package fr.umlv.escape.editor;

import android.graphics.Bitmap;
import fr.umlv.escape.game.Level;

public class EditedLevel {
	static Level level;
	static Bitmap background;
	static String backgroundName;
	
	public EditedLevel(Level level, Bitmap background, String backgroundName) {
		EditedLevel.level = level;
		EditedLevel.background = background;
		EditedLevel.backgroundName = backgroundName;
	}
}
