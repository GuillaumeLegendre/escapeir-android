package fr.umlv.escape.weapon;

/**
 * Factory of {@link Weapon}
 */
public class WeaponsFactory {
	private static WeaponsFactory theWeaponsFactory;

	private WeaponsFactory(){
	}

	/**
	 * @return the singleton of Weapon Factory
	 */
	public static WeaponsFactory getTheWeaponsFactory(){
		if(WeaponsFactory.theWeaponsFactory==null){
			WeaponsFactory.theWeaponsFactory = new WeaponsFactory();
		}
		return WeaponsFactory.theWeaponsFactory;
	}
	
	/**
	 * @param weaponName
	 * @param qty
	 * @return a weapon with a quantity of bullet in function of string weaponName
	 */
	public Weapon createWeapon(String weaponName, int qty){
		switch(weaponName){
		case "MissileLauncher" : return new MissileLauncher(qty);
		case "FlameThrower" : return new FlameThrower(qty);
		case "ShiboleetThrower" : return new ShiboleetThrower(qty);
		case "LaserBeam" : return new LaserBeam(qty);
		default : throw new IllegalArgumentException("WeaponName: "+ weaponName +" isn't a legal weapon");
		}
	}
}
