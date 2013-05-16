package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import fr.umlv.escape.front.Sprite;
import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Abstract class that represent a ship in the Escape Game
 */
public class Ship extends Sprite{
	private final String name;
	public int health;
	private boolean isAlive;
	private Movable moveBehaviour;
	private Shootable shootBehaviour;
	

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
		this.moveBehaviour.move(this.body);
	}
}
