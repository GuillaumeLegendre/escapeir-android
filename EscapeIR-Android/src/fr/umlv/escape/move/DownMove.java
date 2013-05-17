package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import fr.umlv.escape.Objects;

/**
 * This class represent a down move. This move never stop itself
 * @implements {@link Movable}
 */
public class DownMove implements Movable{
	private boolean hasBeenCalled=false;

	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);
		if(!hasBeenCalled){
			Vec2 v2 =new Vec2(0.0f, 2.0f);
			body.setLinearVelocity(v2);
			hasBeenCalled=true;
		}
	}

	@Override
	public void move(Body body, Vec2 force) {
		this.move(body);
	}
}
