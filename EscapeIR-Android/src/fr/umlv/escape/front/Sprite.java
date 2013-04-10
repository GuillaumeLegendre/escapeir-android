package fr.umlv.escape.front;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;

public class Sprite {
	public final Body body; //FIXME que faire du public car utilisé dans tous les packages public ou getter?
	public Bitmap image;
	
	/* ou passer parametre directement pour cr�er le body ici*/
	public Sprite(Body body, Bitmap image){
		this.body = body;
		this.image = image;
	}
}
