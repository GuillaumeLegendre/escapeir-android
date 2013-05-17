package fr.umlv.escape.move;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escape.world.EscapeWorld;

/**Interface that all objects which can move should implements.
 */
public interface Movable {
	/**
	 * Method that moves a {@link Body}.
	 * @param body {@link Body} that represent the object to move in the {@link EscapeWorld}
	 */
	public void move(Body body);

	public void move(Body body, Vec2 force);
}
