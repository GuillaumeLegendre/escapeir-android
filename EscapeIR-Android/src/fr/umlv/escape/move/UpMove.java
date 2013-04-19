package fr.umlv.escape.move;

import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * This class represent a up move. This move never stop itself
 * @implements {@link Movable}
 */
public class UpMove implements Movable{
	private boolean hasBeenCalled=false;
	@Override
	
	public void move(Body body) {
		Objects.requireNonNull(body);
		
		if(!hasBeenCalled){
			Vec2 v2 =new Vec2(0.0f, -2.0f);
			body.setLinearVelocity(v2);
			hasBeenCalled=true;
		}
	}
}
