package fr.umlv.escape.weapon;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Represent all the {@link Ship}'s {@link Weapon}s and his current {@link Weapon}
 */
public class ListWeapon {
	private final Hashtable<String, Weapon> weapons;
	private final ArrayList<Weapon> weaponsList;
	private Weapon current;
	/**Constant that represent the basic quantity of bullet.
	 */
	public final static int BASIC_QTY_BULLET = 99;
	/**Constant that represent the basic weapon.
	 */
	public final static String BASIC_WEAPON = "missile_launcher";
	
	/**
	 * Constructor of listWeapon
	 */
	public ListWeapon(){
		weapons = new Hashtable<String, Weapon>();
		weaponsList = new ArrayList<Weapon>();
		current = addWeapon(BASIC_WEAPON, 0);
		addWeapon("missile_launcher", 0);
		addWeapon("flame_thrower", 10);
		addWeapon("shiboleet_thrower", 0);
		addWeapon("laser_beam", 0);
	}

	/**
	 * Add a {@link Weapon} to the list weapon
	 * @param name of weapon
	 * @param qty of bullet in weapon
	 * @return the weapon add
	 */
	public Weapon addWeapon(String name, int qty) {
		Weapon w;
		if(weapons.containsKey(name)) {
			w = weapons.get(name);
			w.addQte(qty);
		} else {
			WeaponsFactory wf = WeaponsFactory.getTheWeaponsFactory();
			w = wf.createWeapon(name, qty);
			weapons.put(name, w);
			weaponsList.add(w);
		}
		return w;
	}

	/**
	 * @return a list of {@link Weapon}s
	 */
	public List<Weapon> getWeapons() {
		return weaponsList;
	}

	/**
	 * @return the current {@link Weapon}
	 */
	public Weapon getCurrentWeapon(){
		return current;
	}

	/**
	 * Set a weapon to current weapon if there are no {@link Bullet} in loading and if weapon isn't out of ammo.
	 * @param w to set in current
	 * @return true if weapon become current
	 */
	public boolean setCurrentWeapon(String w){
		if(current.getLoadingBullet() == null){
			Weapon weapon = weapons.get(w);
			if((weapon != null) && ((weapon.getBulletQty() > 0) || (weapon.getBulletQty()==Integer.MIN_VALUE))){
				current = weapon;
				return true;
			}
		}
		return false;
	}

	/**
	 * Put to the next {@link Weapon} who aren't out of ammo. if they aren't weapon out of ammo the current weapon is not change.
	 */
	public void setNextWeapon() {
		for(Weapon w : weaponsList){
			if(w.getBulletQty() > 0){
				current = w;
			}
		}
	}
}
