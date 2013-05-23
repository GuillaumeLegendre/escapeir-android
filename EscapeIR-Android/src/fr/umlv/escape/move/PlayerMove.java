package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.Objects;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.gesture.Gesture;
import fr.umlv.escape.gesture.GestureDetector;
import fr.umlv.escape.ship.Ship;

/**
 * This class represent player move.
 * @implements {@link Movable}
 */
public class PlayerMove implements Movable {	
	private Ship playerShip;
	
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
		
		if(this.playerShip==null){
			Game.getTheGame().getPlayer1().getShip();
		}
		
		force.set(force.x*2, force.y*2);
		body.setLinearVelocity(force);
		/*if(playerShip.getCurrentWeapon().getLoadingBullet()!=null){
			playerShip.getCurrentWeapon().getLoadingBullet().getBody().setLinearVelocity(force);
		}*/
	}
}
