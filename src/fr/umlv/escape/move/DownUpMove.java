package fr.umlv.escape.move;

import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.world.EscapeWorld;

/**
 * This class represent a down move followed by an up move.
 * @implements {@link Movable}
 */
public class DownUpMove implements Movable{
	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);

		if(body.getLinearVelocity().y==0){
			Vec2 v2 =new Vec2(0.0f, 8.0f);
			body.setLinearDamping(1f);
			body.setLinearVelocity(v2);
		}
		if(body.getPosition().y*EscapeWorld.SCALE<10){
			Vec2 v2 =new Vec2(0.0f, 8.0f);
			body.setLinearDamping(1f);
			body.setLinearVelocity(v2);
		}
		if(body.getPosition().y*EscapeWorld.SCALE>400){
			Vec2 v2 =new Vec2(0.0f, -4.0f);
			body.setLinearDamping(0);
			body.setLinearVelocity(v2);
		}
	}
}
