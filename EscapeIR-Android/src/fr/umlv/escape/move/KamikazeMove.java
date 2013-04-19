package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.Objects;
import fr.umlv.escape.game.Game;

/**
 * This class represent a down move that follow the y position {@link Player}. This move never stop itself
 * @implements {@link Movable}
 */
public class KamikazeMove implements Movable{
	@Override
	public void move(Body body) {
		Objects.requireNonNull(body);

		float posXplayer=Game.getTheGame().getPlayer1().getShip().getBody().getPosition().x;
		Vec2 v2 =new Vec2(0.0f, 2.0f);
		if(posXplayer<body.getPosition().x-0.1){
			v2.set(-2.0f, 2.0f);
		}
		else if(posXplayer>body.getPosition().x+0.1){
			v2.set(2.0f, 2.0f);
		}
		body.setLinearVelocity(v2);
	}
}
