package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;

import android.graphics.Point;

import fr.umlv.escape.Objects;

/**
 * Class that represent a down shoot.
 * @implements {@link Shootable}.
 */
public class ShootDown implements Shootable{
	private long lastShoot=0;
	
	@Override
	public boolean shoot(Weapon weapon,int x,int y) {
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
		if(currentTime-lastShoot>4000){
			Point positionShip = new Point(x,y);
			weapon.setLoadingBullet(weapon.fire(positionShip));
			lastShoot=currentTime;
			return true;
		}
		return false;
	}
	
	@Override
	public void fire(Weapon weapon) {
		Objects.requireNonNull(weapon);
		
		weapon.getLoadingBullet().fire(new Vec2(0f,2f));
	}
}
