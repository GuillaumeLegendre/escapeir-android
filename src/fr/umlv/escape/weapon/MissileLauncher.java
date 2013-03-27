package fr.umlv.escape.weapon;

/**
 * Represent a missile launcher who are an extend of {@link weapon}
 */
public class MissileLauncher extends Weapon {
	private static final int MAXQTY = 99;
	private static final int FREQUENCY = 500;
	private static final String MISSILE = "BasicMissile";
	private static final String NAME = "MissileLauncher";

	/**
	 * Constructor of MissileLauncher
	 * @param qty : quantity of ammo
	 */
	public MissileLauncher(int qty) {
		super(qty, MAXQTY, FREQUENCY, MISSILE, NAME);
	}

}
