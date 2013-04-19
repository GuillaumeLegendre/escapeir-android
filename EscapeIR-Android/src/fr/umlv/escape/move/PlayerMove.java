package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.Objects;
import fr.umlv.escape.gesture.Gesture;

/**
 * This class represent player move.
 * @implements {@link Movable}
 */
public class PlayerMove implements Movable {
	private Gesture gesture;
	
	/**
	 * Constructor.
	 * @param gesture {@link Gesture} that manage the player interactions.
	 */
	public PlayerMove(Gesture gesture){
		this.gesture=gesture;
	}
	
	/**
	 * Move the {@link Player} depending to the force detected by the {@link Gesture}.
	 */
	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);
		
		Vec2 vec=gesture.getLastForce();
		vec.set(vec.x*2, vec.y*2);
		body.setLinearVelocity(vec);
	}
}
