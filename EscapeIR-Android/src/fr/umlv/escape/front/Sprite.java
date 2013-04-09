package fr.umlv.escape.front;

import android.graphics.Bitmap;

public abstract class Sprite {
	final Body body;
	
	/* ou passer parametre directement pour créer le body ici*/
	public Sprite(Body body){
		this.body = body;
	}
	
	abstract Bitmap getNextImage();
}
