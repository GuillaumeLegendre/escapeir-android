package fr.umlv.escape.weapon;

import java.awt.Point;
import java.util.Objects;

import org.jbox2d.dynamics.Filter;
import fr.umlv.escape.gesture.Gesture;
import fr.umlv.escape.ship.Ship;

/**
 * Class that represent the shoot behavior of the {@link Player}.
 * @implements {@link Shootable}.
 */
public class ShootPlayer implements Shootable{
	private final Gesture gesture;
	
	/**
	 * Constructor
	 * @param gesture {@link Gesture} used by the {@link Player}.
	 */
	public ShootPlayer(Gesture gesture) {
		Objects.requireNonNull(gesture);
		
		this.gesture=gesture;
	}
	
	private boolean canShoot(Weapon weapon){
		Objects.requireNonNull(weapon);
		
		return weapon.getLoadingBullet() == null && weapon.canShoot();
	}
	@Override
	public boolean shoot(Weapon weapon,int x,int y) {
		Objects.requireNonNull(weapon);
		
		if(canShoot(weapon)){
			Point positionShip = new Point(x,y);
			Bullet bullet=weapon.fire(positionShip);
			Filter filter=new Filter();
			filter.categoryBits=8;
			filter.maskBits=4;
			bullet.getBody().getFixtureList().setFilterData(filter);
			bullet.getBody().setActive(true);
			weapon.setLoadingBullet(bullet);
			return true;
		}
		return false;
	}
	
	@Override
	public void fire(Weapon weapon) {
		Objects.requireNonNull(weapon);
		
		Bullet b = weapon.getLoadingBullet();
		if(b != null){
			b.fire(gesture.getLastForce());
			weapon.setLoadingBullet(null);
		}
	}
}
