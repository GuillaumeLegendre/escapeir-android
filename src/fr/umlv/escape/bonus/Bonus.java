package fr.umlv.escape.bonus;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.world.EscapeWorld;


/**Interface that all kind of bonus should implements. This interface contains all nesserary methods
 * needed to manage a bonus.
 */
public interface Bonus {
	
	/**Return the name of the {@link Bonus}.
	 * @return the name of the {@link Bonus}.
	 */
	public String getName();
	
	/**Return the type of the {@link Bonus}.
	 * @return the type of the {@link Bonus}.
	 */
	public String getType();
	
	/**Return the quantity of the {@link Bonus}.
	 * @return the quantity of the {@link Bonus}.
	 */
	public int getQuantity();
	
	/**Return the x position of the center of the {@link Bonus}.
	 * @return the x position of the center of the {@link Bonus}.
	 */
	public int getPosXCenter();
	
	/**Return the y position of the center of the {@link Bonus}.
	 * @return the y position of the center of the {@link Bonus}.
	 */
	public int getPosYCenter();
	
	/**Return the {@link Body} that represent the bonus in the {@link EscapeWorld}.
	 * @return the {@link Body} that represent the bonus in the {@link EscapeWorld}.
	 */
	public Body getBody();
	
	/**Method that move the bonus.
	 */
	public void move();
}
