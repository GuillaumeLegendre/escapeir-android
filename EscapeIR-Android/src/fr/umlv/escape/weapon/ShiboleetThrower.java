package fr.umlv.escape.weapon;

import android.graphics.Point;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.game.Game;

/**
 * Represent a shiboleet thrower who are an extend of {@link weapon}
 */
public class ShiboleetThrower extends Weapon {
	private static final int MAXQTY = 20;
	private static final int FREQUENCY = 500;
	private static final String MISSILE = "Shiboleet";
	private static final String NAME = "ShiboleetThrower";

	/**
	 * Constructor of ShiboleetThrower
	 * @param qty : quantity of ammo
	 */
	public ShiboleetThrower(int qty) {
		super(qty, MAXQTY, FREQUENCY, MISSILE, NAME);
	}

	@Override
	public Bullet fire(Point startPosition){
		String missile = getMissile();
		BulletsFactory bf = BulletsFactory.getTheBulletsFactory();
		boolean playerBullet = false;
		if(Game.getTheGame().getPlayer1().getShip().getCurrentWeapon() == this){
			playerBullet = true;
		}
		Bullet b = bf.createBullet(missile, startPosition, playerBullet);
		b.getBody().setActive(true);
		return b;
	}
}
