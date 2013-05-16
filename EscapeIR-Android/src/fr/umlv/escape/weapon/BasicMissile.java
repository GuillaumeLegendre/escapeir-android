package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;

import fr.umlv.escape.move.StraightLine;

/**
 * Represent a basic missile who extends of {@link Bullet}
 */
public class BasicMissile extends Bullet {
	private static final int POWER = 10;
	private static final int MAXLOAD = 1;

	/**
	 * Constructor of BasicMissile
	 * @param name bullet's kind
	 * @param body body's bullet
	 * @param playerBullet if it's a bullet launch by player
	 */
	public BasicMissile(String name, Body body, boolean playerBullet, Bitmap image) {
		super(POWER, MAXLOAD, name, body, playerBullet, image);
	}

	@Override
	public void fire(Vec2 force) {
		//directionPoint.setLocation(directionPoint.getX(), directionPoint.getY());
		this.setMove(new StraightLine(force));
		getMove().move(getBody());
	}

	@Override
	public boolean loadPower() {
		return false;
	}
}
