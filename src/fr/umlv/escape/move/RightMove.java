package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * This class represent a Right move. This move never stop itself
 * @implements {@link Movable}
 */
public class RightMove implements Movable{
	private boolean hasBeenCalled=false;
	@Override
	public void move(Body body) {
		if(!hasBeenCalled){
			Vec2 v2 =new Vec2(2.0f, 0.0f);
			body.setLinearVelocity(v2);
			hasBeenCalled=true;
		}
	}
}
