package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;

import fr.umlv.escape.move.StraightLine;

/**
 * Represent a fire ball who extends of {@link Bullet}
 */
public class FireBall extends Bullet {
	private static final int POWER = 10;
	private static final int MAXLOAD = 3;

	/**
	 * Constructor of FireBall
	 * @param name bullet's kind
	 * @param body body's bullet
	 * @param playerBullet if it's a bullet launch by player
	 */
	public FireBall(String name, Body body, boolean playerBullet, Bitmap img) {
		super(POWER, MAXLOAD, name, body, playerBullet, img);
	}

	@Override
	public void fire(Vec2 force) {
		this.setMove(new StraightLine(force));
		getMove().move(getBody());
	}
	
	@Override
	public int getDamage(){
		return POWER * this.getCurrentLoad();
	}

}
