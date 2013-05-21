package fr.umlv.escape.weapon;

/**
 * Represent a flame thrower who are an extend of {@link weapon}
 */
public class FlameThrower extends Weapon {
	private static final int MAXQTY = 50;
	private static final int FREQUENCY = 400;
	private static final String MISSILE = "fireball";
	private static final String NAME = "FlameThrower";

	/**
	 * Constructor of FlameThrower
	 * @param qty : quantity of ammo
	 */
	public FlameThrower(int qty) {
		super(qty, MAXQTY, FREQUENCY, MISSILE, NAME);
	}
}
