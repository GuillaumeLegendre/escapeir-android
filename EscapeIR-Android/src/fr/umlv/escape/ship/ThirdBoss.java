package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;

import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.front.FrontImages;
import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Class that represent the third boss.
 * @extends {@link Ship}.
 */
public class ThirdBoss extends Ship{
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
	public ThirdBoss(int health, Body body,Bitmap image, Movable moveBehaviour,Shootable shootBehaviour){
		super("third_boss",health,body,image,moveBehaviour,shootBehaviour);
		this.state=1;
		this.fullHealth=health;
	}

	@Override
	public void takeDamage(int damage){
		this.setHealth(this.getHealth()-damage);
		if(this.getHealth()<=0){
			this.setHealth(fullHealth);
			this.state++;
			if(state<=5)
				this.image = FrontApplication.frontImage.getImage(getName()+""+state);
		}
		if(state>6){
			this.setAlive(false);
			state=6;
		}
	}

	/**
	 * Get the current state of the boss.
	 * @return The current state of the boss.
	 */
	public int getState() {
		return state;
	}

}
