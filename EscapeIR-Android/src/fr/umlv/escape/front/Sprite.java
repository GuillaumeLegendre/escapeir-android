package fr.umlv.escape.front;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.world.EscapeWorld;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprite {
	public final Body body; //FIXME que faire du public car utilisé dans tous les packages public ou getter?
	public Bitmap image;

	/* ou passer parametre directement pour cr�er le body ici*/
	public Sprite(Body body, Bitmap image){
		this.body = body;
		this.image = image;
	}
	
	public void onDrawSprite(Canvas canvas){
		canvas.drawBitmap(image, getPosXCenter() - image.getWidth()/2 , getPosYCenter() - image.getHeight()/2, new Paint());
	}
	
	/**
	 * Return the x position of the center of the sprite.
	 * @return The x position of the center of the sprite.
	 */
	public int getPosXCenter() {
		return (int)(body.getPosition().x*EscapeWorld.SCALE);
	}

	/**
	 * Return the y position of the center of the sprite.
	 * @return The y position of the center of the spriteship.
	 */
	public int getPosYCenter() {
		return (int)(body.getPosition().y*EscapeWorld.SCALE);
	}
	
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Body getBody() {
		return body;
	}
	
	public boolean isStillDisplayable(){
		return true;
	}
}
