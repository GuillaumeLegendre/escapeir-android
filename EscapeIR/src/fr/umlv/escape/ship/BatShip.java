package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Shootable;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Class that represent a bat ship.
 * @extends {@link Ship}.
 */
public class BatShip extends Ship{
	/**
	 * Constructor.
	 * 
	 * @param body body that represent the ship in the {@link EscapeWorld}.
	 * @param health Health of the ship.
	 * @param moveBehaviour How the ship move.
	 * @param shootBehaviour How the ship shoot.
	 */
	public BatShip(Body body, int health, Movable moveBehaviour,Shootable shootBehaviour){
		super("BatShip",body,health,moveBehaviour,shootBehaviour);
	}
}
