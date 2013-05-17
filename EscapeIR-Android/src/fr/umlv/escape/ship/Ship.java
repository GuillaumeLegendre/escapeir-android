package fr.umlv.escape.ship;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import fr.umlv.escape.front.Sprite;
import fr.umlv.escape.front.SpriteShip;
import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.ListWeapon;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.weapon.Weapon;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Abstract class that represent a ship in the Escape Game
 */
public class Ship extends SpriteShip{
	private final String name;
	public int health;
	private boolean isAlive;
	private Movable moveBehaviour;
	private Shootable shootBehaviour;
	private final ListWeapon weapons;
	

	/**
	 * Constructor.
	 * 
	 * @param name The name of the ship
	 * @param body body that represent the ship in the {@link EscapeWorld}.
	 * @param health Health of the ship.
	 * @param moveBehaviour How the ship move.
	 * @param shootBehaviour How the ship shoot.
	 */
	public Ship(String name, int health, Body body, Bitmap image, Movable moveBehaviour, Shootable shootBehaviour){
		super(body, image);
		if(health<0){
			throw new IllegalArgumentException("health can't be negative");
		}
		this.weapons = new ListWeapon();
		this.name=name;
		this.health=health;
		this.isAlive=true;
		this.moveBehaviour = moveBehaviour;
		this.shootBehaviour = shootBehaviour;
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
	 * Return the name of the ship.
	 * @return The name of the ship.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method that move the ship depending the last {@link Movable} set.
	 */
	public void move() {
		this.moveBehaviour.move(body);
	}
	
	public void move(Vec2 force) {
		if(force == null ||
			(force.x == 0) &&
			(force.y == 0)){
			this.move();
		}
		else{
			this.moveBehaviour.move(this.body, force);
		}
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
	 * Return the current weapon of the ship.
	 * @return The {@link Weapon} of the ship.
	 */
	public Weapon getCurrentWeapon() {
		return this.weapons.getCurrentWeapon();
	}

	/**
	 * Return the {@link ListWeapon} of the ship.
	 * @return The {@link ListWeapon} of the ship.
	 */
	public ListWeapon getListWeapon() {
		return weapons;
	}
	
	public Movable getMoveBehaviour() {
		return moveBehaviour;
	}

	public void setMoveBehaviour(Movable moveBehaviour) {
		this.moveBehaviour = moveBehaviour;
	}

	public Shootable getShootBehaviour() {
		return shootBehaviour;
	}

	public void setShootBehaviour(Shootable shootBehaviour) {
		this.shootBehaviour = shootBehaviour;
	}

	public ListWeapon getWeapons() {
		return weapons;
	}
}
