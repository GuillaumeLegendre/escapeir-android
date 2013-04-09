package fr.umlv.escape.front;

import android.graphics.Bitmap;


public interface Drawable {
	/**
	 * Says if the {@link Drawable} should be drawn or not.
	 * @return True if the {@link Drawable} should be drawn else false.
	 */
	public boolean isStillDrawable();

	/**
	 * Specify that the {@link Drawable} should not be drawn anymore.
	 */
	public void doNotDrawAnymore(); // trouver un meilleur nom ^^

	/**
	 * @return The {@link Bitmap} to draw.
	 */
	public Bitmap getImage();

	/**
	 * @return The center position X of the {@link Drawable}.
	 */
	public float getPosX();

	/**
	 * @return The center position Y of the {@link Drawable}.
	 */
	public float getPosY();
}
