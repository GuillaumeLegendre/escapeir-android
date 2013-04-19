package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;

import android.graphics.Point;

import fr.umlv.escape.Objects;
import fr.umlv.escape.weapon.Shootable;

/**
 * This class represent alternating left and right shoot.
 * @implements {@link Shootable}
 */
public class BatShipShoot implements Shootable{
	private long lastShoot=0;
	private float xforce=1;

	@Override
	public boolean shoot(Weapon weapon, int x, int y) {
		Objects.requireNonNull(weapon);
		
		long currentTime=System.currentTimeMillis();
		
		if(currentTime-lastShoot>500){
			int ghostShoot=weapon.getGhostShoot();
			if(ghostShoot>0){
				weapon.setGhostShoot(ghostShoot-1);
				lastShoot=currentTime;
				return false;
			}
		}
		if(currentTime-lastShoot>1000){
			Point positionShip;
			if(xforce==1){
				positionShip = new Point(x-20,y);
				xforce=-1;
			} else {
				positionShip = new Point(x+20,y);
				xforce=1;
			}
			weapon.setLoadingBullet(weapon.fire(positionShip));
			lastShoot=currentTime;
			return true;
		}
		return false;
	}

	@Override
	public void fire(Weapon weapon) {
		Objects.requireNonNull(weapon);
		
		weapon.getLoadingBullet().fire(new Vec2(xforce,2f));
	}
}
