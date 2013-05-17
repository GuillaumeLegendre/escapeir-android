package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.Objects;
import fr.umlv.escape.gesture.Gesture;
import fr.umlv.escape.gesture.GestureDetector;

/**
 * This class represent player move.
 * @implements {@link Movable}
 */
public class PlayerMove implements Movable {	
	/**
	 * Constructor.
	 * @param gesture {@link Gesture} that manage the player interactions.
	 */
	public PlayerMove(){
	}
	
	/**
	 * Move the {@link Player} depending to the force detected by the {@link Gesture}.
	 */
	@Override
	public void move(Body body) {
	}
	
	@Override
	public void move(Body body, Vec2 force) {
		this.move(body);
		
		force.set(force.x*2, force.y*2);
		body.setLinearVelocity(force);
	}
}
