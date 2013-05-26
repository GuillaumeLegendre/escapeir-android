package fr.umlv.escape.editor;

import android.graphics.Bitmap;
import fr.umlv.escape.game.Level;

public class EditedLevel {
	static Level level;
	static Bitmap background;
	
	public EditedLevel(Level level, Bitmap background) {
		this.level = level;
		this.background = background;
	}
}
