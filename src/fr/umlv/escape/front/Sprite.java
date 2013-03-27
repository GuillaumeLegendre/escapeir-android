package fr.umlv.escape.front;

import java.awt.Image;

/**
 * Interface that represent an animation
 */
public interface Sprite {
	/** Return the next image of the sprite to draw
	 * @return the next image of the sprite to draw
	 */
	public Image getNextImage();
}
