package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Class that represent the second boss.
 * @extends {@link Ship}.
 */
public class SecondBoss extends Ship {
	private boolean invisible;
	private int lvlInvisible;
	
	/**
	 * Constructor.
	 * 
	 * @param body body that represent the ship in the {@link EscapeWorld}.
	 * @param health Health of the ship.
	 * @param moveBehaviour How the ship move.
	 * @param shootBehaviour How the ship shoot.
	 */
	public SecondBoss(Body body, int health, Movable moveBehaviour,Shootable shootBehaviour){
		super("SecondBoss",body,health,moveBehaviour,shootBehaviour);
		this.invisible = false;
		this.lvlInvisible=1;
	}
	
	@Override
	public String getName(){
		if(lvlInvisible>1){
			return super.getName() + lvlInvisible;
		}
		return super.getName();
	}
	
	/**
	 * get the current level of invisibility.
	 * @return The current level of invisibility.
	 */
	public int getLvlInvisible() {
		return lvlInvisible;
	}

	/**
	 * Increment the level of invisibility.
	 */
	public void incLvlInvisible() {
		if(this.lvlInvisible<7){
			this.lvlInvisible++;
		}
		if(lvlInvisible==7){
			this.invisible=true;
		}
	}
	
	/**
	 * Decrement the level of invisibility.
	 */
	public void decLvlInvisible() {
		if(this.lvlInvisible>1){
			this.lvlInvisible--;
		}
		if(lvlInvisible==1){
			this.invisible=false;
		}
	}
	
	/**
	 * Return is the boss is becoming invisible or not.
	 * @return true if the boss is becoming invisible else false.
	 */
	public boolean isInvisible() {
		return invisible;
	}
}
