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
}
