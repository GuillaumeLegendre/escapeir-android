package fr.umlv.escape.game;

import java.util.List;
import java.util.Objects;

import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Weapon;

/**Class that represent the player
 */
public class Player {
	private final String name;
	private Ship ship;
	private int life;
	private int score;
	
	/**
	 * Constructor.
	 * @param name The name of the player.
	 * @param ship The ship of the player.
	 * @param life The number of life of the player.
	 */
	public Player(String name, Ship ship, int life){
		Objects.requireNonNull(name);
		Objects.requireNonNull(ship);
		
		this.name=name;
		this.setShip(ship);
		this.life=life;
	}

	/**
	 * Return the current score of the player
	 * @return The current score of the player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Add some points to the current score of the player
	 * @param score Number of points to add to the current score
	 */
	public void addScore(int score) {
		this.score = this.score + score;
	}
	
	/**
	 * Get the current number of life of the player.
	 * @return The current number of life of the player.
	 */
	public int getLife() {
		return life;
	}
	
	/**
	 * Set the current number of life of the player.
	 * @param life the new number of life of the player.
	 */
	public void setLife(int life) {
		if(life<0){
			this.life=0;
		}
		this.life = life;
	}

	/**
	 * Get the name of the player.
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the {@link Ship} of the player.
	 * @return the {@link Ship} of the player.
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Set the {@link Ship} of the player
	 * @param ship the new {@link Ship} of the player
	 */
	public void setShip(Ship ship) {
		Objects.requireNonNull(ship);
		this.ship = ship;
	}
	
	/**
	 * Cheat code to have a lot of life and all the weapons
	 */
	public void codeKonami(){
		this.setLife(99);
		Ship ship = this.getShip();
		ship.setHealth(999);
		List<Weapon> lw = ship.getListWeapon().getWeapons();
		for(Weapon w : lw){
			w.addQte(99);
		}
	}
}
