package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.move.Movable;
import fr.umlv.escape.weapon.Shootable;

public class PlayerShip extends Ship{

	public PlayerShip(String name, int health, Body body, Bitmap image,
			Movable moveBehaviour, Shootable shootBehaviour) {
		super(name, health, body, image, moveBehaviour, shootBehaviour);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDrawSprite(Canvas canvas) {
		super.onDrawSprite(canvas);
		System.out.println("Drawing player : "+this.toString());
		this.setImage(FrontApplication.frontImage.getImage(getNextImageName()));
	}
	
	@Override
	public boolean isStillDisplayable(){
		return true;
	}
}
