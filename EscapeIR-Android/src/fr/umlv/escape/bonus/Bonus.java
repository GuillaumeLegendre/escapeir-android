package fr.umlv.escape.bonus;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import fr.umlv.escape.Objects;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.front.FrontImages;
import fr.umlv.escape.front.Sprite;
import fr.umlv.escape.move.DownMove;
import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Weapon;
import fr.umlv.escape.world.EscapeWorld;

/**Class that represent a reloader for a given {@link Weapon}.
 * @Implements {@link Bonus}
 */
public class Bonus extends Sprite{
	private final String name;
	private final int quantity;
	private final Body body;
	private final String type;
	private final Movable moveBehaviour;

	/**Constructor.
	 * @param quantity quantity of ammo that the bonus will reload.
	 * @param body body that represent the bonus in the {@link EscapeWorld}.
	 * @param type Type that represents the weapon to reload.
	 */
	public Bonus(int quantity, Body body,String type, Bitmap img){
		super(body, img);
		if(quantity<0){
			throw new IllegalArgumentException("quantity can't be negative");
		}
		Objects.requireNonNull(body);
		Objects.requireNonNull(type);
		this.name="weapon_reloader";
		this.quantity=quantity;
		this.body=body;
		this.type=type;
		this.moveBehaviour=new DownMove();
	}

	public void move(){
		moveBehaviour.move(body);
	}
	
	public String getType(){
		return type;
	}
	
	public Body getBody() {
		return body;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public boolean isStillDisplayable(){
		return this.body.isActive();
	}
	
	@Override
	public void onDrawSprite(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDrawSprite(canvas);
		Paint p = new Paint();
		p.setTextSize(15);
		p.setColor(Color.BLUE);
		
		canvas.drawText(String.valueOf(quantity), getPosXCenter(), getPosYCenter()+15, p);
	}
}
