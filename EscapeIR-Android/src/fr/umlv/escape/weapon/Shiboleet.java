package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import android.graphics.Bitmap;
import android.graphics.Point;

import fr.umlv.escape.move.StraightLine;

/**
 * Represent a Shiboleet who extends of {@link Bullet}
 */
public class Shiboleet extends Bullet {
	private static final int POWER = 12;
	private static final int MAXLOAD = 3;

	/**
	 * Constructor of Shiboleet
	 * @param name bullet's kind
	 * @param body body's bullet
	 * @param playerBullet if it's a bullet launch by player
	 */
	public Shiboleet(String name, Body body, boolean playerBullet, Bitmap img) {
		super(POWER, MAXLOAD, name, body, playerBullet, img);
	}

	@Override
	public void fire(Vec2 force) {
		int f = getCurrentLoad();
		BulletsFactory bf = BulletsFactory.getTheBulletsFactory();
		for(int i=1;i<f;i++){
			Point startPos = new Point(this.getPosXCenter(), this.getPosYCenter());
			Bullet bRight = bf.createBullet(this.getName(), startPos, this.isPlayerBullet());
			Bullet bLeft = bf.createBullet(this.getName(), startPos, this.isPlayerBullet());
			if(this.getBody()==null) return;
			Filter filter=this.getBody().getFixtureList().getFilterData();
			if(bRight != null && bLeft != null){
				bRight.getBody().getFixtureList().setFilterData(filter);
				bLeft.getBody().getFixtureList().setFilterData(filter);
				bRight.getBody().setActive(true);
				bLeft.getBody().setActive(true);
	
				double modifierForce = i*0.5;
				double arc = 0.2*i;
				if(!this.isPlayerBullet()){
					modifierForce = - modifierForce;
					arc = -arc;
				}
				double forceY = force.y + arc;
				Vec2 forceBulletLeft = new Vec2(force.x - (float)modifierForce, (float)forceY);
				Vec2 forceBulletRight = new Vec2(force.x + (float)modifierForce, (float)forceY);
				bRight.fire(forceBulletRight);
				bLeft.fire(forceBulletLeft);
			}
		}
		this.setMove(new StraightLine(force));
		getMove().move(getBody());
	}

}
