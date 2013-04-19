package fr.umlv.escape.move;

import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * This class represent the move of the {@link XRay}. it is just a left move accelerated. This move never stop itself
 * @implements {@link Movable}
 */
public class XRayMove implements Movable {
	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);
		
		if(body.getLinearVelocity().x==0){
			Vec2 v2 =new Vec2(4.0f, 0.0f);
			body.setLinearVelocity(v2);
		}
	}
}
