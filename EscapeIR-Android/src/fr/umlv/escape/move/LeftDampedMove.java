package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import fr.umlv.escape.Objects;

/**
 * This class represent a left move which is slowed until reaching a null velocity.
 * @implements {@link Movable}
 */
public class LeftDampedMove implements Movable{
	private boolean hasBeenCalled=false;
	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);

		if(!hasBeenCalled){
			Vec2 v2 =new Vec2(-4.0f, 0.0f);
			body.setLinearVelocity(v2);
			body.setLinearDamping(0.9f);
			hasBeenCalled=true;
		}
	}
	
	@Override
	public void move(Body body, Vec2 force) {
		this.move(body);
	}
}
