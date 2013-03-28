package fr.umlv.escape.weapon;

/**
 * This class represent an object that can't shoot.
 * @implements {@link Shootable}
 */
public class DoNotShoot implements Shootable {
	@Override
	public boolean shoot(Weapon weapon,int x,int y) {
		return true;
	}
	
	@Override
	public void fire(Weapon weapon) {
	}
}
