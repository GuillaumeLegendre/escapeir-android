package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Class that represent the first boss.
 * @extends {@link Ship}.
 */
public class FirstBoss extends Ship {
	private int state;
	private int fullHealth;
	
	/**
	 * Constructor.
	 * 
	 * @param body body that represent the ship in the {@link EscapeWorld}.
	 * @param health Health of the ship.
	 * @param moveBehaviour How the ship move.
	 * @param shootBehaviour How the ship shoot.
	 */
	public FirstBoss(Body body, int health, Movable moveBehaviour,Shootable shootBehaviour){
		super("FirstBoss",body,health,moveBehaviour,shootBehaviour);
		this.state=1;
		this.fullHealth=health;
	}
	
	@Override
	public void takeDamage(int damage){
		this.setHealth(this.getHealth()-damage);
		if(this.getHealth()<=0){
			this.setHealth(fullHealth);
			this.state++;
		}
		if(state>8){
			state=8;
		}
	}

	@Override
	public String getName(){		
		if(state>1){
			return super.getName()+state;
		}
		return super.getName();
	}
	
	/**
	 * Get the current state of the boss.
	 * @return The current state of the boss.
	 */
	public int getState() {
		return state;
	}
}
