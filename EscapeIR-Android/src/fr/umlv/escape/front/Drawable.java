package fr.umlv.escape.front;

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
}
