package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import fr.umlv.escape.move.XRayMove;

/**
 * Represent a XRay who extends of {@link Bullet}
 */
public class XRay extends Bullet{
	private static final int POWER = 50;
	private static final int MAXLOAD = 1;

	/**
	 * Constructor of XRay
	 * @param name bullet's kind
	 * @param body body's bullet
	 * @param playerBullet if it's a bullet launch by player
	 */
	public XRay(String name, Body body, boolean playerBullet, Bitmap img) {
		super(POWER, MAXLOAD, name, body, playerBullet, img);
	}

	@Override
	public void fire(Vec2 force) {
		this.setMove(new XRayMove());
		getMove().move(getBody());
	}
}
