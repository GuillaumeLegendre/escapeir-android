package fr.umlv.escape.weapon;

/**Interface representing a shoot behaviour that all objects that can shoot should implements.
 */
public interface Shootable {
	/**
	 * Create a bullet.
	 * @param weapon {@link Weapon} that shoot.
	 * @param x The x position where to spawn the bullet.
	 * @param y The y position where to spawn the bullet.
	 * @return true if a bullet has been created else false.
	 */
	public boolean shoot(Weapon weapon,int x,int y);
	/**
	 * Launch a bullet created previously with the method {@link #shoot(Weapon, int, int)}.
	 * @param weapon {@link Weapon} that have created the bullet.
	 */
	public void fire(Weapon weapon);
}
