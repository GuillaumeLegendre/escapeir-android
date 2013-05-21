package fr.umlv.escape.weapon;

/**
 * Represent a missile launcher who are an extend of {@link weapon}
 */
public class MissileLauncher extends Weapon {
	private static final int MAXQTY = 99;
	private static final int FREQUENCY = 500;
	private static final String MISSILE = "basic_missile";
	private static final String NAME = "missile_launcher";

	/**
	 * Constructor of MissileLauncher
	 * @param qty : quantity of ammo
	 */
	public MissileLauncher(int qty) {
		super(qty, MAXQTY, FREQUENCY, MISSILE, NAME);
	}

}
