package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.front.Sprite;
/*import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.ListWeapon;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.weapon.Weapon;
import fr.umlv.escape.world.EscapeWorld;*/

/**
 * Abstract class that represent a ship in the Escape Game
 */
public class Ship {
	private final Sprite sprite;
//	private Movable moveBehaviour;
//	private Shootable shootBehaviour;
	private final String name;
	private int health;
//	private final ListWeapon weapons;
	private boolean isAlive;

	/**
	 * Constructor.
	 * 
	 * @param name The name of the ship
	 * @param body body that represent the ship in the {@link EscapeWorld}.
	 * @param health Health of the ship.
	 * @param moveBehaviour How the ship move.
	 * @param shootBehaviour How the ship shoot.
	 */
	public Ship(String name, Sprite sprite, int health, Movable moveBehaviour, Shootable shootBehaviour){
		Objects.requireNonNull(name);
		Objects.requireNonNull(sprite);
		Objects.requireNonNull(moveBehaviour);
		Objects.requireNonNull(shootBehaviour);
		if(health<0){
			throw new IllegalArgumentException("health can't be negative");
		}
		
		this.name=name;
		this.sprite=sprite;
		this.weapons = new ListWeapon();
		this.moveBehaviour=moveBehaviour;
		this.health=health;
		this.isAlive=true;
		this.shootBehaviour=shootBehaviour;
	}
	
	/**
	 * Method to call when a ship have to take some damages.
	 * @param damage The amount of health the ship should lose.
	 */
	public void takeDamage(int damage){
		this.health-=damage;
		if(this.health<=0){
			this.health = 0;
			this.setAlive(false);
		}
	}
	
	/**
	 * Return the health of the ship.
	 * @return The health of the ship.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Set the health of the ship.
	 * @param health the new health of the ship.
	 */
	public void setHealth(int health) {
		if(health<0){
			this.health = 0;
		}
		this.health = health;
	}

	/**
	 * Return the x position of the center of the ship.
	 * @return The x position of the center of the ship.
	 */
	public int getPosXCenter() {
		return (int)(sprite.body.getPosition().x*EscapeWorld.SCALE);
	}

	/**
	 * Return the y position of the center of the ship.
	 * @return The y position of the center of the ship.
	 */
	public int getPosYCenter() {
		return (int)(sprite.body.getPosition().y*EscapeWorld.SCALE);
	}
	
	/**
	 * Return if the ship is alive. The state not alive should represent the ship with 0 health.
	 * @return true if the ship is alive else false.
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Set if the ship is alive.
	 * @param isAlive State of the ship.
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	/**
	 * Method that move the ship depending the last {@link Movable} set.
	 */
	public void move() {
		this.getMoveBehaviour().move(this.getBody());
	}

	/**Create a bullet to shoot at the position x and y.
	 * 
	 * @param x Position x where to spawn the bullet.
	 * @param y Position x where to spawn the bullet.
	 * @return true if the bullet has been created else false.
	 */
	public boolean shoot(int x,int y){
		if(!this.isAlive){
			return false;
		}
		return shootBehaviour.shoot(weapons.getCurrentWeapon(),x,y);
	}
	
	/**
	 * launch the bullet previously created with the method {@link #shoot(int, int)}. 
	 */
	public void fire() {
		Weapon w = weapons.getCurrentWeapon();
		shootBehaviour.fire(w);
		if(w.getBulletQty() <= 0){
			weapons.setNextWeapon();
		}
	}

	/**
	 * Return the move behavior of the ship.
	 * @return The {@link Movable} of the ship.
	 */
	public Movable getMoveBehaviour() {
		return moveBehaviour;
	}

	/**
	 * Set the move behavior of the ship.
	 * @param moveBehaviour The {@link Movable} representing the new move behavior of the ship.
	 */
	public void setMoveBehaviour(Movable moveBehaviour) {
		this.moveBehaviour = moveBehaviour;
	}
	
	/**
	 * Return the shoot behavior of the ship.
	 * @return The {@link Shootable} of the ship.
	 */
	public Shootable getShootBehaviour() {
		return shootBehaviour;
	}

	/**
	 * Set the shoot behavior of the ship.
	 * @param shootBehaviour The {@link Shootable} representing the new shoot behavior of the ship.
	 */
	public void setShootBehaviour(Shootable shootBehaviour) {
		this.shootBehaviour = shootBehaviour;
	}

	/**
	 * Return the current weapon of the ship.
	 * @return The {@link Weapon} of the ship.
	 */
	public Weapon getCurrentWeapon() {
		return this.weapons.getCurrentWeapon();
	}

	/**
	 * Return the current body that represent the ship in the {@link EscapeWorld}.
	 * @return The current body that represent the ship in the {@link EscapeWorld}.
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * Return the {@link ListWeapon} of the ship.
	 * @return The {@link ListWeapon} of the ship.
	 */
	public ListWeapon getListWeapon() {
		return weapons;
	}
	
	/**
	 * Return the name of the ship. This name can depend of some state of the ship.
	 * @return The name of the ship.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the basic name of the ship.
	 * @return The basic name of the ship.
	 */
	public String getBasicName() {
		return name;
	}
	
	@Override
	public String toString(){
		return this.name+" x:"+this.getPosXCenter()+" y:"+this.getPosYCenter()+" health:"+this.health+" Trajectory:"+this.moveBehaviour;
	}
}
