package fr.umlv.escape.game;

import fr.umlv.escape.Objects;
import fr.umlv.escape.ship.Ship;


/**Class that represent the player
 */
public class Player {
	final String name;
	Ship ship;
	int life;
	int score;
	int next_life_with_score = 1;
	
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
		this.ship = ship;
		this.life=life;
	}
	
	/**
	 * Cheat code to have a lot of life and all the weapons
	 */
	public void codeKonami(){
		this.life = 99;
		ship.health = 999;
		/*ArrayList<Weapon> lw = ship.getListWeapon().getWeapons();
		for(int i=0; i<lw.size(); ++i){
			lw.get(i).addQte(99);
		}*/
		//TODO remetre
	}
	
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public String getName() {
		return name;
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
		if(this.score > 2500 * next_life_with_score){
			this.life++;
			next_life_with_score++;
		}
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
}
