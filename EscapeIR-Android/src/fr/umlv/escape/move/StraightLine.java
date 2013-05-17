package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.Objects;

/**
 * This class represent a straight line move.
 * @implements {@link Movable}
 */
public class StraightLine implements Movable {
	private Vec2 lastForce;
	
	/**
	 * Constructor.
	 * @param force that will define the magnitude of the move.
	 */
	public StraightLine(Vec2 force){
		this.lastForce=force;
	}
	
	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);
		
		body.applyLinearImpulse(lastForce,body.getLocalCenter());	
	}
	
	@Override
	public String toString(){
		return "StraightLine";
	}
	
	@Override
	public void move(Body body, Vec2 force) {
		this.move(body);
	}
}
