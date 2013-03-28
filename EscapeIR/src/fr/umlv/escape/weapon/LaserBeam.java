package fr.umlv.escape.weapon;

/**
 * Represent a Laser Beam who are an extend of {@link weapon}
 */
public class LaserBeam extends Weapon {
	private static final int MAXQTY = 1;
	private static final int FREQUENCY = 400;
	private static final String MISSILE = "XRay";
	private static final String NAME = "LaserBeam";

	/**
	 * Constructor of LaserBeam
	 * @param qty : quantity of ammo
	 */
	public LaserBeam(int qty) {
		super(qty, MAXQTY, FREQUENCY, MISSILE, NAME);
	}
}
